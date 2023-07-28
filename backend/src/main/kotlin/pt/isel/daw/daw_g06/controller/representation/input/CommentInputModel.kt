package pt.isel.daw.daw_g06.controller.representation.input

import pt.isel.daw.daw_g06.model.Comment

data class CommentInputModel(
        val text: String
) {
    fun mapToComment(iid: Int) = Comment(issue = iid, text = text)
    fun mapToComment(iid: Int, cid: Int) = Comment(id = cid, issue = iid, text = text)
}