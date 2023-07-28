package pt.isel.daw.daw_g06.service.implementation

import org.springframework.stereotype.Service
import pt.isel.daw.daw_g06.model.Comment
import pt.isel.daw.daw_g06.repository.contract.ICommentRepository
import pt.isel.daw.daw_g06.repository.contract.IIssueRepository
import pt.isel.daw.daw_g06.repository.contract.IProjectRepository
import pt.isel.daw.daw_g06.repository.contract.IUserRepository
import pt.isel.daw.daw_g06.repository.exceptions.EntityNotFoundException
import pt.isel.daw.daw_g06.service.contract.ICommentService
import pt.isel.daw.daw_g06.service.exceptions.AuthorizationException

@Service
class CommentService(
        private val userRepository: IUserRepository,
        private val projectRepository: IProjectRepository,
        private val issueRepository: IIssueRepository,
        private val commentRepository: ICommentRepository
) : ICommentService {

    override fun create(uid: Int, pid: Int, comment: Comment) = commentRepository.createComment(uid, pid, comment)

    override fun readComments(pid: Int, iid: Int): List<Comment> {
        issueRepository.readIssue(pid, iid) // Validate issue existence
        return commentRepository.readComments(pid, iid)
    }

    override fun readComment(pid: Int, iid: Int, cid: Int) =
            commentRepository.readComment(pid, iid, cid)

    override fun updateComment(uid: Int, pid: Int, iid: Int, cid: Int, text: String) {
        val old = commentRepository.readComment(pid, iid, cid)
        val user = userRepository.readUser(uid)
        if (user.username == old.creator) commentRepository.updateComment(pid, old.replacing(text))
        else throw AuthorizationException("User cannot update a comment that he didn't create.")
    }

    override fun deleteComment(uid: Int, pid: Int, iid: Int, cid: Int) {
        val c = commentRepository.readComment(pid, iid, cid)
        val user = userRepository.readUser(uid)
        if (user.username == c.creator) commentRepository.deleteComment(pid, iid, cid)
        else
            try {
                projectRepository.validateMemberInProject(uid, pid)
                commentRepository.deleteComment(pid, iid, cid)
            } catch (e: EntityNotFoundException) {
                throw AuthorizationException("User does not have permission to delete comment.")
            }
    }
}