package test.test.test.accessbility

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import test.test.test.database.DBHelper

/**
 *  Anchor : Create by CimZzz
 *  Time : 2019/1/18 12:16:04
 *  Project : taoke_android
 *  Since Version : Alpha
 */
class SubscribeServices() : AccessibilityService() {
    var otherParams: Any? = null
    val dbHelper = DBHelper(this)

    private val accessibilityList: List<BaseAccessibility> = arrayListOf(
        FansAccessibility(this),
        UserCenterAccessibility(this),
        FollowAccessibility(this),
        ConversationGroupAccessibility(this)
    )
    var currentAccessibility: BaseAccessibility? = null


    override fun onInterrupt() {
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        when (event.eventType) {
            AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED -> {
                val current = currentAccessibility
                if(current != null) {
                    if(current.isViewFromThis(event.className.toString())) {
                        current.onAccessibilityEvent(event)
                        return
                    }
                    else current.quit()
                }
                Log.v("CimZzz", "切换至 ${event.className}")
                currentAccessibility = null
                for(accessibility in accessibilityList) {
                    if(accessibility.isViewFromThis(event.className.toString())) {
                        currentAccessibility = accessibility
                        val params = otherParams
                        otherParams = null
                        accessibility.enter(params?:Unit)
                        return
                    }
                }
            }
            else -> currentAccessibility?.onAccessibilityEvent(event) ?: Log.v("CimZzz", "${event.eventType}")
        }
    }
}