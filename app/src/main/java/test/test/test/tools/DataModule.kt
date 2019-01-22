package test.test.test.tools

import android.content.Context
import test.test.test.accessbility.CustomApplication

/**
 *  Anchor : Create by CimZzz
 *  Time : 2019/1/22 19:56:29
 *  Project : taoke_android
 *  Since Version : Alpha
 */
object DataModule {
    private val sharedPreference = CustomApplication.application.getSharedPreferences("Setting", Context.MODE_PRIVATE)


    fun isOpenAutoInput() : Boolean = sharedPreference.getBoolean("isAutoInput", false)
    fun setAutoInput(isAutoInput: Boolean) {
        sharedPreference.edit().putBoolean("isAutoInput", isAutoInput).apply()
    }

    fun getAutoInputTxt(): String? = sharedPreference.getString("autoInputTxt", null)

    fun setAutoInputTxt(txt: String?) {
        sharedPreference.edit().putString("autoInputTxt", txt).apply()
    }
}