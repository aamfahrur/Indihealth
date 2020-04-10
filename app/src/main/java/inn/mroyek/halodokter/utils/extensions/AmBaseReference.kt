package inn.mroyek.halodokter.utils.extensions

import com.google.firebase.firestore.FirebaseFirestore
import inn.mroyek.halodokter.struct.User

/*import com.google.firebase.firestore.FirebaseFirestore
import inn.mroyek.halodokter.struct.*
import inn.mroyek.halodokter.utils.commons.Constants.USER.DOCTOR
import inn.mroyek.halodokter.utils.components.slider.AmImage*/


fun FirebaseFirestore.user() = collection(User.tableName)
fun FirebaseFirestore.user(uid: String?) = collection(User.tableName).document(uid.toString())
/*

fun FirebaseFirestore.doctors() = collection(User.tableName).whereEqualTo(User.CREATOR.Fields.category, DOCTOR)

fun FirebaseFirestore.sliders() = collection(AmImage.tableName)

fun FirebaseFirestore.specialists() = collection(Specialist.tableName)

fun FirebaseFirestore.articles() = collection(Article.tableName)

fun FirebaseFirestore.location() = collection(Location.tableName)

fun FirebaseFirestore.favorite() = collection(Favorite.tableName)

fun FirebaseFirestore.chat() = collection(Chat.tableName)
fun FirebaseFirestore.chat(uid: String) = collection(Chat.tableName).document(uid)
fun FirebaseFirestore.conversation() = collection(Conversation.tableName)
*/
