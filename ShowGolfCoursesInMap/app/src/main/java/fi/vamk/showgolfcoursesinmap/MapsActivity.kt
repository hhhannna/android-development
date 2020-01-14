package fi.vamk.showgolfcoursesinmap

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

import com.android.volley.*
import com.android.volley.toolbox.*
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        createMarkers()
    }

    fun createMarkers() {
        // Instantiate the RequestQueue
        val queue = Volley.newRequestQueue(this)
        // URL to JSON data - remember use your own data here
        val url = "http://ptm.fi/materials/golfcourses/golf_courses.json"
        // Create request and listeners
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                // Get employees from JSON
                val courses = response.getJSONArray("courses")
                val length = courses.length()

                for (i in 0 until length) {
                    val coursesObject = courses.getJSONObject(i)
                    val type: String = coursesObject.getString("type")
                    val lat: Double = coursesObject.getString("lat").toDouble()
                    val lng: Double = coursesObject.getString("lng").toDouble()
                    val pos = LatLng(lat, lng)
                    val course: String = coursesObject.getString("course")
                    val address: String = coursesObject.getString("address")
                    val phone: String = coursesObject.getString("phone")
                    val email: String = coursesObject.getString("email")
                    val web: String = coursesObject.getString("web")
                    val imgPath: String = coursesObject.getString("image")

                    val kulta = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)
                    val kultaetu = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)
                    val etu = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)
                    val muu = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                    val color: BitmapDescriptor

                    if (type == "Kulta") {
                        color = kulta
                    } else if (type == "Kulta/Etu") {
                        color = kultaetu
                    } else if (type == "Etu") {
                        color = etu
                    } else { color = muu
                    }

                    val info = InfoWindowData(course, address, phone, email, web, imgPath)

                    val customInfoWindow = CustomInfoWindowAdapter(this)
                    mMap.setInfoWindowAdapter(customInfoWindow)

                    val marker = mMap.addMarker(MarkerOptions().position(pos).icon(color))
                    marker.tag = info
                    marker.showInfoWindow()

                }
            },
            Response.ErrorListener { error ->
                Log.d("JSON",error.toString())
            }
        )
        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
    }
}
