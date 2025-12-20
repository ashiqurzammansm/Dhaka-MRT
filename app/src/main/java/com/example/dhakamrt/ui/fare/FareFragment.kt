package com.example.dhakamrt.ui.fare

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dhakamrt.databinding.FragmentFareBinding

class FareFragment : Fragment() {

    private lateinit var binding: FragmentFareBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentFareBinding.inflate(inflater, container, false)
        return binding.root
    }
}
