package pt.isel.daw.daw_g06.controller.representation.input

import pt.isel.daw.daw_g06.model.Issue

data class IssueInputModel(
        val name: String,
        val description: String
) {
    fun mapToIssue(pid: Int) = Issue(project = pid, name = name, description = description)
    fun mapToIssue(pid: Int, iid: Int) = Issue(id = iid, project = pid, name = name, description = description)
}
