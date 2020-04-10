package inn.mroyek.halodokter.utils.extensions

import java.text.SimpleDateFormat
import java.util.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
fun String.toDate(): Date {
    return SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse(this)
}

fun Date.toFormat(): String {
    return SimpleDateFormat("d MMM yyyy", Locale.getDefault()).format(this)
}

fun Date.toInputFormat(): String {
    return SimpleDateFormat("d/M/yyyy", Locale.getDefault()).format(this)
}

fun Date.toTime(): String {
    return SimpleDateFormat("HH:mm", Locale.getDefault()).format(this)
}
