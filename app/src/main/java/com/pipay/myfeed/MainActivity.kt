package com.pipay.myfeed

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.commitNow
import kotlinx.coroutines.withTimeoutOrNull

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prepareStatusBar()
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val fragment = FeedFragment.newInstance()
            supportFragmentManager.commitNow {
                replace(R.id.fragmentContainerView, fragment, "feed")
            }
        }
    }

    private fun prepareStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = Color.parseColor("#80000000")
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }

    }

}
