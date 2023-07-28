package pt.isel.daw.daw_g06.repository.implementation

import org.springframework.dao.DataAccessException
import org.springframework.dao.IncorrectResultSizeDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.queryForObject
import org.springframework.stereotype.Repository
import pt.isel.daw.daw_g06.model.Comment
import pt.isel.daw.daw_g06.repository.contract.ICommentRepository
import pt.isel.daw.daw_g06.repository.exceptions.ArchivedCommentException
import pt.isel.daw.daw_g06.repository.exceptions.DatabaseAccessException
import pt.isel.daw.daw_g06.repository.exceptions.DatabaseExceptionCodes
import pt.isel.daw.daw_g06.repository.exceptions.EntityNotFoundException
import java.sql.ResultSet
import java.sql.SQLException

@Repository
class CommentRepository(private val jdbcTemplate: JdbcTemplate) : ICommentRepository {

    override fun createComment(uid: Int, pid: Int, comment: Comment): Int {
        try {
            return jdbcTemplate.queryForObject(Query.INSERT_COMMENT,
                    arrayOf(comment.issue, uid, comment.text))!!
        } catch (e: DataAccessException) {
            when ((e.cause as SQLException).sqlState) {
                DatabaseExceptionCodes.COMMENT_ARCHIVED_ISSUE ->
                    throw ArchivedCommentException("Issue with id ${comment.issue} already archived.")
                else -> throw DatabaseAccessException("Error accessing database.")
            }
        }
    }

    override fun readComments(pid: Int, iid: Int): List<Comment> {
        try {
            return jdbcTemplate.query(Query.SELECT_COMMENTS, arrayOf(iid), commentRM)
        } catch (e: DataAccessException) {
            throw DatabaseAccessException("Error accessing database.")
        }
    }

    override fun readComment(pid: Int, iid: Int, cid: Int): Comment {
        try {
            return jdbcTemplate.queryForObject(Query.SELECT_COMMENT, arrayOf(cid), commentRM)!!
        } catch (e: IncorrectResultSizeDataAccessException) {
            throw EntityNotFoundException("Comment not found.")
        } catch (e: DataAccessException) {
            throw DatabaseAccessException("Error accessing database.")
        }
    }

    override fun updateComment(pid: Int, comment: Comment) {
        try {
            jdbcTemplate.update(Query.UPDATE_COMMENT, comment.text, comment.id)
        } catch (e: DataAccessException) {
            throw DatabaseAccessException("Error accessing database.")
        }
    }

    override fun deleteComment(pid: Int, iid: Int, cid: Int) {
        try {
            jdbcTemplate.update(Query.DELETE_COMMENT, cid)
        } catch (e: DataAccessException) {
            throw DatabaseAccessException("Error accessing database.")
        }
    }

    private val commentRM: (ResultSet, Int) -> Comment = { rs, _ ->
        Comment(
                rs.getInt("id"),
                rs.getInt("issue"),
                rs.getString("creator"),
                rs.getString("text"),
                rs.getTimestamp("created")
        )
    }
}