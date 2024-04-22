package com.ozturksahinyetisir.cryptoapp.view.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ozturksahinyetisir.cryptoapp.data.adapter.CryptoInfoAdapter
import com.ozturksahinyetisir.cryptoapp.databinding.FragmentHomeBinding
import com.ozturksahinyetisir.cryptoapp.viewmodels.CryptoInfoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val cryptoInfoViewModel: CryptoInfoViewModel by viewModels()
    private lateinit var cryptoInfoAdapter: CryptoInfoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cryptoInfoAdapter = CryptoInfoAdapter()

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = cryptoInfoAdapter
        }

        Log.d("Home","home fragment created.")
        observeCryptoInfoList()

        val refreshButton: ImageButton = binding.buttonRefresh
        refreshButton.setOnClickListener {
            cryptoInfoViewModel.refreshCryptos()
        }
    }

    private fun observeCryptoInfoList() {
        cryptoInfoViewModel.allCryptos.observe(viewLifecycleOwner) { cryptoList ->
            cryptoList?.let {
                cryptoInfoAdapter.submitList(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}