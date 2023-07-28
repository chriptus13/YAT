package pt.isel.daw.daw_g06.repository.implementation

import org.springframework.dao.DataAccessException
import org.springframework.dao.IncorrectResultSizeDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.queryForObject
import org.springframework.stereotype.Repository
import pt.isel.daw.daw_g06.model.Transition
import pt.isel.daw.daw_g06.repository.contract.ITransitionRepository
import pt.isel.daw.daw_g06.repository.exceptions.*
import java.sql.ResultSet
import java.sql.SQLException

@Repository
class TransitionRepository(private val jdbcTemplate: JdbcTemplate) : ITransitionRepository {
    override fun addProjectTransition(pid: Int, startState: String, endState: String): Int {
        try {
            return jdbcTemplate.queryForObject(Query.ADD_PROJECT_TRANSITION, arrayOf(pid, startState, endState))!!
        } catch (e: DataAccessException) {
            when ((e.cause as SQLException).sqlState) {
                DatabaseExceptionCodes.INVALID_TRANSITION ->
                    throw InvalidTransitionException("Start state and end state cannot be equal.")
                DatabaseExceptionCodes.INVALID_START_STATE ->
                    throw EntityNotFoundException("Start state not found.")
                DatabaseExceptionCodes.INVALID_END_STATE ->
                    throw EntityNotFoundException("End state not found.")
                else -> throw DatabaseAccessException("Error accessing database.")
            }
        }
    }

    override fun removeProjectTransition(pid: Int, tid: Int) {
        try {
            jdbcTemplate.queryForMap(Query.REMOVE_PROJECT_TRANSITION, pid, tid)
        } catch (e: DataAccessException) {
            when ((e.cause as SQLException).sqlState) {
                DatabaseExceptionCodes.INVALID_TRANSITION_REMOVE ->
                    throw InvalidOperationException("Cannot remove default transition.")
                else -> throw DatabaseAccessException("Error accessing database.")
            }
        }
    }

    override fun readProjectTransitions(pid: Int): List<Transition> {
        try {
            return jdbcTemplate.query(Query.SELECT_PROJECT_TRANSITIONS, arrayOf(pid), transitionRM)
        } catch (e: DataAccessException) {
            throw DatabaseAccessException("Error accessing database.")
        }
    }

    override fun readProjectTransition(pid: Int, tid: Int): Transition {
        try {
            return jdbcTemplate.queryForObject(Query.SELECT_PROJECT_TRANSITION, arrayOf(pid, tid), transitionRM)!!
        } catch (e: IncorrectResultSizeDataAccessException) {
            throw EntityNotFoundException("Transition not found.")
        } catch (e: DataAccessException) {
            throw DatabaseAccessException("Error accessing database.")
        }
    }

    private val transitionRM: (ResultSet, Int) -> Transition = { rs, _ ->
        Transition(
                rs.getInt("id"),
                rs.getInt("project"),
                rs.getString("startState"),
                rs.getString("endState")
        )
    }
}