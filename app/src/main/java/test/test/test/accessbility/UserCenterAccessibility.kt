package test.test.test.accessbility

import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

/**
 *  Anchor : Create by CimZzz
 *  Time : 2019/1/18 19:34:57
 *  Project : taoke_android
 *  Since Version : Alpha
 */
class UserCenterAccessibility(service: SubscribeServices) : BaseAccessibility(service) {
    companion object {
        const val VIEW = "com.yunge.mtoilets.ui.account.activity.UserCenterActivity"
        const val CLOSE_BTN_ID = "com.yunge.mtoilets:id/close_activity"
        const val FAN_ID = "com.yunge.mtoilets:id/ll_fans"
        const val FOLLOW_ID = "com.yunge.mtoilets:id/ll_follow"
        const val TITLE_ID = "com.yunge.mtoilets:id/tv_list_title"
        const val HEADER_ID = "com.yunge.mtoilets:id/iv_user_head"
    }

    var isMine = false
    var fanBtnNode: AccessibilityNodeInfo? = null
    var followBtnNode: AccessibilityNodeInfo? = null
    var closeBtnNode: AccessibilityNodeInfo? = null
    var titleNode: AccessibilityNodeInfo? = null

    override fun isViewFromThis(clsName: String): Boolean = clsName == VIEW

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        when(event.eventType) {
            AccessibilityEvent.TYPE_VIEW_CLICKED-> {
                if(event.text.contains("关注") && isMine)
                    service.otherParams = "我的"
            }
        }
    }

    override fun enter(vararg params: Any) {
        fanBtnNode = findFirstNode(CLOSE_BTN_ID)
        followBtnNode = findFirstNode(FAN_ID)
        closeBtnNode = findFirstNode(FOLLOW_ID)
        titleNode = findFirstNode(TITLE_ID)
        isMine = titleNode?.text?.toString().equals("所有话题")

        printAllNode()
    }

    override fun quit() {

    }
}