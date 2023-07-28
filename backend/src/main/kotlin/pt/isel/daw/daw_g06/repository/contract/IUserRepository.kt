package pt.isel.daw.daw_g06.repository.contract

import pt.isel.daw.daw_g06.model.User

interface IUserRepository {
    fun readUser(username: String, password: String): Int

    fun readUsers(): List<User>

    fun readUser(uid: Int): User

    fun readUser(username: String): User
}
