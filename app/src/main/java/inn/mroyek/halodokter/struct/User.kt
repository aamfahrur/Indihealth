package inn.mroyek.halodokter.struct

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint
import inn.mroyek.halodokter.utils.commons.Constants
import java.util.*

class User(
    var uid: String? = "",
    var fullName: String? = "",
    var email: String? = "",
    var specialist: String? = "",
    var photoUrl: String? = "",
    var dateOfBirth: Date = Date(),
    var blood: String? = "",
    var sex: String? = "",
    var phoneNumber: String? = "",
    var ration: Float = 0.0f,
    var emailVerified: Boolean = false,
    var category: Int = Constants.USER.PATIENT,
    var location: GeoPoint = GeoPoint(0.0,0.0),
    var pushId: String? = "",
    var onlineUser: Boolean = false,
    var lastUpdated: Timestamp? = Timestamp.now()
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        Date(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readFloat(),
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        GeoPoint(0.0, 0.0),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readParcelable(Timestamp::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uid)
        parcel.writeString(fullName)
        parcel.writeString(email)
        parcel.writeString(specialist)
        parcel.writeString(photoUrl)
        parcel.writeString(blood)
        parcel.writeString(sex)
        parcel.writeString(phoneNumber)
        parcel.writeFloat(ration)
        parcel.writeByte(if (emailVerified) 1 else 0)
        parcel.writeInt(category)
        parcel.writeString(pushId)
        parcel.writeByte(if (onlineUser) 1 else 0)
        parcel.writeParcelable(lastUpdated, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }

        const val tableName = "users"

        object Fields {
            const val uid = "uid"
            const val fullName = "fullName"
            const val email = "email"
            const val specialist = "specialist"
            const val photoUrl = "photoUrl"
            const val dateOfBirth = "dateOfBirth"
            const val blood = "blood"
            const val sex = "sex"
            const val phoneNumber = "phoneNumber"
            const val ration = "ration"
            const val emailVerified = "emailVerified"
            const val category = "category"
            const val location = "location"
            const val pushId = "pushId"
            const val onlineUser = "onlineUser"
            const val lastUpdated = "lastUpdated"
        }
    }

}