package com.davidbronn.movietmdb.utils.navigation

import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment
import com.davidbronn.movietmdb.utils.navigation.IMDBFragmentNavigator

class IMDBNavHostFragment : NavHostFragment() {

    override fun createFragmentNavigator(): Navigator<out FragmentNavigator.Destination> {
        return IMDBFragmentNavigator(requireContext(), childFragmentManager, id)
    }
}