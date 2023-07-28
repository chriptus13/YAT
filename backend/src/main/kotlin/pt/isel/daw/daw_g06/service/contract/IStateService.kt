package pt.isel.daw.daw_g06.service.contract

import pt.isel.daw.daw_g06.model.State

interface IStateService {
    fun addState(uid: Int, state: State)
    fun readStates(pid: Int): List<State>
    fun removeState(uid: Int, state: State)
}