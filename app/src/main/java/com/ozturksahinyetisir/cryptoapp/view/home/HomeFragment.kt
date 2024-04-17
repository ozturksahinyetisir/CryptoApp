package com.ozturksahinyetisir.cryptoapp.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ozturksahinyetisir.cryptoapp.data.adapter.CryptoInfoAdapter
import com.ozturksahinyetisir.cryptoapp.databinding.FragmentHomeBinding
import com.ozturksahinyetisir.cryptoapp.util.Resource
import com.ozturksahinyetisir.cryptoapp.viewmodels.CryptoInfoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val cryptoInfoViewModel: CryptoInfoViewModel by viewModels()
    private lateinit var cryptoInfoAdapter: CryptoInfoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cryptoInfoAdapter = CryptoInfoAdapter()

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = cryptoInfoAdapter
        }

        observeCryptoInfoList()
        cryptoInfoViewModel.getCryptoInfoList()
    }

    private fun observeCryptoInfoList() {
        cryptoInfoViewModel.cryptoInfoList.observe(viewLifecycleOwner, Observer { resource ->
            when (resource) {
                is Resource.Success -> {
                    cryptoInfoAdapter.submitList(resource.data)
                }
                is Resource.Error -> {
                    // TODO : Error
                }
                is Resource.Loading -> {
                    // TODO : Loading
                }
            }
        })
    }
}
