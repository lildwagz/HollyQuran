package lildwagz.com.hollyquran

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.asLiveData
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.material.navigation.NavigationView
import com.lzx.starrysky.utils.KtPreferences.Companion.context
import dagger.android.support.DaggerAppCompatActivity
import lildwagz.com.hollyquran.data.QuranDatabase
import lildwagz.com.hollyquran.data.util.PrefsSet
import lildwagz.com.hollyquran.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private val binding: ActivityMainBinding by viewBinding(R.id.drawer_layout)
    private val PERMISSION_ACCESS_FINE_LOCATION: Int = 101
    lateinit var mNavController: NavController



    private lateinit var city: String
    private lateinit var street: String
    private lateinit var subCity: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(binding.root)




        setSupportActionBar(binding.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        mNavController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_quran,R.id.nav_schedule_prayer, R.id.nav_about_us,R.id.nav_bookmark
            ), drawerLayout
        )
        checkLocationPermission()
        setupActionBarWithNavController(mNavController, appBarConfiguration)
        navView.setupWithNavController(mNavController)
//\

        getCurrentLocation()
        requestLocation()

        mNavController.addOnDestinationChangedListener { controller, destination, arguments ->
            when(destination.id){
                R.id.nav_search_quran_Fragment ->{
                    binding.toolbar.isVisible =false

                }else ->{
                    binding.toolbar.isVisible = true
                }
            }
        }
//        throw RuntimeException("Test Crash") // Force a crash


    }

    private fun contohManggilDatabase() {
        val database = QuranDatabase.getInstance(this)
        val quran = database.quranDao().getQuran()
        quran.asLiveData().observe(this, {
            Log.d("Cek Jumlah", it.size.toString())
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(mNavController) || super.onOptionsItemSelected(item)
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }



    private fun requestLocation() {
        val mLocationRequest: LocationRequest = LocationRequest.create()
        mLocationRequest.interval = 60000
        mLocationRequest.fastestInterval = 5000
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        val mLocationCallback: LocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {}
        }
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        LocationServices.getFusedLocationProviderClient(this)
            .requestLocationUpdates(mLocationRequest, mLocationCallback, null)
    }

    fun onPermissionSuccess() {
        getCurrentLocation()
    }

    private fun getCurrentLocation() {
        if (isLocationEnabled() &&  !(isAirplaneModeOn())) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
//                 to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            LocationServices.getFusedLocationProviderClient(this).lastLocation
                .addOnSuccessListener {
                    it?.let { location ->

                        val geocoder = Geocoder(context, Locale.getDefault())

                        val addresses =
                                geocoder.getFromLocation(location?.latitude, location?.longitude, 1)

                        street = addresses[0].getAddressLine(0)
                        subCity = addresses[0].locality
                        city = addresses[0].subAdminArea
//                        if (LocationPrefs.city.isEmpty() || LocationPrefs.city != city) {
//                            LocationPrefs.city = city
//                        }

//                        subscribeSchedule(city)
//                        binding.txtLocation.setText(city)
                        PrefsSet.city = city

//                        Toast.makeText(this, "Lokasi $addresses", Toast.LENGTH_SHORT).show()

                        return@addOnSuccessListener
                    }
                    Toast.makeText(this, "Location GPS not enabled", Toast.LENGTH_SHORT).show()
                }
        }else if(isAirplaneModeOn()){
            Toast.makeText(this, "mode airplane is enabled", Toast.LENGTH_SHORT).show()

        }
        else {
            requestLocationEnable()
        }
    }

    private fun isLocationEnabled(): Boolean {
        val lm: LocationManager =
            this.getSystemService(DaggerAppCompatActivity.LOCATION_SERVICE) as LocationManager
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }
    private fun isAirplaneModeOn(): Boolean {
        return Settings.System.getInt(
            context?.contentResolver,
            Settings.Global.AIRPLANE_MODE_ON, 0
        ) != 0
    }


    private fun requestLocationEnable() {
        AlertDialog.Builder(this)
            .setTitle("Location Enabled")
            .setMessage("GPS Network not enabled")
            .setPositiveButton("Ok") { _, _ ->
                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                AlertDialog.Builder(this)
                    .setTitle("Location Permission")
                    .setMessage("This app require access the location.")
                    .setPositiveButton("Ask me") { _, _ ->
                        requestLocationPermission()
                    }
                    .setNegativeButton("No") { _, _ ->
//                        notifyDetailFragment(false)
                    }
                    .show()
            } else {
                requestLocationPermission()
            }
        } else {
            val k : String = ""
//            notifyDetailFragment(true)
        }
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_ACCESS_FINE_LOCATION
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_ACCESS_FINE_LOCATION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    val k : String = ""
//                    onPermissionSuccess()
//                    notifyDetailFragment(true)
                } else {
                    val k : String = ""

//                    notifyDetailFragment(false)
                    finish()
                }
            }
        }
    }


//    private fun notifyDetailFragment(permissionGranted: Boolean) {
//        if (permissionGranted) {
////            val activeFragment = nav_host_fragment_content_main.childFragmentManager.primaryNavigationFragment
//            if (activeFragment is TabSurahFragment) {
//                activeFragment.onPermissionSuccess()
//            }
//        } else {
////            finish()
//        }
//    }




}