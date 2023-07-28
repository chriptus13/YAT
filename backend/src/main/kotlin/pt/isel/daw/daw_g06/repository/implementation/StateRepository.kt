package pt.isel.daw.daw_g06.repository.implementation

import org.springframework.dao.DataAccessException
import org.springframework.dao.DuplicateKeyException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import pt.isel.daw.daw_g06.model.State
import pt.isel.daw.daw_g06.repository.contract.IStateRepository
import pt.isel.daw.daw_g06.repository.exceptions.DatabaseAccessException
import pt.isel.daw.daw_g06.repository.exceptions.DatabaseExceptionCodes
import pt.isel.daw.daw_g06.repository.exceptions.InvalidOperationException
import pt.isel.daw.daw_g06.repository.exceptions.UniqueAttributeViolationException
import java.sql.ResultSet
import java.sql.SQLException

@Repository
class StateRepository(private val jdbcTemplate: JdbcTemplate) : IStateRepository {
    override fun addProjectState(pid: Int, state: String) {
        try {
            jdbcTemplate.queryForMap(Query.ADD_PROJECT_STATE, pid, state)
        } catch (e: DataAccessException) {
            when ((e.cause as SQLException).sqlState) {
                DatabaseExceptionCodes.INVALID_STATE ->
                    throw UniqueAttributeViolationException("State's name needs to be unique.")
                else -> throw DatabaseAccessException("Error accessing database.")
            }
        }
    }

    override fun removeProjectState(pid: Int, state: String) {
        try {
            jdbcTemplate.queryForMap(Query.REMOVE_PROJECT_STATE, pid, state)
        } catch (e: DataAccessException) {
            when ((e.cause as SQLException).sqlState) {
                DatabaseExceptionCodes.INVALID_STATE_REMOVE ->
                    throw InvalidOperationException("Cannot remove default states or initial state.")
                else -> throw DatabaseAccessException("Error accessing database.")
            }
        }
    }

    override fun readProjectStates(pid: Int): List<State> {
        try {
            return jdbcTemplate.query(Query.SELECT_PROJECT_STATES, arrayOf(pid), stateRM)
        } catch (e: DataAccessException) {
            throw DatabaseAccessException("Error accessing database.")
        }
    }

    private val stateRM: (ResultSet, Int) -> State = { rs, _ ->
        State(rs.getInt("project"), rs.getString("name"))
    }
}