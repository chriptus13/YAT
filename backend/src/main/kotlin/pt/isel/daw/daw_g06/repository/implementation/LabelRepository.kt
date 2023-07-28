package pt.isel.daw.daw_g06.repository.implementation

import org.springframework.dao.DataAccessException
import org.springframework.dao.DuplicateKeyException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import pt.isel.daw.daw_g06.model.Label
import pt.isel.daw.daw_g06.repository.contract.ILabelRepository
import pt.isel.daw.daw_g06.repository.exceptions.DatabaseAccessException
import pt.isel.daw.daw_g06.repository.exceptions.DatabaseExceptionCodes
import pt.isel.daw.daw_g06.repository.exceptions.EntityNotFoundException
import pt.isel.daw.daw_g06.repository.exceptions.UniqueAttributeViolationException
import java.sql.ResultSet
import java.sql.SQLException

@Repository
class LabelRepository(private val jdbcTemplate: JdbcTemplate) : ILabelRepository {
    override fun addProjectLabel(pid: Int, label: String) {
        try {
            jdbcTemplate.update(Query.INSERT_PROJECT_LABEL, pid, label)
        } catch (ignored: DuplicateKeyException) {
            throw UniqueAttributeViolationException("Label's name needs to be unique.")
        } catch (e: DataAccessException) {
            throw DatabaseAccessException("Error accessing database.")
        }
    }

    override fun removeProjectLabel(pid: Int, label: String) {
        try {
            jdbcTemplate.update(Query.DELETE_PROJECT_LABEL, pid, label)
        } catch (e: DataAccessException) {
            throw DatabaseAccessException("Error accessing database.")
        }
    }

    override fun readProjectLabels(pid: Int): List<Label> {
        try {
            return jdbcTemplate.query(Query.SELECT_PROJECT_LABELS, arrayOf(pid), labelRM)
        } catch (e: DataAccessException) {
            throw DatabaseAccessException("Error accessing database.")
        }
    }

    override fun addIssueLabel(pid: Int, iid: Int, label: String) {
        try {
            jdbcTemplate.queryForMap(Query.INSERT_ISSUE_LABEL, iid, pid, label)
        } catch (e: DataAccessException) {
            when ((e.cause as SQLException).sqlState) {
                DatabaseExceptionCodes.INVALID_PROJECT_LABEL ->
                    throw EntityNotFoundException("Project label with name {$label} not found.")
                else -> throw DatabaseAccessException("Error accessing database.")
            }
        } catch (ignored: DuplicateKeyException) {
        }
    }

    override fun removeIssueLabel(pid: Int, iid: Int, label: String) {
        try {
            jdbcTemplate.update(Query.DELETE_ISSUE_LABEL, iid, pid, label)
        } catch (e: DataAccessException) {
            throw DatabaseAccessException("Error accessing database.")
        }
    }

    override fun readIssueLabels(pid: Int, iid: Int): List<Label> {
        try {
            return jdbcTemplate.query(Query.SELECT_ISSUE_LABELS, arrayOf(pid, iid), labelRM)
        } catch (e: DataAccessException) {
            throw DatabaseAccessException("Error accessing database.")
        }
    }

    private val labelRM: (ResultSet, Int) -> Label = { rs, _ ->
        Label(rs.getInt("id"), rs.getInt("project"), rs.getString("name"))
    }
}