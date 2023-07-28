package pt.isel.daw.daw_g06.repository

import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import pt.isel.daw.daw_g06.model.Project
import pt.isel.daw.daw_g06.repository.contract.IProjectRepository
import pt.isel.daw.daw_g06.repository.contract.IStateRepository

@RunWith(SpringRunner::class)
@SpringBootTest
class StateRepositoryTest {
    @Autowired
    private lateinit var stateRepository: IStateRepository
    @Autowired
    private lateinit var projectRepository: IProjectRepository

    val pName = "Test Project"
    val sName = "open"

    @Test
    fun addAndReadProjectStatesTest() {
        var pid = -1
        try {
            //Create project
            pid = projectRepository.createProject(Project(name = pName))

            // Operation to test
            stateRepository.addProjectState(pid, sName)

            val states = stateRepository.readProjectStates(pid)
            val addedState = states.find { it.name == sName }

            Assert.assertEquals(pid, addedState?.project)
            Assert.assertEquals(sName, addedState?.name)
        } finally {
            // Delete data used for test
            projectRepository.deleteProject(pid)
        }
    }

    @Test
    fun deleteProjectStateTest() {
        var pid = -1
        try {
            //Create project
            pid = projectRepository.createProject(Project(name = pName))

            // Operation to test
            stateRepository.addProjectState(pid, sName)
            stateRepository.removeProjectState(pid, sName)

            val states = stateRepository.readProjectStates(pid)

            // The set of available states of a project always have 2 default states
            assertEquals(2, states.size)
        } finally {
            // Delete data used for test
            projectRepository.deleteProject(pid)
        }
    }
}