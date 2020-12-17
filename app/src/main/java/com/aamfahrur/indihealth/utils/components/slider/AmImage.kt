package com.aamfahrur.indihealth.utils.components.slider

class AmImage (var image: String = "", var title: String = "", var subTitle: String = "") {
    companion object {
        const val tableName = "ads"

        object Fields {
            const val image = "image"
            const val title = "title"
            const val subTitle = "subTitle"
        }
    }
}