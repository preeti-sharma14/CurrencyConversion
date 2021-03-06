package com.assignment.currencyConversion.main.presentation.view

import android.os.Bundle
import android.view.View
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.assignment.currencyConversion.R
import com.assignment.currencyConversion.databinding.ActivityConversionBinding
import com.assignment.currencyConversion.main.domain.helper.EndPoints
import com.assignment.currencyConversion.main.domain.helper.Resource
import com.assignment.currencyConversion.main.Utils.Utility
import com.assignment.currencyConversion.main.domain.model.Rates
import com.assignment.currencyConversion.main.presentation.viewmodel.ConversionListViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.ArrayList
import android.widget.ArrayAdapter
import com.assignment.currencyConversion.main.domain.model.CurrencyListResponse


@AndroidEntryPoint
class ConversionActivity : AppCompatActivity() {
    private var viewBinding: ActivityConversionBinding? = null
    private val binding get() = viewBinding!!
    val spinner1 = viewBinding?.spnFirstCountry
    val spinner2 = viewBinding?.spnSecondCountry
    private var selectedItem1: String? = "EGP"
    private var selectedItem2: String? = "INR"

    private lateinit var conversionViewModel:ConversionListViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_NoActionBar)
        super.onCreate(savedInstanceState)
        conversionViewModel=ViewModelProvider(this).get(ConversionListViewModel::class.java)
        viewBinding = ActivityConversionBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initSpinner()
        conversionViewModel.conversionList.observe(this){

        }
        setUpClickListener()
    }

    private fun initSpinner() {

        spinner1?.setItems(getAllCountries())
        spinner1?.setOnClickListener {
            Utility.hideKeyboard(this)
        }
        spinner1?.setOnItemSelectedListener { view, position, id, item ->
            val countryCode = getCountryCode(item.toString())
           // val currencySymbol = getSymbol(countryCode)
            //selectedItem1 = currencySymbol
            //viewBinding?.txtFirstCurrencyName?.setText(selectedItem1)
        }

        val spinner2 = viewBinding?.spnSecondCountry
        spinner2?.setOnClickListener {
            Utility.hideKeyboard(this)
        }
        spinner2?.setItems(getAllCountries())
        spinner2?.setOnItemSelectedListener { view, position, id, item ->
            val countryCode = getCountryCode(item.toString())
            val currencySymbol = getSymbol()
           // selectedItem2 = currencySymbol
            //viewBinding?.txtSecondCurrencyName?.setText(selectedItem2)
        }
    }

    private fun getCountryCode(countryName: String) =
        Locale.getISOCountries().find { Locale("", it).displayCountry == countryName }

    private fun getSymbol() {
        //hide keyboard
        Utility.hideKeyboard(this)

        //make progress bar visible
        binding.prgLoading.visibility = View.VISIBLE

        //make button invisible
        binding.btnConvert.visibility = View.GONE

        //Get the data inputed
        val apiKey = EndPoints.API_KEY
        val adapter = ArrayAdapter<Resource<CurrencyListResponse>>(
            this,
            android.R.layout.simple_spinner_item
        )
        adapter.add(conversionViewModel.getConversionList(apiKey))
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner1?.setAdapter(adapter)

    }

    private fun getAllCountries(): ArrayList<String> {
        val locales = Locale.getAvailableLocales()
        val countries = ArrayList<String>()
        for (locale in locales) {
            val country = locale.displayCountry
            if (country.trim { it <= ' ' }.isNotEmpty() && !countries.contains(country)) {
                countries.add(country)
            }
        }
        countries.sort()
        return countries

    }

    private fun setUpClickListener() {

        //Convert button clicked - check for empty string and internet then do the conersion
        binding.btnConvert.setOnClickListener {

            //check if the input is empty
            val numberToConvert = binding.etFirstCurrency.text.toString()

            if (numberToConvert.isEmpty() || numberToConvert == "0") {
                Snackbar.make(
                    binding.mainLayout,
                    "Input a value in the first text field, result will be shown in the second text field",
                    Snackbar.LENGTH_LONG
                )
                    .withColor(ContextCompat.getColor(this, R.color.dark_red))
                    .setTextColor(ContextCompat.getColor(this, R.color.white))
                    .show()
            }

            //check if internet is available
            else if (!Utility.isNetworkAvailable(this)) {
                Snackbar.make(
                    binding.mainLayout,
                    "You are not connected to the internet",
                    Snackbar.LENGTH_LONG
                )
                    .withColor(ContextCompat.getColor(this, R.color.dark_red))
                    .setTextColor(ContextCompat.getColor(this, R.color.white))
                    .show()
            }

            //carry on and convert the value
            else {
                doConversion()
            }
        }
    }

    /**
     * A method that does the conversion by communicating with the API - fixer.io based on the data inputed
     * Uses viewModel and flows
     */

    private fun doConversion() {

        //hide keyboard
        Utility.hideKeyboard(this)

        //make progress bar visible
        binding.prgLoading.visibility = View.VISIBLE

        //make button invisible
        binding.btnConvert.visibility = View.GONE

        //Get the data inputed
        val apiKey = EndPoints.API_KEY

        //do the conversion
        conversionViewModel.getConversionList(apiKey)

        //observe for changes in UI
        observeUi()

    }

    /**
     * Using coroutines flow, changes are observed and responses gotten from the API
     *
     */

    private fun observeUi() {

        conversionViewModel.updateData.observe(this, androidx.lifecycle.Observer { result ->

            when (result.status) {
                Resource.Status.SUCCESS -> {
                    if (result.data?.success == true) {

                        val map: Map<String, Rates> = result.data.rates!!
                        map.keys.forEach {

                            val rateForAmount = map[it]?.rate_for_amount

                            conversionViewModel.convertedRate.value = rateForAmount

                            //format the result obtained e.g 1000 = 1,000
                            val formattedString =
                                String.format("%,.2f", conversionViewModel.convertedRate.value)

                            //set the value in the second edit text field
                            binding.etSecondCurrency.setText(formattedString)

                        }


                        //stop progress bar
                        binding.prgLoading.visibility = View.GONE
                        //show button
                        binding.btnConvert.visibility = View.VISIBLE
                    } else if (result.data?.success == false) {
                        val layout = binding.mainLayout
                        Snackbar.make(
                            layout,
                            "Ooops! something went wrong, Try again",
                            Snackbar.LENGTH_LONG
                        )
                            .withColor(ContextCompat.getColor(this, R.color.dark_red))
                            .setTextColor(ContextCompat.getColor(this, R.color.white))
                            .show()

                        //stop progress bar
                        binding.prgLoading.visibility = View.GONE
                        //show button
                        binding.btnConvert.visibility = View.VISIBLE
                    }
                }
                Resource.Status.ERROR -> {

                    val layout = binding.mainLayout
                    Snackbar.make(
                        layout,
                        "Oopps! Something went wrong, Try again",
                        Snackbar.LENGTH_LONG
                    )
                        .withColor(ContextCompat.getColor(this, R.color.dark_red))
                        .setTextColor(ContextCompat.getColor(this, R.color.white))
                        .show()
                    //stop progress bar
                    binding.prgLoading.visibility = View.GONE
                    //show button
                    binding.btnConvert.visibility = View.VISIBLE
                }

                Resource.Status.LOADING -> {
                    //stop progress bar
                    binding.prgLoading.visibility = View.VISIBLE
                    //show button
                    binding.btnConvert.visibility = View.GONE
                }
            }
        })
    }

    /**
     * Method for changing the background color of snackBars
     */

    private fun Snackbar.withColor(@ColorInt colorInt: Int): Snackbar {
        this.view.setBackgroundColor(colorInt)
        return this
    }

}