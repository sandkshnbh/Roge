package com.sandkshnbh.Roge.service

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.util.Log
import android.view.KeyEvent
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast

class RogeAccessibilityService : AccessibilityService() {

    private var motionGestureManager: MotionGestureManager? = null

    override fun onServiceConnected() {
        super.onServiceConnected()
        Log.d(TAG, "Roge Accessibility Service Connected")
        
        motionGestureManager = MotionGestureManager(
            this,
            onProximityNear = {
                handleProximityAction()
            }
        )
        motionGestureManager?.startListening()
    }

    private fun handleProximityAction() {
        val prefs = getSharedPreferences("roge_prefs", Context.MODE_PRIVATE)
        val action = prefs.getString("sensor_action", "notifications") ?: "notifications"
        
        Log.d(TAG, "Triggering action: $action")
        
        when (action) {
            "notifications" -> performGlobalAction(GLOBAL_ACTION_NOTIFICATIONS)
            "quick_settings" -> performGlobalAction(GLOBAL_ACTION_QUICK_SETTINGS)
            "home" -> performGlobalAction(GLOBAL_ACTION_HOME)
            "back" -> performGlobalAction(GLOBAL_ACTION_BACK)
            "recents" -> performGlobalAction(GLOBAL_ACTION_RECENTS)
            "power_menu" -> performGlobalAction(GLOBAL_ACTION_POWER_DIALOG)
            "lock_screen" -> performGlobalAction(GLOBAL_ACTION_LOCK_SCREEN)
            "screenshot" -> performGlobalAction(GLOBAL_ACTION_TAKE_SCREENSHOT)
            else -> performGlobalAction(GLOBAL_ACTION_NOTIFICATIONS)
        }
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
    }

    override fun onInterrupt() {
        Log.d(TAG, "Roge Accessibility Service Interrupted")
        motionGestureManager?.stopListening()
    }
    
    override fun onDestroy() {
        super.onDestroy()
        motionGestureManager?.stopListening()
    }

    override fun onKeyEvent(event: KeyEvent): Boolean {
        return super.onKeyEvent(event)
    }

    companion object {
        private const val TAG = "RogeAccessibilityService"
    }
}
