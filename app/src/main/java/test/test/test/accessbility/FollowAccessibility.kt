package test.test.test.accessbility

import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import test.test.test.tools.CountDownTimer

/**
 *  Anchor : Create by CimZzz
 *  Time : 2019/1/18 20:08:06
 *  Project : taoke_android
 *  Since Version : Alpha
 */
class FollowAccessibility(service: SubscribeServices) : BaseAccessibility(service) {

    companion object {
        const val SCROLL_TIME = 100L
        const val VIEW = "com.yunge.mtoilets.ui.account.activity.FollowListActivity"
        const val LINEAR_LAYOUT = "android.widget.LinearLayout"
        const val TITLE_ID = "com.yunge.mtoilets:id/tv_activity_title"
        const val LIST_ID = "com.yunge.mtoilets:id/rv_content"
        const val IMG_ID = "com.yunge.mtoilets:id/iv_follow"
        const val NAME_ID = "com.yunge.mtoilets:id/tv_name"
    }

    private var isMine: Boolean = false
    private var countDownTimer: CountDownTimer? = null



    override fun isViewFromThis(clsName: String): Boolean = clsName == VIEW

    override fun enter(vararg params: Any) {
        isMine = findFirstNode(TITLE_ID)?.text?.startsWith("我的关注")?:false
        if(isMine)
            startTimer()
    }

    override fun quit() {
        if(isMine)
            closeTimer()
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        when (event.eventType) {
            AccessibilityEvent.TYPE_VIEW_SCROLLED -> {
                if(isMine)
                    startTimer()
            }
        }
    }


    private fun startTimer() {
        closeTimer()
        countDownTimer = CountDownTimer(SCROLL_TIME, SCROLL_TIME) { isFinish->
            val listViewNodeList = service.rootInActiveWindow.findAccessibilityNodeInfosByViewId(LIST_ID) ?: return@CountDownTimer

            if (listViewNodeList.size == 0)
                return@CountDownTimer

            val listViewNode = listViewNodeList[0]

            if(isFinish) {
                (0 until listViewNode.childCount).forEach {
                    val childNode = listViewNode.getChild(it)
                    if (childNode.className == LINEAR_LAYOUT) {
                        val nameNode = findFirstNode(NAME_ID, childNode)?:return@forEach
                        val imgNode = findFirstNode(IMG_ID, childNode)?:return@forEach

                        val orgIdTxt = nameNode.text
                        if (orgIdTxt.isNullOrEmpty() || !orgIdTxt.startsWith("ID:"))
                            return@forEach

                        val id = orgIdTxt.substring(3, orgIdTxt.length)
                        service.dbHelper.checkExists(id)
                    }
                }
                if(listViewNode.childCount != 0)
                    listViewNode.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)
                else startTimer()
            }
        }
        countDownTimer?.start()
    }

    private fun closeTimer() {
        countDownTimer?.cancel()
        countDownTimer = null
    }
}