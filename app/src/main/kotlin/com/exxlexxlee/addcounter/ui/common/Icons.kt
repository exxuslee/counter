package com.exxlexxlee.addcounter.ui.common

import android.util.Log
import com.exxlexxlee.addcounter.R

object Icons {

    val all = listOf(
        R.drawable.ic_baseline_sentiment_satisfied_alt_24,
        R.drawable.ic_baseline_sentiment_very_dissatisfied_24,
        R.drawable.outline_ads_click_24,
        R.drawable.outline_bar_chart_24,
        R.drawable.outline_barefoot_24,
        R.drawable.outline_bolt_24,
        R.drawable.outline_brush_24,
        R.drawable.outline_chess_pawn_24,
        R.drawable.outline_counter_0_24,
        R.drawable.outline_counter_1_24,
        R.drawable.outline_counter_2_24,
        R.drawable.outline_counter_3_24,
        R.drawable.outline_counter_4_24,
        R.drawable.outline_counter_5_24,
        R.drawable.outline_counter_6_24,
        R.drawable.outline_counter_7_24,
        R.drawable.outline_counter_8_24,
        R.drawable.outline_counter_9_24,
        R.drawable.outline_deceased_24,
        R.drawable.outline_dice_1_24,
        R.drawable.outline_dice_2_24,
        R.drawable.outline_dice_3_24,
        R.drawable.outline_dice_4_24,
        R.drawable.outline_dice_5_24,
        R.drawable.outline_dice_6_24,
        R.drawable.outline_earthquake_24,
        R.drawable.outline_eco_24,
        R.drawable.outline_emoji_objects_24,
        R.drawable.outline_exercise_24,
        R.drawable.outline_explosion_24,
        R.drawable.outline_flag_24,
        R.drawable.outline_key_24,
        R.drawable.outline_local_cafe_24,
        R.drawable.outline_pets_24,
        R.drawable.outline_pill_24,
        R.drawable.outline_raven_24,
        R.drawable.outline_science_24,
        R.drawable.outline_skull_24,
        R.drawable.outline_transition_dissolve_24,
        R.drawable.outline_trophy_24,
        R.drawable.outline_work_24,
    )

    fun icon(index: Int) = try {
        all[index]
    } catch (_: Exception) {
        Log.e("Icons", "icon: Index out of bounds $index, returning default icon.")
        all[0]
    }


}