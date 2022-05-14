package com.davidbronn.movietmdb.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.davidbronn.movietmdb.databinding.DetailsFragmentBinding
import com.davidbronn.movietmdb.databinding.PeopleFragmentBinding
import com.davidbronn.movietmdb.domain.model.DetailsModel
import com.davidbronn.movietmdb.utils.binding.ViewBindingUtils.setImageBackDropUrl
import com.davidbronn.movietmdb.utils.extensions.asVisibility
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private val args: DetailsFragmentArgs by navArgs()
    private lateinit var binding: DetailsFragmentBinding
    private lateinit var castAdapter: MovieCastItemAdapter
    private lateinit var moviesAdapter: MovieCastItemAdapter
    private val viewModel: DetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return  if (!::binding.isInitialized) {
            binding = DetailsFragmentBinding.inflate(layoutInflater)
            binding.vm = viewModel
            binding.lifecycleOwner = viewLifecycleOwner
            binding.root
        } else {
            binding.root
        }
        /*binding = DetailsFragmentBinding.inflate(layoutInflater)
        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root*/
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpAdapters()
        setUpObservers()
        viewModel.fetchMovieDetails(args.movieID)
        viewModel.fetchSimilarMovies(args.movieID)
        viewModel.fetchCreditsAndCasts(args.movieID)
    }

    private fun setUpAdapters() {
        castAdapter = MovieCastItemAdapter { item ->
            if (item.isMovie.not()) {
                val directions = DetailsFragmentDirections.toPeople(item.title, item.movieId.toInt(), item.url)
                findNavController().navigate(directions)
            }
        }
        binding.rvCast.adapter = castAdapter
        binding.rvCast.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        moviesAdapter = MovieCastItemAdapter { item ->
            // TODO: No navigation to be done
        }
        binding.rvSimilarMovies.adapter = moviesAdapter
        binding.rvSimilarMovies.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setUpObservers() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is DetailsViewModel.DetailsState.MovieCasts -> {
                    castAdapter.submitList(state.castModels)
                    binding.cvCast.visibility = state.castModels.isNotEmpty().asVisibility()
                }
                is DetailsViewModel.DetailsState.SimilarMovies -> {
                    moviesAdapter.submitList(state.models)
                    binding.cvSimilarMovies.visibility = state.models.isNotEmpty().asVisibility()
                }
                is DetailsViewModel.DetailsState.MovieDetail -> {
                    setMovieDetails(state.detail)
                }
            }
        }
    }

    private fun setMovieDetails(model: DetailsModel) {
        binding.detail = model
        binding.executePendingBindings()
    }
}