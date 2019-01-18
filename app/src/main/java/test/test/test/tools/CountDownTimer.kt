package test.test.test.tools

import android.os.CountDownTimer

/**
 *  Anchor : Create by CimZzz
 *  Time : 2019/1/18 17:37:23
 *  Project : taoke_android
 *  Since Version : Alpha
 */
class CountDownTimer(millisInFuture: Long, countDownInterval: Long, val callback: (Boolean)->Unit) :
    CountDownTimer(millisInFuture, countDownInterval) {
    override fun onFinish() {
        callback(true)
    }

    override fun onTick(millisUntilFinished: Long) {
        callback(false)
    }
}