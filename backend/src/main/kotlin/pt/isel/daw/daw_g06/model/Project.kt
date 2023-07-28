package pt.isel.daw.daw_g06.model

data class Project(
        val id: Int = -1,
        val name: String,
        val description: String? = null,
        val initialState: String? = null
) {
    fun replacing(
            name: String = this.name,
            description: String? = this.description,
            initialState: String? = this.initialState
    ) = Project(id, name, description, initialState)
}