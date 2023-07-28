package pt.isel.daw.daw_g06.service.contract

import pt.isel.daw.daw_g06.model.Issue

interface IIssueService {
    fun create(uid: Int, issue: Issue): Int
    fun readIssues(pid: Int): List<Issue>
    fun readIssue(pid: Int, iid: Int): Issue
    fun updateIssue(uid: Int, issue: Issue)
    fun updateIssueName(uid: Int, pid: Int, iid: Int, name: String)
    fun updateIssueDescription(uid: Int, pid: Int, iid: Int, description: String)
    fun updateIssueState(uid: Int, pid: Int, iid: Int, state: String)
    fun deleteIssue(uid: Int, pid: Int, iid: Int)
}