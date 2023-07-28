package pt.isel.daw.daw_g06.model

import java.util.*

data class Issue(
        val id: Int = -1,
        val project: Int = -1,
        val creator: String = "",
        val name: String = "",
        val description: String = "",
        val created: Date = Date(),
        val closed: Date? = null,
        val state: String = ""
) {
    fun replacing(
            name: String = this.name,
            description: String = this.description,
            state: String = this.state
    ) = Issue(id, project, creator, name, description, created, closed, state)
}