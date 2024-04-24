package com.ozturksahinyetisir.cryptoapp.view.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ozturksahinyetisir.cryptoapp.R
import com.ozturksahinyetisir.cryptoapp.databinding.FragmentAccountBinding
import com.ozturksahinyetisir.cryptoapp.view.home.HomeFragment

class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.listImage.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.nav_container, HomeFragment())
                .commit()
        }

        }
}