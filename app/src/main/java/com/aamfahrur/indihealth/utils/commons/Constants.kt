package com.aamfahrur.indihealth.utils.commons

import com.aamfahrur.indihealth.R


object Constants {
    object View {
        val Style = arrayOf("Regular", "Medium", "MediumItalic", "Bold", "BoldItalic", "Italic")
        val Name = arrayOf("fonts/GoogleSans-")
        val Input =
            arrayOf(R.drawable.input_primary, R.drawable.input_accent, R.drawable.input_secondary)

        const val Type: String = ".ttf"
    }

    object INTENT {
        object ACTIVITY {
            const val FROM_SIGNIN = "intentActivityFromLogin"
            const val FROM_SIGNUP = "intentActivityFromSignUp"
            const val FIRST_LOGIN = "intentLoginFirstLogin"
            const val USER = "intentUser"
            const val ARTICLE = "intentArticle"
            const val FAV = "intentFav"
        }

        object REQUEST {
            const val PERMISSION = 10
            const val IMAGE_PICKER = 11
        }
    }

    object ERROR {
        const val UNKNOWN = "unknown"
        const val USERNAME_EXISTS = "username"
    }

    object SHARED {
        const val pushToken = "SHARED_PUSH_TOKEN"
        const val userUid = "SHARED_UID"
    }

    object USER {
        const val DOCTOR = 1
        const val PATIENT = 2
        const val ADMIN = 3
    }

    object PATH {
        const val PROFILE_PHOTO = "profiles/"
    }

    object FRAGMENT {
        object HOME {
            const val NAME = "fragmentHome"
        }
        object PROFILE {
            const val NAME = "fragmentProfile"
        }
        object CHAT {
            const val NAME = "fragmentChat"
        }
    }
}