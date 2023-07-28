package pt.isel.daw.daw_g06.repository.implementation

import org.springframework.dao.DataAccessException
import org.springframework.dao.IncorrectResultSizeDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.queryForObject
import org.springframework.stereotype.Repository
import pt.isel.daw.daw_g06.model.Issue
import pt.isel.daw.daw_g06.repository.contract.IIssueRepository
import pt.isel.daw.daw_g06.repository.exceptions.*
import java.sql.ResultSet
import java.sql.SQLException

@Repository
class IssueRepository(private val jdbcTemplate: JdbcTemplate) : IIssueRepository {
    override fun createIssue(uid: Int, issue: Issue): Int {
        try {
            return jdbcTemplate.queryForObject(Query.INSERT_ISSUE,
                    arrayOf(issue.project, uid, issue.name, issue.description))!!
        } catch (e: DataAccessException) {
            when ((e.cause as SQLException).sqlState) {
                DatabaseExceptionCodes.PROJECT_INITIAL_STATE_NOT_SET ->
                    throw InvalidOperationException("Initial state for project with id ${issue.project} not set.")
                else -> throw DatabaseAccessException("Error accessing database.")
            }
        }
    }

    override fun readIssues(pid: Int): List<Issue> {
        try {
            return jdbcTemplate.query(Query.SELECT_ISSUES, arrayOf(pid), issueRM)
        } catch (e: DataAccessException) {
            throw DatabaseAccessException("Error accessing database.")
        }
    }

    override fun readIssue(pid: Int, iid: Int): Issue {
        try {
            return jdbcTemplate.queryForObject(Query.SELECT_ISSUE, arrayOf(pid, iid), issueRM)!!
        } catch (e: IncorrectResultSizeDataAccessException) {
            throw EntityNotFoundException("Issue not found.")
        } catch (e: DataAccessException) {
            throw DatabaseAccessException("Error accessing database.")
        }
    }

    override fun updateIssue(issue: Issue) {
        try {
            jdbcTemplate.update(Query.UPDATE_ISSUE,
                    issue.name, issue.description, issue.state, issue.project, issue.id)
        } catch (e: DataAccessException) {
            when ((e.cause as SQLException).sqlState) {
                DatabaseExceptionCodes.INVALID_TRANSITION ->
                    throw InvalidTransitionException("Transition to state {${issue.state}} is not possible.")
                DatabaseExceptionCodes.INVALID_STATE ->
                    throw InvalidOperationException("Invalid state. State [${issue.state}] does not exist.")
                else -> throw DatabaseAccessException("Error accessing database.")
            }
        }
    }

    override fun deleteIssue(pid: Int, iid: Int) {
        try {
            jdbcTemplate.update(Query.DELETE_ISSUE, pid, iid)
        } catch (e: DataAccessException) {
            throw DatabaseAccessException("Error accessing database.")
        }
    }

    private val issueRM: (ResultSet, Int) -> Issue = { rs, _ ->
        Issue(rs.getInt("id"), rs.getInt("project"),
                rs.getString("creator"), rs.getString("name"),
                rs.getString("description"), rs.getTimestamp("created"),
                rs.getTimestamp("closed"), rs.getString("state")
        )
    }
}