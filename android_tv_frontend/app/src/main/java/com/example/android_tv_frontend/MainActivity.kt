package com.example.android_tv_frontend

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentActivity

/**
 * PUBLIC_INTERFACE
 * MainActivity
 *
 * This is the entry point Activity for the Android TV app.
 * Shows a centered "Hello, TV!" message with Ocean Professional theme styling.
 * Parameters: none
 * Returns: none
 */
class MainActivity : FragmentActivity() {

    private lateinit var titleText: TextView
    private lateinit var subtitleText: TextView
    private lateinit var card: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        titleText = findViewById(R.id.title_text)
        subtitleText = findViewById(R.id.subtitle_text)
        card = findViewById(R.id.card)

        titleText.text = getString(R.string.hello_tv)
        subtitleText.text = getString(R.string.hello_tv_subtitle)

        // Initial subtle scale for modern feel
        card.scaleX = 0.98f
        card.scaleY = 0.98f
        card.alpha = 0f
        card.animate()
            .alpha(1f)
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(450)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .start()

        // Request focus for TV remote navigation
        card.isFocusable = true
        card.isFocusableInTouchMode = true
        card.setOnFocusChangeListener { v, hasFocus -> onCardFocusChanged(v, hasFocus) }
        card.requestFocus()
    }

    private fun onCardFocusChanged(view: View, hasFocus: Boolean) {
        val targetScale = if (hasFocus) 1.03f else 1.0f
        view.animate()
            .scaleX(targetScale)
            .scaleY(targetScale)
            .setDuration(160)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .start()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // Handle TV remote control inputs to give visual feedback on OK/Select
        return when (keyCode) {
            KeyEvent.KEYCODE_DPAD_CENTER,
            KeyEvent.KEYCODE_ENTER -> {
                performSelectAnimation(card)
                true
            }
            KeyEvent.KEYCODE_BACK -> {
                finish()
                true
            }
            else -> super.onKeyDown(keyCode, event)
        }
    }

    private fun performSelectAnimation(target: View) {
        target.animate()
            .scaleX(1.02f)
            .scaleY(1.02f)
            .setDuration(70)
            .withEndAction {
                target.animate()
                    .scaleX(1.03f)
                    .scaleY(1.03f)
                    .setDuration(120)
                    .start()
            }
            .start()
    }
}
