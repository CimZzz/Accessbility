package test.test.test.accessbility

import android.os.Bundle
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import test.test.test.tools.CountDownTimer
import test.test.test.tools.DataModule

/**
 *  Anchor : Create by CimZzz
 *  Time : 2019/1/22 14:52:57
 *  Project : taoke_android
 *  Since Version : Alpha
 */
class ConversationGroupAccessibility(service: SubscribeServices) : BaseAccessibility(service) {

    companion object {
        const val SCROLL_TIME = 3000L
        const val VIEW = "com.yunge.mtoilets.ui.chat.activity.ConversationGroupActivity"
        const val SEND_PIC_ID = "com.yunge.mtoilets:id/iv_send_photo"
        const val EDIT_ID = "com.yunge.mtoilets:id/embedded_text_editor"
        const val SEND_ID = "com.yunge.mtoilets:id/bt_send"
    }

    private var countDownTimer: CountDownTimer? = null
    private var lastClickTime = -1L

    override fun isViewFromThis(clsName: String): Boolean = clsName == VIEW

    override fun onAccessibilityEvent(event: AccessibilityEvent) {

        when (event.eventType) {
            AccessibilityEvent.TYPE_VIEW_CLICKED -> {
                if(event.className == "android.widget.EditText" && event.eventTime - lastClickTime < 200) {
                    lastClickTime = -1
                    findFirstNode(SEND_PIC_ID)?.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                }
                else lastClickTime = event.eventTime
            }
        }
    }

    override fun enter(vararg params: Any) {
        startTimer()
    }

    override fun quit() {
        closeTimer()
    }


    private fun startTimer() {
        closeTimer()
        countDownTimer = CountDownTimer(ConversationGroupAccessibility.SCROLL_TIME, ConversationGroupAccessibility.SCROLL_TIME) {
            isFinish->
            Log.v("CimZzz", "tick")
            if(isFinish) {
                if (DataModule.isOpenAutoInput()) {
                    val editTxt = findFirstNode(EDIT_ID)
                    val sendBtn = findFirstNode(SEND_ID)
                    if (editTxt != null && sendBtn != null) {
                        val arguments = Bundle()
                        arguments.putCharSequence(
                            AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE,
                            DataModule.getAutoInputTxt()
                        )
                        editTxt.performAction(AccessibilityNodeInfo.ACTION_FOCUS)
                        editTxt.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments)

                        sendBtn.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                    }

                }
                startTimer()
            }
        }
        countDownTimer?.start()
    }

    private fun closeTimer() {
        countDownTimer?.cancel()
        countDownTimer = null
    }
}