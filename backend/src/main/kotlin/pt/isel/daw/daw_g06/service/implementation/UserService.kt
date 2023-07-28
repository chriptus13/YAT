package pt.isel.daw.daw_g06.service.implementation

import org.springframework.stereotype.Service
import pt.isel.daw.daw_g06.model.User
import pt.isel.daw.daw_g06.repository.contract.IUserRepository
import pt.isel.daw.daw_g06.repository.exceptions.EntityNotFoundException
import pt.isel.daw.daw_g06.service.contract.IUserService
import pt.isel.daw.daw_g06.service.exceptions.AuthenticationException

@Service
class UserService(
        private val userRepository: IUserRepository
) : IUserService {

    override fun authenticate(user: User): Int {
        try {
            return userRepository.readUser(user.username, user.password)
        } catch (e: EntityNotFoundException) {
            throw AuthenticationException("Incorrect username or password.")
        }
    }

    override fun getUser(username: String) = userRepository.readUser(username)

    override fun getUser(uid: Int) = userRepository.readUser(uid)
}