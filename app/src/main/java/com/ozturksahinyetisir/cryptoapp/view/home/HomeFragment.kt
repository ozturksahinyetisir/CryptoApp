package com.ozturksahinyetisir.cryptoapp.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ozturksahinyetisir.cryptoapp.data.adapter.CryptoInfoAdapter
import com.ozturksahinyetisir.cryptoapp.databinding.FragmentHomeBinding
import com.ozturksahinyetisir.cryptoapp.util.Resource
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

        observeCryptoInfoList()
    }

    private fun observeCryptoInfoList() {
        cryptoInfoViewModel.cryptoInfoResource.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    cryptoInfoAdapter.submitList(resource.data)
                }
                is Resource.Error -> {
                    // Todo : show error message

                }
                is Resource.Loading -> {
                    // Todo : loading indicator
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}