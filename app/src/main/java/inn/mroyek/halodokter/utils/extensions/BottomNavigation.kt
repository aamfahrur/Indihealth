package inn.mroyek.halodokter.utils.extensions

import com.google.android.material.bottomnavigation.BottomNavigationView


infix fun BottomNavigationView.selectedListener(listener: BottomNavigationView.OnNavigationItemSelectedListener) {
	setOnNavigationItemSelectedListener(listener)
}