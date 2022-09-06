package com.moviedb.util

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.moviedb.R
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class BindingUtils {
    companion object {
        fun bindImage(imgView: ImageView, imgUrl: String?) {
            imgUrl.let {
                if (it == null) {
                    Glide.with(imgView.context).load(R.drawable.ic_broken_image).into(imgView)
                } else {
                    val fullUri = "${Constants.FULL_IMAGE_IRL}$imgUrl"
                    val imgUri = fullUri.toUri().buildUpon().scheme(Constants.SCHEME).build()
                    Glide.with(imgView.context)
                        .load(imgUri)
                        .placeholder(R.drawable.ic_broken_image)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .transform(RoundedCorners(8))
                        .into(imgView)
                }
            }
        }

        fun bindMovieDateYear(textView: TextView, date: String?, onlyYear: Boolean) {
            try {
                date?.let {
                    val parser = SimpleDateFormat(Constants.DATE_YEAR_MONTH_DAY, Locale.US)
                    val formatter = if (onlyYear) {
                        SimpleDateFormat(Constants.DATE_YEAR, Locale.US)
                    } else {
                        SimpleDateFormat(Constants.DATE_MONTH_YEAR, Locale.US)
                    }
                    val dateParsed = parser.parse(it)
                    if (dateParsed != null) {
                        val output = formatter.format(dateParsed)
                        textView.text = output.toString()
                    } else {
                        textView.text = "-"
                    }
                }
            } catch (e: Exception) {
                textView.text = "-"
            }
        }

        fun bindIntToCurrency(textView: TextView, int: Long) {
            val currency = NumberFormat.getInstance(Locale.US).format(int)
            textView.text = if (int == 0.toLong()) "-" else "$$currency.00"
        }
    }
}

