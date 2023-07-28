package pt.isel.daw.daw_g06.repository.contract

import pt.isel.daw.daw_g06.model.Label

interface ILabelRepository {
    fun addProjectLabel(pid: Int, label: String)

    fun removeProjectLabel(pid: Int, label: String)

    fun readProjectLabels(pid: Int): List<Label>

    fun addIssueLabel(pid: Int, iid: Int, label: String)

    fun removeIssueLabel(pid: Int, iid: Int, label: String)

    fun readIssueLabels(pid: Int, iid: Int): List<Label>
}