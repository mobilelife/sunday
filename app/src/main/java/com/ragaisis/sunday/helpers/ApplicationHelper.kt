package com.ragaisis.sunday.helpers

import android.app.Activity
import android.graphics.Point

object ApplicationHelper {

    fun getScreenWidth(activity: Activity?): Int {
        val display = activity?.windowManager?.defaultDisplay
        val size = Point()
        display?.getSize(size)
        return size.x
    }
}