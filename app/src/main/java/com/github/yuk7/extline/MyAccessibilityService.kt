package com.github.yuk7.extline

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Toast


class MyAccessibilityService : AccessibilityService() {
    override fun onInterrupt() {

    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        val source = event!!.source ?: return
        val iab_header_url_nodes = source.findAccessibilityNodeInfosByViewId("jp.naver.line.android:id/iab_header_url")
        if (iab_header_url_nodes.size > 0) {
            val parent = iab_header_url_nodes[0] as AccessibilityNodeInfo
            try {
                val url_text = parent.text.toString()

                val iab_header_close_nodes = source.findAccessibilityNodeInfosByViewId("jp.naver.line.android:id/iab_header_close")
                if (iab_header_close_nodes.size > 0) {
                    val parent =iab_header_close_nodes[0] as AccessibilityNodeInfo
                    parent.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                }

                Thread.sleep(200)
                val i = Intent(Intent.ACTION_VIEW, Uri.parse(url_text))
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                applicationContext.startActivity(i)

            } catch (e : Exception) {
                Log.e("ERR",Log.getStackTraceString(e))
            }
        }


    }

}