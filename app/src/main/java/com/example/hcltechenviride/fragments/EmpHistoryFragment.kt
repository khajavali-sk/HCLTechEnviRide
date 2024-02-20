package com.example.hcltechenviride.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hcltechenviride.R
import com.example.hcltechenviride.databinding.FragmentEmpHistoryBinding
import com.example.hcltechenviride.databinding.FragmentEmpProfileBinding


class EmpHistoryFragment : Fragment() {
    private lateinit var binding: FragmentEmpHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEmpHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {

                }


}