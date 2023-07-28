package pt.isel.daw.daw_g06.repository.contract

import pt.isel.daw.daw_g06.model.Comment

interface ICommentRepository {
    fun createComment(uid: Int, pid: Int, comment: Comment): Int

    fun readComments(pid: Int, iid: Int): List<Comment>

    fun readComment(pid: Int, iid: Int, cid: Int): Comment

    fun updateComment(pid: Int, comment: Comment)

    fun deleteComment(pid: Int, iid: Int, cid: Int)
}