package com.sandkshnbh.Roge.service

import java.io.DataOutputStream
import java.io.IOException

object RootUtils {

    fun isRootAvailable(): Boolean {
        return try {
            val process = Runtime.getRuntime().exec("su -c ls")
            process.waitFor() == 0
        } catch (e: Exception) {
            false
        }
    }

    fun requestRoot(): Boolean {
        return try {
            val process = Runtime.getRuntime().exec("su")
            val os = DataOutputStream(process.outputStream)
            os.writeBytes("exit\n")
            os.flush()
            process.waitFor()
            process.exitValue() == 0
        } catch (e: IOException) {
            false
        } catch (e: InterruptedException) {
            false
        }
    }
}
