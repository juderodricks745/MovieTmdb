package com.davidbronn.movietmdb.ui.movies

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.davidbronn.movietmdb.R
import com.davidbronn.movietmdb.databinding.MoviesFragmentBinding
import com.davidbronn.movietmdb.utils.extensions.asVisibility
import com.davidbronn.movietmdb.utils.extensions.gone
import com.davidbronn.movietmdb.utils.extensions.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var binding: MoviesFragmentBinding
    private val viewModel: MoviesViewModel by viewModels()
    private var themeListener: IThemeChangeListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        themeListener = context as IThemeChangeListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return  if (!::binding.isInitialized) {
            binding = MoviesFragmentBinding.inflate(layoutInflater)
            binding.vm = viewModel
            binding.lifecycleOwner = viewLifecycleOwner
            binding.root
        } else {
            binding.root
        }
        /*binding = MoviesFragmentBinding.inflate(layoutInflater)
        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root*/
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpAdapter()
        setUpObserver()
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_settings, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_theme -> {
                themeListener?.onThemeChange()
                true
            }
            else -> false
        }
    }

    private fun setUpObserver() {
        lifecycleScope.launchWhenCreated {
            viewModel.fetchMovies().collectLatest { pagingData ->
                moviesAdapter.submitData(pagingData)
            }
        }

        lifecycleScope.launchWhenCreated {
            moviesAdapter.loadStateFlow.collect { loadState ->
                // This will be called during initial loading
                binding.progressBar.visibility = (loadState.refresh is LoadState.Loading).asVisibility()

                // This will be called to clear the error view if it is displayed after initial exception
                if (loadState.append is LoadState.NotLoading && moviesAdapter.itemCount == 0) {
                    binding.btnRetry.gone()
                    binding.tvNetworkIssue.gone()
                }

                // This will be called if any error occurs initially
                if (loadState.refresh is LoadState.Error) {
                    binding.tvNetworkIssue.text = (loadState.refresh as LoadState.Error).error.localizedMessage
                    binding.progressBar.gone()
                    binding.btnRetry.visible()
                    binding.tvNetworkIssue.visible()
                    binding.btnRetry.setOnClickListener {
                        moviesAdapter.retry()
                    }
                }
            }
        }
    }

    private fun setUpAdapter() {
        moviesAdapter = MoviesAdapter { item ->
            val directions = MoviesFragmentDirections.toDetails(item.title, item.id, item.posterPath)
            findNavController().navigate(directions)
        }
        val footerAdapter = ReposLoadStateAdapter { moviesAdapter.retry() }
        val gridLayoutManager = GridLayoutManager(context, 2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == moviesAdapter.itemCount && footerAdapter.itemCount > 0) 2 else 1
            }
        }
        binding.rvMovies.hasFixedSize()
        binding.rvMovies.layoutManager = gridLayoutManager
        binding.rvMovies.adapter = moviesAdapter.withLoadStateFooter(footerAdapter)
    }

    override fun onDetach() {
        themeListener = null
        super.onDetach()
    }
}