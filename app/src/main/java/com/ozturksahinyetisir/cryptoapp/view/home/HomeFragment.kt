package com.ozturksahinyetisir.cryptoapp.view.home

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ozturksahinyetisir.cryptoapp.R
import com.ozturksahinyetisir.cryptoapp.data.adapter.CryptoInfoAdapter
import com.ozturksahinyetisir.cryptoapp.databinding.FragmentHomeBinding
import com.ozturksahinyetisir.cryptoapp.util.RefreshCountDownTimer
import com.ozturksahinyetisir.cryptoapp.view.account.AccountFragment
import com.ozturksahinyetisir.cryptoapp.viewmodels.CryptoInfoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val cryptoInfoViewModel: CryptoInfoViewModel by viewModels()
    private lateinit var cryptoInfoAdapter: CryptoInfoAdapter
    private var countDownTimer: RefreshCountDownTimer? = null
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

        binding.accountImage.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.nav_container, AccountFragment())
                //.addToBackStack(null) removed stack lag
                .commit()
        }


        Log.d("Home","home fragment created.")
        observeCryptoInfoList()
        observeFilteredCryptoInfoList()

        val refreshButton: ImageButton = binding.buttonRefresh
        refreshButton.setOnClickListener {
            cryptoInfoViewModel.refreshCryptos()

            refreshButton.isEnabled = false
            refreshButton.setImageResource(R.drawable.ic_refresh)
            refreshButton.setColorFilter(Color.RED)

            countDownTimer?.cancel()
            countDownTimer = RefreshCountDownTimer(refreshButton).apply {
                start()
            }
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    if (it.isEmpty()) {
                        observeCryptoInfoList()
                    } else {
                        cryptoInfoViewModel.searchCryptoInfo(it)
                    }
                }
                return true
            }
        })
    }


    private fun observeCryptoInfoList() {
        cryptoInfoViewModel.allCryptos.observe(viewLifecycleOwner) { cryptoList ->
            cryptoList?.let {
                cryptoInfoAdapter.submitList(it)
                Log.d("HomeFragment", "Crypto List Size: ${it.size}")
            }
        }
    }
    private fun observeFilteredCryptoInfoList() {
        cryptoInfoViewModel.filteredCryptoInfoList.observe(viewLifecycleOwner) { filteredList ->
            filteredList?.let {
                if (filteredList.isNotEmpty()) {
                    cryptoInfoAdapter.submitList(filteredList)
                    Log.d("HomeFragment", "Filtered Crypto List Size: ${filteredList.size}")
                } else {
                    Log.d("HomeFragment", "Filtered Crypto List is empty.")
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
