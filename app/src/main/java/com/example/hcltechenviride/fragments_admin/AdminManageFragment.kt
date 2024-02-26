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

        binding = FragmentAdminManageBinding.inflate(inflater, container,false)

        binding.addCycleBtn.setOnClickListener {
            startActivity(Intent(requireActivity(),AddCyclesActivity::class.java))
        }

        binding.cycleList.setOnClickListener {
            startActivity(Intent(requireActivity(),CycleListActivity::class.java))
        }

        return  binding.root
    }

    companion object {

    }
}