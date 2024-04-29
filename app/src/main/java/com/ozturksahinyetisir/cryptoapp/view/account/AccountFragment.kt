package com.ozturksahinyetisir.cryptoapp.view.account

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ozturksahinyetisir.cryptoapp.R
import com.ozturksahinyetisir.cryptoapp.data.model.MyAsset
import com.ozturksahinyetisir.cryptoapp.data.repository.MyAssetRepository
import com.ozturksahinyetisir.cryptoapp.databinding.FragmentAccountBinding
import com.ozturksahinyetisir.cryptoapp.view.home.HomeFragment
import com.ozturksahinyetisir.cryptoapp.viewmodels.CryptoInfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!
    private val cryptoInfoViewModel: CryptoInfoViewModel by viewModels()
    @Inject
    lateinit var myAssetRepository: MyAssetRepository
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

        binding.addButton.setOnClickListener {
            showAddAssetDialog()
        }

        updateBalance()


        }
    private fun showAddAssetDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_new_asset, null)
        val spinnerAsset: Spinner = dialogView.findViewById(R.id.spinnerAsset)
        val editTextAmount: EditText = dialogView.findViewById(R.id.editTextAmount)

        val assetNames = cryptoInfoViewModel.getAllCryptoNames()
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, assetNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerAsset.adapter = adapter

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setPositiveButton("Add") { dialog, _ ->
                val selectedAssetName = assetNames[spinnerAsset.selectedItemPosition]
                val amount = editTextAmount.text.toString().toDoubleOrNull() ?: 0.0

                Log.d("SelectedAssetName", "Selected Asset Name: $selectedAssetName")

                val allCryptos = cryptoInfoViewModel.allCryptos.value
                val selectedCrypto = allCryptos?.find { it.name.equals(selectedAssetName, ignoreCase = true) }
                val price = selectedCrypto?.quote?.USD?.price ?: 0.0


                val totalValue = amount * price


                val newAsset = MyAsset(
                    cryptoId = selectedCrypto?.id ?: 0,
                    name = selectedAssetName,
                    symbol = selectedCrypto?.symbol ?: "",
                    amount = amount,
                    price = price,
                    totalValue = totalValue
                )

                lifecycleScope.launch {
                    myAssetRepository.insertMyAsset(newAsset)
                    Toast.makeText(
                        requireContext(),
                        "Asset added successfully!",
                        Toast.LENGTH_SHORT
                    ).show()
                    Toast.makeText(requireContext(), "Total Value: $totalValue", Toast.LENGTH_SHORT)
                        .show()
                    updateBalance()
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        val titleTextView = dialog.findViewById<TextView>(R.id.dialogTitle)
        titleTextView?.setTextColor(ContextCompat.getColor(requireContext(), R.color.orange))

        dialog.window?.setBackgroundDrawableResource(R.color.oxfordblue)
        dialog.show()
    }

    private fun updateBalance() {
        lifecycleScope.launch {
            val totalValue: Double = myAssetRepository.getTotalValue() ?: 0.0
            val formattedValue = String.format("%.2f", totalValue)
            binding.balanceText.text = formattedValue
        }
    }
}