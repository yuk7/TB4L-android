package com.github.yuk7.extline

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<TextView>(R.id.textView).text =
            getString(R.string.accessibility_disabled_message)
        findViewById<Button>(R.id.btnOpenSettings).text =
            getString(R.string.accessibility_open_settings)

        findViewById<Button>(R.id.btnOpenSettings).setOnClickListener {
            startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
        }
    }

    override fun onResume() {
        super.onResume()
        if (isAccessibilitySettingsOn(this)) {
            findViewById<TextView>(R.id.textView).text =
                getString(R.string.accessibility_enabled_message)
            findViewById<Button>(R.id.btnOpenSettings).isVisible = false
        }
    }

    private fun isAccessibilitySettingsOn(context: Context): Boolean {
        val res = Settings.Secure.getInt(
            context.applicationContext.contentResolver,
            Settings.Secure.ACCESSIBILITY_ENABLED
        )
        if (res != 1) return false

        val serviceName = packageName + "/" + MyAccessibilityService::class.java.canonicalName
        val settingValue = Settings.Secure.getString(
            context.applicationContext.contentResolver,
            Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
        )

        for (pkg in settingValue.split(":")) {
            if (pkg.contains(serviceName)) {
                return true
            }
        }
        return false
    }
}
