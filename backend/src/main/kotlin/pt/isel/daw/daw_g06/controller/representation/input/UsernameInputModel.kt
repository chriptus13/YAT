package pt.isel.daw.daw_g06.controller.representation.input

import pt.isel.daw.daw_g06.model.Member

data class UsernameInputModel(
        val username: String
) {
    fun toMember(pid: Int) = Member(pid, username)
}