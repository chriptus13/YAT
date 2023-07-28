package pt.isel.daw.daw_g06.repository.implementation

import org.springframework.dao.DataAccessException
import org.springframework.dao.DuplicateKeyException
import org.springframework.dao.IncorrectResultSizeDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.queryForObject
import org.springframework.stereotype.Repository
import pt.isel.daw.daw_g06.model.Member
import pt.isel.daw.daw_g06.model.Project
import pt.isel.daw.daw_g06.repository.contract.IProjectRepository
import pt.isel.daw.daw_g06.repository.exceptions.*
import java.sql.ResultSet
import java.sql.SQLException

@Repository
class ProjectRepository(private val jdbcTemplate: JdbcTemplate) : IProjectRepository {
    override fun createProject(project: Project): Int {
        try {
            return if (project.initialState == null)
                jdbcTemplate.queryForObject(Query.INSERT_PROJECT,
                        arrayOf(project.name, project.description ?: "null"))!!
            else jdbcTemplate.queryForObject(Query.INSERT_PROJECT_WITH_INITIALSTATE,
                    arrayOf(project.name, project.description ?: "", project.initialState))!!
        } catch (e: DuplicateKeyException) {
            throw UniqueAttributeViolationException("Project name needs to be unique.")
        } catch (e: DataAccessException) {
            throw DatabaseAccessException("Error accessing database.")
        }
    }

    override fun readProjects(): List<Project> {
        try {
            return jdbcTemplate.query(Query.SELECT_PROJECTS, projectRM)
        } catch (e: DataAccessException) {
            throw DatabaseAccessException("Error accessing database.")
        }
    }

    override fun readProject(pid: Int): Project {
        try {
            return jdbcTemplate.queryForObject(Query.SELECT_PROJECT, arrayOf(pid), projectRM)!!
        } catch (e: IncorrectResultSizeDataAccessException) {
            throw EntityNotFoundException("Project not found.")
        } catch (e: DataAccessException) {
            throw DatabaseAccessException("Error accessing database.")
        }
    }

    override fun updateProject(project: Project) {
        try {
            jdbcTemplate.update(Query.UPDATE_PROJECT,
                    project.name, project.description, project.initialState, project.id)
        } catch (e: DuplicateKeyException) {
            throw UniqueAttributeViolationException("Project name needs to be unique.")
        } catch (e: DataAccessException) {
            when ((e.cause as SQLException).sqlState) {
                DatabaseExceptionCodes.INVALID_STATE ->
                    throw InvalidOperationException("Invalid state. State [${project.initialState}] does not exist.")
                else -> throw DatabaseAccessException("Error accessing database.")
            }
        }
    }

    override fun deleteProject(pid: Int) {
        try {
            jdbcTemplate.update(Query.DELETE_PROJECT, pid)
        } catch (e: DataAccessException) {
            throw DatabaseAccessException("Error accessing database.")
        }
    }

    override fun addProjectMember(pid: Int, uid: Int) {
        try {
            jdbcTemplate.update(Query.INSERT_PROJECT_USER, pid, uid)
        } catch (e: DuplicateKeyException) {
            throw UniqueAttributeViolationException("User is already a member.")
        } catch (e: DataAccessException) {
            throw DatabaseAccessException("Error accessing database.")
        }
    }

    override fun removeProjectMember(pid: Int, uid: Int) {
        try {
            jdbcTemplate.update(Query.DELETE_PROJECT_USER, pid, uid)
        } catch (e: DataAccessException) {
            throw DatabaseAccessException("Error accessing database.")
        }
    }

    override fun readProjectMembers(pid: Int): List<Member> {
        try {
            return jdbcTemplate.query(Query.SELECT_PROJECT_MEMBERS, arrayOf(pid), memberRM)
        } catch (e: DataAccessException) {
            throw DatabaseAccessException("Error accessing database.")
        }
    }

    override fun validateMemberInProject(uid: Int, pid: Int) {
        try {
            jdbcTemplate.queryForObject<Int>(Query.SELECT_PROJECT_USER, arrayOf(pid, uid))
        } catch (e: IncorrectResultSizeDataAccessException) {
            throw EntityNotFoundException("Member not found.")
        } catch (e: DataAccessException) {
            throw DatabaseAccessException("Error accessing database.")
        }
    }

    private val projectRM: (ResultSet, Int) -> Project = { rs, _ ->
        Project(rs.getInt("id"), rs.getString("name"),
                rs.getString("description"), rs.getString("initialState")
        )
    }

    private val memberRM: (ResultSet, Int) -> Member = { rs, _ ->
        Member(rs.getInt("project"), rs.getString("username"))
    }
}