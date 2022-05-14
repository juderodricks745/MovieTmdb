package com.davidbronn.movietmdb.ui.people

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.davidbronn.movietmdb.databinding.PeopleFragmentBinding
import com.davidbronn.movietmdb.utils.binding.ViewBindingUtils.setImagePosterUrlWithTitle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PeopleFragment : Fragment() {

    private lateinit var binding: PeopleFragmentBinding
    private val viewModel: PeopleViewModel by viewModels()

    private val args: PeopleFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return  if (!::binding.isInitialized) {
            binding = PeopleFragmentBinding.inflate(layoutInflater)
            binding.vm = viewModel
            binding.lifecycleOwner = viewLifecycleOwner
            binding.root
        } else {
            binding.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchPerson(args.peopleID)
        binding.ivPersonProfile.setImagePosterUrlWithTitle(args.peopleUrl)

        viewModel.state.observe(viewLifecycleOwner) { state ->
            when(state) {
                is PeopleViewModel.PersonState.PersonDetail -> {
                    binding.person = state.detail
                    binding.executePendingBindings()
                }
            }
        }
    }
}