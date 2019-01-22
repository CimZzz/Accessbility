package test.test.test.accessbility

import android.app.Application

/**
 *  Anchor : Create by CimZzz
 *  Time : 2019/1/22 19:57:08
 *  Project : taoke_android
 *  Since Version : Alpha
 */
class CustomApplication: Application() {
    companion object {
        lateinit var application: CustomApplication
    }

    override fun onCreate() {
        application = this
        super.onCreate()
    }
}