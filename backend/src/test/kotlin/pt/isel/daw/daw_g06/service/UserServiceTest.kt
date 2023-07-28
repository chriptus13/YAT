package pt.isel.daw.daw_g06.service

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import pt.isel.daw.daw_g06.model.User
import pt.isel.daw.daw_g06.repository.exceptions.EntityNotFoundException
import pt.isel.daw.daw_g06.service.contract.IUserService
import pt.isel.daw.daw_g06.service.exceptions.AuthenticationException
import pt.isel.daw.daw_g06.utils.UserUtilsTest

@RunWith(SpringRunner::class)
@SpringBootTest
class UserServiceTest {
    @Autowired
    private lateinit var userService: IUserService

    @Test
    fun authenticateTest() {
        userService.authenticate(
                User(username = UserUtilsTest.userC.username, password = UserUtilsTest.userC.password)
        )
    }

    @Test(expected = AuthenticationException::class)
    fun authenticateErrorTest() {
        userService.authenticate(
                User(username = UserUtilsTest.userC.username, password = "Wrong password")
        )
    }

    @Test
    fun getUserTest() {
        val user = userService.getUser(UserUtilsTest.userM.id)
        assertEquals(UserUtilsTest.userM.id, user.id)
        assertEquals(UserUtilsTest.userM.username, user.username)
        assertEquals(UserUtilsTest.userM.password, user.password)
    }

    @Test(expected = EntityNotFoundException::class)
    fun getNonExistentUser() {
        userService.getUser(Int.MIN_VALUE)
    }
}