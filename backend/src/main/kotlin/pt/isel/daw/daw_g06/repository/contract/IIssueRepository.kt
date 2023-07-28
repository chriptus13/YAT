package pt.isel.daw.daw_g06.repository.contract

import pt.isel.daw.daw_g06.model.Issue

interface IIssueRepository {
    fun createIssue(uid: Int, issue: Issue): Int

    fun readIssues(pid: Int): List<Issue>

    fun readIssue(pid: Int, iid: Int): Issue

    fun updateIssue(issue: Issue)

    fun deleteIssue(pid: Int, iid: Int)
}