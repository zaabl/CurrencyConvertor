package com.example.currencyconvertoralpha.ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.currencyconvertoralpha.databinding.ActivityMainBinding
import com.example.currencyconvertoralpha.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private var selected: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fillSpinners()
//        ViewModelGetAllCurrencies()
        loadData()
        viewsOnclickImpl()
        numberPadImpl()
    }

    private fun loadData(){
        viewModel.loadCurrencies()

        lifecycleScope.launchWhenStarted {
            viewModel.conversion.collect{event->
                when(event){
                    is MainViewModel.CurrencyEvent.Success ->{
                        binding.progressBar.isVisible = false
                        Toast.makeText(this@MainActivity, "Success", Toast.LENGTH_SHORT).show()
                    }
                    is MainViewModel.CurrencyEvent.Failure ->{
                        binding.progressBar.isVisible = false
                        Toast.makeText(this@MainActivity, "Failed to connect to the internet", Toast.LENGTH_SHORT).show()
                    }
                    is MainViewModel.CurrencyEvent.Loading ->{
                        binding.progressBar.isVisible = true
                    }
                    else -> Unit
                }
            }
        }

    }

    private fun viewsOnclickImpl(){
        binding.tvFirstCurrencyValue.setTextColor(Color.MAGENTA)
        binding.tvFirstCurrencyValue.setOnClickListener {
            binding.tvFirstCurrencyValue.setTextColor(Color.MAGENTA)
            binding.tvSecondCurrencyValue.setTextColor(Color.BLACK)
            selected = 1
        }

        binding.tvSecondCurrencyValue.setOnClickListener {
            binding.tvSecondCurrencyValue.setTextColor(Color.MAGENTA)
            binding.tvFirstCurrencyValue.setTextColor(Color.BLACK)
            selected = 2
        }

        binding.imgSwap.setOnClickListener{
            //Handling Spinner values
            val tempSelectedCurrency = binding.spFirstCurrency.selectedItemPosition
            binding.spFirstCurrency.setSelection(binding.spSecondCurrency.selectedItemPosition)
            binding.spSecondCurrency.setSelection(tempSelectedCurrency)

            //Handling TextView values

            refreshTextViews()
        }
    }


    private fun refreshTextViews(){
        viewModel.convertCurrencies(selected,
            binding.spFirstCurrency.selectedItem.toString(),
            binding.spSecondCurrency.selectedItem.toString())
        binding.tvFirstCurrencyValue.text = viewModel.input1
        binding.tvSecondCurrencyValue.text = viewModel.input2
    }

    private fun numberPadImpl(){
        binding.btnC.setOnClickListener{
            viewModel.setInput(selected, "C")
            refreshTextViews()
        }
        binding.btnZero.setOnClickListener{
            viewModel.setInput(selected, "0")
            refreshTextViews()
        }
        binding.btnDot.setOnClickListener{
            viewModel.setInput(selected, ".")
            refreshTextViews()
        }
        binding.btnOne.setOnClickListener{
            viewModel.setInput(selected, "1")
            refreshTextViews()
        }
        binding.btnTwo.setOnClickListener{
            viewModel.setInput(selected, "2")
            refreshTextViews()
        }
        binding.btnThree.setOnClickListener{
            viewModel.setInput(selected, "3")
            refreshTextViews()
        }
        binding.btnFour.setOnClickListener{
            viewModel.setInput(selected, "4")
            refreshTextViews()
        }
        binding.btnFive.setOnClickListener{
            viewModel.setInput(selected, "5")
            refreshTextViews()
        }
        binding.btnSix.setOnClickListener{
            viewModel.setInput(selected, "6")
            refreshTextViews()
        }
        binding.btnSeven.setOnClickListener{
            viewModel.setInput(selected, "7")
            refreshTextViews()
        }
        binding.btnEight.setOnClickListener{
            viewModel.setInput(selected, "8")
            refreshTextViews()
        }
        binding.btnNine.setOnClickListener{
            viewModel.setInput(selected, "9")
            refreshTextViews()
        }
    }

    private fun fillSpinners(){
        val currenciesNames = viewModel.getCurrenciesNames()
        val adapter = ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, currenciesNames)
        binding.spFirstCurrency.adapter = adapter
        binding.spSecondCurrency.adapter = adapter
    }


}