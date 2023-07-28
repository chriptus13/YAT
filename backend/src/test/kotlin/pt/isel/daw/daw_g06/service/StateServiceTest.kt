package pt.isel.daw.daw_g06.service

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import pt.isel.daw.daw_g06.model.Project
import pt.isel.daw.daw_g06.model.State
import pt.isel.daw.daw_g06.repository.exceptions.EntityNotFoundException
import pt.isel.daw.daw_g06.service.contract.IProjectService
import pt.isel.daw.daw_g06.service.contract.IStateService
import pt.isel.daw.daw_g06.utils.UserUtilsTest

@RunWith(SpringRunner::class)
@SpringBootTest
class StateServiceTest {
    @Autowired
    private lateinit var projectService: IProjectService
    @Autowired
    private lateinit var stateService: IStateService

    private val pName = "Test Project"
    private val openState = "opened"

    @Test
    fun addStateToProjectTest() {
        var pid = -1
        try {
            // Create project
            pid = projectService.create(UserUtilsTest.userA.id, Project(name = pName))

            // Add state to project
            stateService.addState(UserUtilsTest.userA.id, State(pid, openState))

            // Read set of available states of created project
            val states = stateService.readStates(pid)

            // Assert
            // Project should have a list with three states (two default and the one added)
            assertEquals(3, states.size)

        } finally {
            // Delete data used for test
            projectService.deleteProject(UserUtilsTest.userA.id, pid)
        }
    }

    @Test
    fun addTwiceTheSameStateToProjectTest() {
        var pid = -1
        try {
            // Create project
            pid = projectService.create(UserUtilsTest.userA.id, Project(name = pName))

            // Add same state twice
            val state = State(pid, openState)
            stateService.addState(UserUtilsTest.userA.id, state)
            stateService.addState(UserUtilsTest.userA.id, state)

            // Read set of available states of created project
            val states = stateService.readStates(pid)

            // Assert
            // Project should have a list with three states (two default and the one added)
            assertEquals(3, states.size)

        } finally {
            // Delete data used for test
            projectService.deleteProject(UserUtilsTest.userA.id, pid)
        }
    }

    @Test(expected = EntityNotFoundException::class)
    fun addStateToNonExistentProject() {
        stateService.addState(UserUtilsTest.userA.id, State(Int.MIN_VALUE, openState))
    }

    @Test
    fun readStatesInProjectTest() {
        var pid = -1
        try {
            // Create project
            pid = projectService.create(UserUtilsTest.userA.id, Project(name = pName))

            // Read set of available states of created project
            val states = stateService.readStates(pid)

            // Assert
            // Project should have a list with two default states
            assertEquals(2, states.size)

        } finally {
            // Delete data used for test
            projectService.deleteProject(UserUtilsTest.userA.id, pid)
        }
    }

    @Test(expected = EntityNotFoundException::class)
    fun readStatesOfNonExistentProject() {
        stateService.readStates(Int.MIN_VALUE)
    }

    @Test
    fun removeStateOfProject() {

    }
}