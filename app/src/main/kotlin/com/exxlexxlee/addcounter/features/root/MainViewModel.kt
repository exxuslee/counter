package com.exxlexxlee.addcounter.features.root

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.exxlexxlee.addcounter.features.root.models.Action
import com.exxlexxlee.addcounter.features.root.models.MainEvent
import com.exxlexxlee.addcounter.features.root.models.ViewState
import com.exxlexxlee.addcounter.navigation.Routes
import com.exxlexxlee.addcounter.managers.PuzzleWorkManager
import com.exxlexxlee.addcounter.ui.common.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    val workManager: PuzzleWorkManager,
) : BaseViewModel<ViewState, Action, MainEvent>(
    initialState = ViewState(
        initialRoute = Routes.GameRoute.route,
    )
) {

    init {
        viewModelScope.launch(Dispatchers.Default) {
            workManager.startWork()
            workManager.observeProgress().collect {
                if (it == "done") {
                    Log.d("PuzzleWorker", "Puzzle $it")
                    workManager.startWork()
                }
            }
        }
    }

    override fun obtainEvent(viewEvent: MainEvent) {
        when (viewEvent) {
            is MainEvent.MainRoute -> true
            MainEvent.Dice -> viewAction = Action.Dice
        }

    }

}