package com.exxlexxlee.addcounter.ui.common

import android.util.Log
import com.exxlexxlee.addcounter.R

object Icons {
    val start = listOf(
        R.drawable.icon_0001,
        R.drawable.icon_0002,
        R.drawable.icon_0006,
        R.drawable.icon_0003,
        R.drawable.icon_0004,
        R.drawable.icon_0005,
        R.drawable.icon_0007,
        R.drawable.icon_0008,
    )
    val all = listOf(
        R.drawable.icon_0001,
        R.drawable.icon_0002,
        R.drawable.icon_0006,
        R.drawable.icon_0003,
        R.drawable.icon_0004,
        R.drawable.icon_0005,
        R.drawable.icon_0007,
        R.drawable.icon_0008,
        R.drawable.icon_0009,
    )

    fun icon(index: Int) = try {
        all[index]
    } catch (_: Exception) {
        Log.e("Icons", "icon: Index out of bounds $index, returning default icon.")
        all[0]
    }


}