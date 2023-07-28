package pt.isel.daw.daw_g06.model

import java.util.*

data class Comment(
        val id: Int = -1,
        val issue: Int,
        val creator: String = "",
        val text: String,
        val created: Date? = null
) {
    fun replacing(text: String) = Comment(id, issue, creator, text, created)
}