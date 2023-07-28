package pt.isel.daw.daw_g06.service.contract

import pt.isel.daw.daw_g06.model.Comment

interface ICommentService {
    fun create(uid: Int, pid: Int, comment: Comment): Int
    fun readComments(pid: Int, iid: Int): List<Comment>
    fun readComment(pid: Int, iid: Int, cid: Int): Comment
    fun updateComment(uid: Int, pid: Int, iid: Int, cid: Int, text: String)
    fun deleteComment(uid: Int, pid: Int, iid: Int, cid: Int)
}