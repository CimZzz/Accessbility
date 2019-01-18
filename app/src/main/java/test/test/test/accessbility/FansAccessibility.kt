package test.test.test.accessbility

import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import test.test.test.tools.CountDownTimer

/**
 *  Anchor : Create by CimZzz
 *  Time : 2019/1/18 18:23:50
 *  Project : taoke_android
 *  Since Version : Alpha
 */
class FansAccessibility(service: SubscribeServices) : BaseAccessibility(service) {

    companion object {
        const val SCROLL_TIME = 1000L
        const val INTERVAL_TIME = 100L
        const val PER_COUNT = 4
        const val VIEW = "com.yunge.mtoilets.ui.account.activity.FansListActivity"
        const val LINEAR_LAYOUT = "android.widget.LinearLayout"
        const val TITLE_ID = "com.yunge.mtoilets:id/tv_activity_title"
        const val LIST_ID = "com.yunge.mtoilets:id/rv_content"
        const val IMG_ID = "com.yunge.mtoilets:id/iv_follow"
        const val NAME_ID = "com.yunge.mtoilets:id/tv_name"
    }

    private var isMine: Boolean = false
    private var countDownTimer: CountDownTimer? = null
    private var startIndex: Int = 0

    override fun isViewFromThis(clsName: String): Boolean {
        return clsName == VIEW
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        when (event.eventType) {
            AccessibilityEvent.TYPE_VIEW_SCROLLED -> {
                startTimer()
            }
            else -> {
                Log.v("CimZzz", "other event type: ${event.eventType}")
            }
        }
    }

    override fun enter(vararg params: Any) {
        isMine = findFirstNode(FollowAccessibility.TITLE_ID)?.text?.startsWith("我的粉丝")?:false
        startTimer()
    }

    override fun quit() {
        closeTimer()
    }

    private fun startTimer() {
        closeTimer()
        startIndex = 0
        countDownTimer = CountDownTimer(SCROLL_TIME, INTERVAL_TIME) {isFinish->
            val listViewNodeList = service.rootInActiveWindow.findAccessibilityNodeInfosByViewId(LIST_ID) ?: return@CountDownTimer

            if (listViewNodeList.size == 0)
                return@CountDownTimer

            val listViewNode = listViewNodeList[0]

            if(isFinish) {
                if(listViewNode.childCount != 0)
                    listViewNode.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)
                else startTimer()
                return@CountDownTimer
            }
            else {
                val startIndex = this.startIndex
                var endIndex = this.startIndex + PER_COUNT


                if(listViewNode.childCount == 0)
                    return@CountDownTimer

                if(listViewNode.childCount < endIndex)
                    endIndex = listViewNode.childCount

                if(startIndex == endIndex)
                    return@CountDownTimer

                (startIndex until endIndex).forEach {
                    val childNode = listViewNode.getChild(it)
                    if (childNode.className == LINEAR_LAYOUT) {
                        val nameNodeList = childNode.findAccessibilityNodeInfosByViewId(NAME_ID) ?: return@forEach
                        val imgNodeList = childNode.findAccessibilityNodeInfosByViewId(IMG_ID) ?: return@forEach

                        if (nameNodeList.size == 0 || imgNodeList.size == 0)
                            return@forEach

                        val nameNode = nameNodeList[0]
                        val imgNode = imgNodeList[0]

                        val orgIdTxt = nameNode.text
                        if (orgIdTxt.isNullOrEmpty() || !orgIdTxt.startsWith("ID:"))
                            return@forEach

                        val id = orgIdTxt.substring(3, orgIdTxt.length)

                        if (!service.dbHelper.checkExists(id))
                            imgNode.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                    }
                }
                this.startIndex = endIndex
            }
        }
        countDownTimer?.start()
    }

    private fun closeTimer() {
        countDownTimer?.cancel()
        countDownTimer = null
    }
}