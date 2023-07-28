package pt.isel.daw.daw_g06.controller.representation.input

import pt.isel.daw.daw_g06.model.State

data class StateInputModel(
        val name: String
) {
    fun mapToState(pid: Int) = State(pid, name)
}
