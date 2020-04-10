package inn.mroyek.halodokter.utils.components.slider

/*

class AmSliderAdapter(private val context: Context, private val imageModelArrayList: List<AmImage>, private val listener: ArticleAdapterInterface) : PagerAdapter() {
	
	private val inflater: LayoutInflater = LayoutInflater.from(context)
	
	override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
		container.removeView(view as View)
	}
	
	override fun getCount(): Int {
		return imageModelArrayList.size
	}
	
	override fun instantiateItem(view: ViewGroup, position: Int): Any {
		val data = imageModelArrayList[position]
		val imageLayout = inflater.inflate(R.layout.component_slider, view, false)
		
		Utils().imageRound(imageLayout.image, data.image, context, ImageView.ScaleType.CENTER_CROP)
		
		imageLayout.labelTitle.text = data.title
		imageLayout.labelSubTitle.text = data.subTitle
		
		view.addView(imageLayout, 0)

		imageLayout.setOnClickListener {
			val article = Article(data.title, data.image, context.getString(R.string.app_name), data.subTitle)
			listener.onItemClick(position, article)
		}
		
		return imageLayout
	}
	
	override fun isViewFromObject(view: View, obj: Any): Boolean {
		return view == obj
	}
	
	override fun restoreState(state: Parcelable?, loader: ClassLoader?) {}
	
	override fun saveState(): Parcelable? {
		return null
	}
	
}*/
