package pt.isel.daw.daw_g06.model

data class Transition(
        val id: Int = -1,
        val project: Int = -1,
        val startState: String,
        val endState: String
)