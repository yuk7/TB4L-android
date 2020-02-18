package com.github.yuk7.extline

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(!isAccessibilitySettingsOn(this)) {
            startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
        }

    }

    private fun isAccessibilitySettingsOn(context: Context) : Boolean {
        val res = Settings.Secure.getInt(context.applicationContext.contentResolver, Settings.Secure.ACCESSIBILITY_ENABLED)
        if(res != 1) return false

        val serviceName = packageName + "/" + MyAccessibilityService::class.java.canonicalName
        val settingValue = Settings.Secure.getString(context.applicationContext.contentResolver, Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES)

        for(pkg in settingValue.split(":")) {
            if(pkg.contains(serviceName)) {
                return true
            }
        }
        return false
    }
}
