package test.test.test.tools

import android.widget.Toast
import test.test.test.accessbility.CustomApplication

/**
 *  Anchor : Create by CimZzz
 *  Time : 2019/1/22 20:04:22
 *  Project : taoke_android
 *  Since Version : Alpha
 */
object ToastUtils {
    private var toast: Toast? = null

    fun sendToast(obj: Any?) {
        toast?.cancel()

        toast = Toast.makeText(CustomApplication.application, obj.toString(), Toast.LENGTH_SHORT)
        toast?.show()
    }
}