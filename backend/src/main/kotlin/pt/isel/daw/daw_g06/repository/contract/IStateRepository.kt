package pt.isel.daw.daw_g06.repository.contract

import pt.isel.daw.daw_g06.model.State

interface IStateRepository {
    fun addProjectState(pid: Int, state: String)

    fun removeProjectState(pid: Int, state: String)

    fun readProjectStates(pid: Int): List<State>
}