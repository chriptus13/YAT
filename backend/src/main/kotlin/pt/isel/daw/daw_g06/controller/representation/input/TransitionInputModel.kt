package pt.isel.daw.daw_g06.controller.representation.input

import pt.isel.daw.daw_g06.model.Transition

data class TransitionInputModel(
        val startState: String,
        val endState: String
) {
    fun mapToTransition(pid: Int) = Transition(project = pid, startState = startState, endState = endState)
}
