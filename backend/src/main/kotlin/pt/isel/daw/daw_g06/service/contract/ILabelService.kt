package pt.isel.daw.daw_g06.service.contract

import pt.isel.daw.daw_g06.model.Label

interface ILabelService {
    fun addLabelToProject(uid: Int, label: Label)
    fun readProjectLabels(pid: Int): List<Label>
    fun removeProjectLabel(uid: Int, label: Label)
    fun addLabelToIssue(uid: Int, iid: Int, label: Label)
    fun readIssueLabels(pid: Int, iid: Int): List<Label>
    fun removeIssueLabel(uid: Int, iid: Int, label: Label)


}
