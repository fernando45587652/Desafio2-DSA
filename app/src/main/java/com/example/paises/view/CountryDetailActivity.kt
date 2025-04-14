package com.example.paises.view
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.paises.controller.PaisApiClient
import com.example.paises.databinding.ActivityCountryDetailBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.launch

class CountryDetailActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityCountryDetailBinding
    private lateinit var mapView: MapView
    private var latLng: LatLng? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mapView = binding.mapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        val countryName = intent.getStringExtra("country_name")
        if (countryName != null) {
            loadCountryDetails(countryName)
        }
    }

    private fun loadCountryDetails(name: String) {
        lifecycleScope.launch {
            try {
                val countries = PaisApiClient.service.getCountryByName(name)
                val country = countries.firstOrNull()
                if (country != null) {
                    binding.tvCountryName.text = country.name.official
                    binding.tvCapital.text = "Capital: ${country.capital?.joinToString() ?: "N/A"}"
                    binding.tvPopulation.text = "Población: ${country.population}"
                    binding.tvRegion.text = "Región: ${country.region}"
                    binding.tvSubregion.text = "Subregión: ${country.subregion ?: "N/A"}"
                    binding.tvLanguages.text = "Idiomas: ${country.languages?.values?.joinToString() ?: "N/A"}"

                    Glide.with(this@CountryDetailActivity)
                        .load(country.flags.png)
                        .into(binding.ivFlag)

                    val lat = country.latlng.getOrNull(0)
                    val lng = country.latlng.getOrNull(1)
                    if (lat != null && lng != null) {
                        latLng = LatLng(lat, lng)
                        mapView.getMapAsync(this@CountryDetailActivity)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        latLng?.let {
            googleMap.addMarker(MarkerOptions().position(it).title("Ubicación del país"))
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(it, 4f))
        }
    }

    // Ciclo de vida del MapView
    override fun onResume() { super.onResume(); mapView.onResume() }
    override fun onPause() { super.onPause(); mapView.onPause() }
    override fun onDestroy() { super.onDestroy(); mapView.onDestroy() }
    override fun onLowMemory() { super.onLowMemory(); mapView.onLowMemory() }
}