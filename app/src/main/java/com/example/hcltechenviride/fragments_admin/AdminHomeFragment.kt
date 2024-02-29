package com.example.hcltechenviride.fragments_admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hcltechenviride.adapters.ViewPagerAdapter
import com.example.hcltechenviride.databinding.FragmentAdminHomeBinding

class AdminHomeFragment : Fragment() {

    // Binding instance for the fragment
    private var _binding: FragmentAdminHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAdminHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Setup view pager and tab layout
        setupViewPager()
    }

    private fun setupViewPager() {
        // Initialize the view pager adapter
        val viewPagerAdapter = ViewPagerAdapter(childFragmentManager)
        // Add fragments to the adapter with their titles
        viewPagerAdapter.addFragments(CyclesInUseFragment(), "Cycles In Use")
        viewPagerAdapter.addFragments(CycleHistoryFragment(), "History")
        // Set the adapter to the view pager
        binding.viewPager.adapter = viewPagerAdapter
        // Connect the tab layout with the view pager
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Set the binding instance to null to prevent memory leaks
        _binding = null
    }

    companion object {

    }
}
