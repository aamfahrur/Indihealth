package inn.mroyek.halodokter.utils.commons
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Typeface
import android.net.Uri
import android.os.Handler
import android.provider.MediaStore
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import inn.mroyek.halodokter.utils.components.AmNoSwipe
import inn.mroyek.halodokter.R
import java.io.ByteArrayOutputStream
import java.util.*

class Utils {

    fun imageRound(imageView: ImageView, url: String?, context: Context, scaleType: ImageView.ScaleType? = null) {
        url?.let {
            val radius = context.resources.getDimensionPixelSize(R.dimen.radius)
            var requestOptions = RequestOptions()

            requestOptions = requestOptions.transform(CenterCrop(), RoundedCorners(radius))
            requestOptions = requestOptions.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)

            if(scaleType == ImageView.ScaleType.CENTER_INSIDE) {
                requestOptions = requestOptions.centerInside()
            }

            Glide.with(context)
                .load(it)
                .apply(requestOptions)
                .into(imageView)
        }
    }

    fun imageCircle(imageView: ImageView, url: String?, context: Context, scaleType: ImageView.ScaleType? = null) {
        url?.let {
            val radius = context.resources.getDimensionPixelSize(R.dimen.radius_circle)
            var requestOptions = RequestOptions()

            requestOptions = requestOptions.transform(CenterCrop(), RoundedCorners(radius))
            requestOptions = requestOptions.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)

            if(scaleType == ImageView.ScaleType.CENTER_INSIDE) {
                requestOptions = requestOptions.centerInside()
            }

            Glide.with(context)
                .load(it)
                .apply(requestOptions)
                .into(imageView)
        }
    }

    fun imageTopRound(imageView: ImageView, url: String?, context: Context, scaleType: ImageView.ScaleType? = null) {
        url?.let {
            val radius = context.resources.getDimensionPixelSize(R.dimen.radius)
            var requestOptions = RequestOptions()

            val roundTransform = RoundedCornersTransformation(radius, 0, RoundedCornersTransformation.CornerType.TOP)
            requestOptions = requestOptions.transform(CenterCrop(), roundTransform)
            requestOptions = requestOptions.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)

            if(scaleType == ImageView.ScaleType.CENTER_INSIDE) {
                requestOptions = requestOptions.centerInside()
            }

            Glide.with(context)
                .load(it)
                .apply(requestOptions)
                .into(imageView)
        }
    }

    fun image(imageView: ImageView, url: String?, context: Context, scaleType: ImageView.ScaleType? = null) {
        url?.let {
            var requestOptions = RequestOptions()

            requestOptions = requestOptions.transform(CenterCrop())
            requestOptions = requestOptions.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)

            if(scaleType == ImageView.ScaleType.CENTER_INSIDE) {
                requestOptions = requestOptions.centerInside()
            }

            Glide.with(context)
                .load(it)
                .apply(requestOptions)
                .into(imageView)
        }
    }

    fun showSnackBar(context: Context, layoutParent: View, message: String, duration: Int = Snackbar.LENGTH_SHORT, actionText: String = "", actionListener: Runnable? = null) {
        val mSnackBar = Snackbar.make(layoutParent, message, duration)

        if(message.contains("Unable to resolve")) mSnackBar.behavior = AmNoSwipe()

        mSnackBar.view.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary))

        val textViewAction = mSnackBar.view.findViewById(R.id.snackbar_action) as TextView
        val textView = mSnackBar.view.findViewById(R.id.snackbar_text) as TextView

        textView.setTypeface(Typeface.createFromAsset(context.assets, Constants.View.Name[0] + Constants.View.Style[0] + Constants.View.Type), Typeface.NORMAL)
        textView.setTextColor(ContextCompat.getColor(context, R.color.colorTextPrimary))
        textView.gravity = Gravity.CENTER
        textView.textSize = 11.5f
        textView.maxLines = 2

        if(actionText != "") {

            textViewAction.setTypeface(Typeface.createFromAsset(context.assets, Constants.View.Name[0] + Constants.View.Style[1] + Constants.View.Type), Typeface.NORMAL)
            textViewAction.setTextColor(ContextCompat.getColor(context, R.color.colorTextPrimary))
            textViewAction.gravity = Gravity.CENTER
            textViewAction.background = ContextCompat.getDrawable(context, R.drawable.button_primary)
            textViewAction.textSize = 11.5f

            mSnackBar.setAction(actionText) { actionListener?.run() }
        }

        Handler().postDelayed({
            mSnackBar.show()
        }, (if(actionText != "") 500 else 0).toLong())
    }

    fun dayTimeGreeting(context: Context) : String {
        val cal = Calendar.getInstance()

        cal.time = Date()

        return when (cal.get(Calendar.HOUR_OF_DAY)) {
            in 12..16 -> context.getString(R.string.text_good_afternoon)
            in 17..20 -> context.getString(R.string.text_good_evening)
            in 21..23 -> context.getString(R.string.text_good_night)
            else -> context.getString(R.string.text_good_morning)
        }

    }

    fun compressBitmap(bitmap: Bitmap, quality:Int): Bitmap {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream)

        val byteArray = stream.toByteArray()
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }

    fun getImageUri(context: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(context.contentResolver, inImage, "HiDocFoto", null)
        return Uri.parse(path)
    }

}