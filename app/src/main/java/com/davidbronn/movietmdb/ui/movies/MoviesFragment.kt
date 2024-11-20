package com.davidbronn.movietmdb.ui.movies

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.davidbronn.movietmdb.R
import com.davidbronn.movietmdb.databinding.MoviesFragmentBinding
import com.davidbronn.movietmdb.utils.extensions.gone
import com.davidbronn.movietmdb.utils.extensions.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var binding: MoviesFragmentBinding
    private val viewModel: MoviesViewModel by viewModels()
    private var themeListener: IThemeChangeListener? = null
    private val menuHost: MenuHost by lazy { requireActivity() }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        themeListener = context as IThemeChangeListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MoviesFragmentBinding.inflate(layoutInflater)
        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpMenu()
        setUpAdapter()
        setUpObserver()
    }

    private fun setUpMenu() {
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_settings, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.menu_theme -> {
                        themeListener?.onThemeChange() // Handle theme change
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setUpAdapter() {
        moviesAdapter = MoviesAdapter { item ->
            val directions = MoviesFragmentDirections.toDetails(item.title, item.id, item.posterPath)
            findNavController().navigate(directions)
        }

        moviesAdapter.addLoadStateListener { loadState ->
            val refreshState = loadState.refresh
            // Handle loading state
            binding.progressBar.isVisible = refreshState is LoadState.Loading

            // Handle error for refresh state
            if (refreshState is LoadState.Error) {
                showError(refreshState.error.localizedMessage)
            } else {
                binding.tvNetworkIssue.gone()
                binding.btnRetry.gone()
            }
        }

        // Add load state footer for pagination; load & error for append states will be handled by the adapter itself.
        val footerAdapter = ReposLoadStateAdapter { moviesAdapter.retry() }
        val gridLayoutManager = GridLayoutManager(context, 2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == moviesAdapter.itemCount && footerAdapter.itemCount > 0) 2 else 1
            }
        }
        binding.rvMovies.setHasFixedSize(true)
        binding.rvMovies.layoutManager = gridLayoutManager
        binding.rvMovies.adapter = moviesAdapter.withLoadStateFooter(footerAdapter)
    }

    private fun setUpObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.moviesFlow.collectLatest { pagingData ->
                    moviesAdapter.submitData(pagingData)
                }
            }
        }
    }

    // If an error occurs while initially fetching the data, then this will be shown
    private fun showError(message: String?) {
        binding.progressBar.gone()
        binding.btnRetry.visible()
        binding.tvNetworkIssue.text = message
        binding.tvNetworkIssue.visible()
        binding.btnRetry.setOnClickListener {
            moviesAdapter.retry()
        }
    }

    override fun onDetach() {
        themeListener = null
        super.onDetach()
    }
}