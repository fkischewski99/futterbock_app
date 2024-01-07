package view.event.homescreen

import data.Querys
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import model.Event


data class State(
    var pastEvents: List<Event> = ArrayList(),
    var upcommingEvents: MutableList<Event> = ArrayList()
)

class ViewModelEventOverview(private val querys: Querys) : ViewModel() {
    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    init {
        _state.update {
            it.copy(
                pastEvents = querys.getPastEvents(),
                upcommingEvents = querys.getCurrentEvents()
            )
        }
    }

    fun onDeleteClick(event: Event) {
        //TODO delete Item from DB

        _state.update {
            it.copy(
                upcommingEvents = ArrayList()
            )
        }


    }

}