package com.example.hcltechenviride.fragments_admin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hcltechenviride.databinding.FragmentAdminManageBinding

class AdminManageFragment : Fragment() {

    private lateinit var binding: FragmentAdminManageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAdminManageBinding.inflate(inflater, container, false)

        // Button click listeners to navigate to different activities
        binding.addCycleBtn.setOnClickListener {
            startActivity(Intent(requireActivity(), AddCyclesActivity::class.java))
        }

        binding.cycleList.setOnClickListener {
            startActivity(Intent(requireActivity(), CycleListActivity::class.java))
        }

        binding.userList.setOnClickListener {
            startActivity(Intent(requireActivity(), UserListActivity::class.java))
        }

        return  binding.root
    }

    companion object {

    }
}
