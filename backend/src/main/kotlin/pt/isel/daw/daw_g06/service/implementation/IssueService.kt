package pt.isel.daw.daw_g06.service.implementation

import org.springframework.stereotype.Service
import pt.isel.daw.daw_g06.model.Issue
import pt.isel.daw.daw_g06.repository.contract.IIssueRepository
import pt.isel.daw.daw_g06.repository.contract.IProjectRepository
import pt.isel.daw.daw_g06.repository.contract.ITransitionRepository
import pt.isel.daw.daw_g06.service.contract.IIssueService

@Service
class IssueService(
        private val projectRepository: IProjectRepository,
        private val issueRepository: IIssueRepository,
        private val transitionRepository: ITransitionRepository
) : IIssueService {
    override fun create(uid: Int, issue: Issue): Int {
        projectRepository.validateMemberInProject(uid, issue.project)
        return issueRepository.createIssue(uid, issue)
    }

    override fun readIssues(pid: Int): List<Issue> {
        projectRepository.readProject(pid) // Validate project existence
        return issueRepository.readIssues(pid)
    }

    override fun readIssue(pid: Int, iid: Int): Issue = issueRepository.readIssue(pid, iid)

    override fun updateIssue(uid: Int, issue: Issue) {
        projectRepository.validateMemberInProject(uid, issue.project)
        issueRepository.updateIssue(issue)
    }

    override fun updateIssueName(uid: Int, pid: Int, iid: Int, name: String) {
        projectRepository.validateMemberInProject(uid, pid)
        val old = issueRepository.readIssue(pid, iid)
        issueRepository.updateIssue(old.replacing(name = name))
    }

    override fun updateIssueDescription(uid: Int, pid: Int, iid: Int, description: String) {
        projectRepository.validateMemberInProject(uid, pid)
        val old = issueRepository.readIssue(pid, iid)
        issueRepository.updateIssue(old.replacing(description = description))
    }

    override fun updateIssueState(uid: Int, pid: Int, iid: Int, state: String) {
        projectRepository.validateMemberInProject(uid, pid)
        val old = issueRepository.readIssue(pid, iid)
        issueRepository.updateIssue(old.replacing(state = state))
    }

    override fun deleteIssue(uid: Int, pid: Int, iid: Int) {
        projectRepository.validateMemberInProject(uid, pid)
        issueRepository.deleteIssue(pid, iid)
    }
}