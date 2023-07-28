package pt.isel.daw.daw_g06.repository

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import pt.isel.daw.daw_g06.repository.contract.IUserRepository
import pt.isel.daw.daw_g06.utils.UserUtilsTest

@RunWith(SpringRunner::class)
@SpringBootTest
class UserRepositoryTest {
    @Autowired
    private lateinit var userRepository: IUserRepository

    @Test
    fun getUserByIdTest() {
        val user = userRepository.readUser(UserUtilsTest.userA.id)

        assertEquals(UserUtilsTest.userA, user)
    }

    @Test
    fun getUserByUsernameTest() {
        val user = userRepository.readUser(UserUtilsTest.userC.username)

        assertEquals(UserUtilsTest.userC, user)
    }

    @Test
    fun getUserIdTest() {
        val userID = userRepository.readUser(UserUtilsTest.userM.username, UserUtilsTest.userM.password)

        assertEquals(UserUtilsTest.userM.id, userID)
    }
}