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
    }

    var fanBtnNode: AccessibilityNodeInfo? = null
    var followBtnNode: AccessibilityNodeInfo? = null
    var closeBtnNode: AccessibilityNodeInfo? = null

    override fun isViewFromThis(clsName: String): Boolean = clsName == VIEW

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
    }

    override fun enter(vararg params: Any) {
        fanBtnNode = findFirstNode(CLOSE_BTN_ID)
        followBtnNode = findFirstNode(FAN_ID)
        closeBtnNode = findFirstNode(FOLLOW_ID)


    }

    override fun quit() {
    }
}