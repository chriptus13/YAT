package pt.isel.daw.daw_g06.service.implementation

import org.springframework.stereotype.Service
import pt.isel.daw.daw_g06.model.Label
import pt.isel.daw.daw_g06.repository.contract.ILabelRepository
import pt.isel.daw.daw_g06.repository.contract.IProjectRepository
import pt.isel.daw.daw_g06.repository.implementation.IssueRepository
import pt.isel.daw.daw_g06.service.contract.ILabelService

@Service
class LabelService(
        private val projectRepository: IProjectRepository,
        private val issueRepository: IssueRepository,
        private val labelRepository: ILabelRepository
) : ILabelService {
    override fun addLabelToProject(uid: Int, label: Label) {
        projectRepository.validateMemberInProject(uid, label.project)
        labelRepository.addProjectLabel(label.project, label.name)
    }

    override fun readProjectLabels(pid: Int): List<Label> {
        projectRepository.readProject(pid) // Validate project existence
        return labelRepository.readProjectLabels(pid)
    }

    override fun removeProjectLabel(uid: Int, label: Label) {
        projectRepository.validateMemberInProject(uid, label.project)
        labelRepository.removeProjectLabel(label.project, label.name)
    }

    override fun addLabelToIssue(uid: Int, iid: Int, label: Label) {
        projectRepository.validateMemberInProject(uid, label.project)
        labelRepository.addIssueLabel(label.project, iid, label.name)
    }

    override fun readIssueLabels(pid: Int, iid: Int): List<Label> {
        issueRepository.readIssue(pid, iid) // Validate issue existence
        return labelRepository.readIssueLabels(pid, iid)
    }

    override fun removeIssueLabel(uid: Int, iid: Int, label: Label) {
        projectRepository.validateMemberInProject(uid, label.project)
        labelRepository.removeIssueLabel(label.project, iid, label.name)
    }


}