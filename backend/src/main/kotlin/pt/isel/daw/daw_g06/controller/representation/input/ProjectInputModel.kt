package pt.isel.daw.daw_g06.controller.representation.input

import pt.isel.daw.daw_g06.model.Project

data class ProjectInputModel(
        val name: String,
        val description: String?,
        val initialState: String?
) {
    fun mapToProject() = Project(name = name, description = description, initialState = initialState)
    fun mapToProject(pid: Int) = Project(pid, name, description, initialState)
}