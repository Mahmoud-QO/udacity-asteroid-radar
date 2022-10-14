package com.udacity.asteroidradar

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.PictureOfDay
import com.udacity.asteroidradar.ui.main.AsteroidListAdapter
import com.udacity.asteroidradar.ui.main.MainViewModel

//if (pic != null && pic.mediaType == "image") {
//    Picasso.with(context).load(pic.url).into(binding.activityMainImageOfTheDay)

@BindingAdapter("binding_imageResource_withPicasso_imgUrlOf")
fun ImageView.bindImgUrlImageResource(value: PictureOfDay?) { value?.let {
    val imgUri = it.url.toUri().buildUpon().scheme("https").build()
    Picasso.with(context)
        .load(imgUri)
        .into(this)
}}

@BindingAdapter("binding_adapter_viewModel")
fun RecyclerView.setViewModelAdapter(vm: MainViewModel) {
    adapter = AsteroidListAdapter(
        AsteroidListAdapter.ItemListener { vm.navToDetail(it) }
    )
}

@BindingAdapter("binding_adapterList_asteroids")
fun RecyclerView.setAsteroidsAdapterList(value: List<Asteroid>?) { value?.let {
    val adapter = adapter as AsteroidListAdapter
    adapter.submitAsteroidList(it)
}}

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}

//@BindingAdapter("binding_image_marsApiStatus")
//fun ImageView.setMarsApiStatusImage(value: MarsApiStatus?) { value?.let {
//    @Suppress("LiftReturnOrAssignment")
//    when(it) {
//        MarsApiStatus.LOADING -> {
//            setImageResource(R.drawable.loading_animation)
//            visibility = View.VISIBLE
//        }
//        MarsApiStatus.DONE -> {
//            visibility = View.GONE
//        }
//        MarsApiStatus.ERROR -> {
//            setImageResource(R.drawable.ic_connection_error)
//            visibility = View.VISIBLE
//        }
//    }
//}}
//
//@BindingAdapter("binding_imageResource_imgUrlOf")
//fun ImageView.setImgUrlImageResource(value: RealEstate?) { value?.let {
//    val imgUri = it.imgUrl.toUri().buildUpon().scheme("https").build()
//    Glide.with(context)
//        .load(imgUri)
//        .apply(RequestOptions()
//            .placeholder(R.drawable.loading_animation)
//            .error(R.drawable.ic_broken_image))
//        .into(this)
//}}
