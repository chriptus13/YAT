package pt.isel.daw.daw_g06.controller.representation.input

import pt.isel.daw.daw_g06.model.Label

data class NameInputModel(
        val name: String
) {
    fun mapToLabel(pid: Int) = Label(project = pid, name = name)
}