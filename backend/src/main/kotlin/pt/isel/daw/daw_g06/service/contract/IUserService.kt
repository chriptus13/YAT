package pt.isel.daw.daw_g06.service.contract

import pt.isel.daw.daw_g06.model.User

interface IUserService {
    fun authenticate(user: User): Int

    fun getUser(username: String): User

    fun getUser(uid: Int): User
}