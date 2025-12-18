package com.sandkshnbh.Roge.service

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log

class MotionGestureManager(
    private val context: Context,
    private val onProximityNear: () -> Unit
) : SensorEventListener {

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)

    fun startListening() {
        proximitySensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    fun stopListening() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            if (it.sensor.type == Sensor.TYPE_PROXIMITY) {
                handleProximity(it)
            }
        }
    }

    private fun handleProximity(event: SensorEvent) {
        val distance = event.values[0]
        // Revert to fixed threshold (usually 0.0 is near, and maximumRange is far)
        if (distance < (proximitySensor?.maximumRange ?: 5f)) {
            Log.d(TAG, "Proximity Near detected")
            onProximityNear()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    companion object {
        private const val TAG = "MotionGestureManager"
    }
}
