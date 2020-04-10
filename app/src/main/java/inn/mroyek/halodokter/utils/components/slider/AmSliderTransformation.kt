package inn.mroyek.halodokter.utils.components.slider

import android.view.View
import androidx.viewpager.widget.ViewPager


class AmSliderTransformation : ViewPager.PageTransformer {
	override fun transformPage(page: View, position: Float) {
		page.translationX = -position*page.width
		page.alpha = 1-Math.abs(position)
	}
}