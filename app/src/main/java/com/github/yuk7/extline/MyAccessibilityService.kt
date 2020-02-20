package com.github.yuk7.extline

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo


class MyAccessibilityService : AccessibilityService() {
    override fun onInterrupt() {

    }

    var lastWindowId = 0
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        val source = event!!.source ?: return

        val ihCloseNodes =
            source.findAccessibilityNodeInfosByViewId("jp.naver.line.android:id/iab_header_close")

        if (ihCloseNodes.size > 0) {
            if (ihCloseNodes[0].windowId == lastWindowId) {
                ihCloseNodes[0].performAction(AccessibilityNodeInfo.ACTION_CLICK)
                return
            }
            val ihUrlNodes =
                source.findAccessibilityNodeInfosByViewId("jp.naver.line.android:id/iab_header_url")
            if (ihUrlNodes.size > 0) {
                val urlText = (ihUrlNodes[0].text ?: "").toString()

                if (urlText.contains("http://") || urlText.contains("https://")) {
                    val i = Intent(Intent.ACTION_VIEW, Uri.parse(urlText))
                    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    applicationContext.startActivity(i)
                    lastWindowId = ihCloseNodes[0].windowId
                }
            }
        }
    }
}