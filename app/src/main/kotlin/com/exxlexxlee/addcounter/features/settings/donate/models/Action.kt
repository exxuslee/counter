package com.exxlexxlee.addcounter.features.settings.donate.models

import android.net.Uri


sealed class Action {
    data class Donate(val uri: Uri) : Action()
}