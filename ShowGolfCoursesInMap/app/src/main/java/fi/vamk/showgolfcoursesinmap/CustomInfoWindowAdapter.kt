package fi.vamk.showgolfcoursesinmap

import android.app.Activity
import android.content.Context
import android.view.View
import com.bumptech.glide.Glide
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import kotlinx.android.synthetic.main.custom_info_window.view.*

class CustomInfoWindowAdapter(val context: Context) : GoogleMap.InfoWindowAdapter {

    override fun getInfoContents(p0: Marker?): View {

        val url = "http://ptm.fi/materials/golfcourses/"

        var mInfoView = (context as Activity).layoutInflater.inflate(R.layout.custom_info_window, null)
        var mInfoWindow: InfoWindowData? = p0?.tag as InfoWindowData?

        mInfoView.titleTextView.text = mInfoWindow?.title
        mInfoView.addressTextView.text = mInfoWindow?.address
        mInfoView.phoneTextView.text = mInfoWindow?.phone
        mInfoView.emailTextView.text = mInfoWindow?.email
        mInfoView.weburlTextView.text = mInfoWindow?.web
        Glide.with(mInfoView.imageView.context).load( url + (mInfoWindow?.img)).into(mInfoView.imageView)
        return mInfoView
    }

    override fun getInfoWindow(p0: Marker?): View? {
        return null
    }

}