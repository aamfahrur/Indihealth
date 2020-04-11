package inn.mroyek.halodokter.struct

import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint

class Location (
    var uid: String,
    var location: GeoPoint = GeoPoint(0.0, 0.0),
    var createdOn: Timestamp = Timestamp.now()
) {
    companion object {
        const val tableName = "locations"

        object Fields {
            const val uid = "uid"
            const val location = "location"
            const val createdOn = "createdOn"
        }
    }
}