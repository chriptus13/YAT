package pt.isel.daw.daw_g06.repository.implementation

import org.springframework.dao.DataAccessException
import org.springframework.dao.IncorrectResultSizeDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.queryForObject
import org.springframework.stereotype.Repository
import pt.isel.daw.daw_g06.model.User
import pt.isel.daw.daw_g06.repository.contract.IUserRepository
import pt.isel.daw.daw_g06.repository.exceptions.DatabaseAccessException
import pt.isel.daw.daw_g06.repository.exceptions.EntityNotFoundException
import java.sql.ResultSet

@Repository
class UserRepository(private val jdbcTemplate: JdbcTemplate) : IUserRepository {
    override fun readUser(username: String, password: String): Int {
        try {
            return jdbcTemplate.queryForObject(Query.SELECT_USER_AUTH, arrayOf(username, password))!!
        } catch (e: IncorrectResultSizeDataAccessException) {
            throw EntityNotFoundException("User not found.")
        } catch (e: DataAccessException) {
            throw DatabaseAccessException("Error accessing database.")
        }
    }

    override fun readUsers(): List<User> {
        try {
            return jdbcTemplate.query(Query.SELECT_USERS, userRM)
        } catch (e: DataAccessException) {
            throw DatabaseAccessException("Error accessing database.")
        }
    }

    override fun readUser(uid: Int): User {
        try {
            return jdbcTemplate.queryForObject(Query.SELECT_USER, arrayOf(uid), userRM)!!
        } catch (e: IncorrectResultSizeDataAccessException) {
            throw EntityNotFoundException("User not found.")
        } catch (e: DataAccessException) {
            throw DatabaseAccessException("Error accessing database.")
        }
    }

    override fun readUser(username: String): User {
        try {
            return jdbcTemplate.queryForObject(Query.SELECT_USER_BY_USERNAME, arrayOf(username), userRM)!!
        } catch (e: IncorrectResultSizeDataAccessException) {
            throw EntityNotFoundException("User not found.")
        } catch (e: DataAccessException) {
            throw DatabaseAccessException("Error accessing database.")
        }
    }

    private val userRM: (ResultSet, Int) -> User = { rs, _ ->
        User(rs.getInt("id"), rs.getString("username"), rs.getString("password"))
    }
}