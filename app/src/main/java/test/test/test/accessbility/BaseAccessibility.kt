package test.test.test.accessbility

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import java.util.*

/**
 *  Anchor : Create by CimZzz
 *  Time : 2019/1/18 18:21:51
 *  Project : taoke_android
 *  Since Version : Alpha
 */
abstract class BaseAccessibility(val service: SubscribeServices) {
    abstract fun isViewFromThis(clsName: String): Boolean
    abstract fun enter(vararg params: Any)
    abstract fun quit()
    abstract fun onAccessibilityEvent(event: AccessibilityEvent)


    fun printAllNode() {
        val pool = LinkedList<AccessibilityNodeInfo>()
        pool.push(service.rootInActiveWindow)
        while (pool.isNotEmpty()) {
            val childNode = pool.poll()
            Log.v("CimZzz", "$childNode")
            if(childNode.childCount > 0) {
                (0 until childNode.childCount).forEach {
                    pool.push(childNode.getChild(it))
                }
            }
        }
    }


    fun findFirstNode(id: String, baseNode: AccessibilityNodeInfo = service.rootInActiveWindow): AccessibilityNodeInfo? {
        val nodeList = baseNode.findAccessibilityNodeInfosByViewId(id)?:return null
        if(nodeList.size == 0)
            return null

        return nodeList[0];
    }
}