package pt.isel.daw.daw_g06.repository

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import pt.isel.daw.daw_g06.model.Project
import pt.isel.daw.daw_g06.repository.contract.IProjectRepository
import pt.isel.daw.daw_g06.repository.contract.IStateRepository
import pt.isel.daw.daw_g06.repository.contract.ITransitionRepository
import pt.isel.daw.daw_g06.repository.exceptions.EntityNotFoundException
import pt.isel.daw.daw_g06.repository.exceptions.InvalidTransitionException

@RunWith(SpringRunner::class)
@SpringBootTest
class TransitionRepositoryTest {
    @Autowired
    private lateinit var transitionRepository: ITransitionRepository

    @Autowired
    private lateinit var projectRepository: IProjectRepository

    @Autowired
    private lateinit var stateRepository: IStateRepository

    val pName = "Test Project"
    val sName = "open"

    @Test
    fun addTransitionToProjectTest() {
        var pid = -1
        try {
            // Create project
            pid = projectRepository.createProject(Project(name = pName))

            stateRepository.addProjectState(pid, sName)
            val tid = transitionRepository.addProjectTransition(pid, sName, "closed")

            val transition = transitionRepository.readProjectTransition(pid, tid)

            assertEquals(tid, transition.id)
            assertEquals(pid, transition.project)
            assertEquals(sName, transition.startState)
            assertEquals("closed", transition.endState)
        } finally {
            // Delete testing data
            projectRepository.deleteProject(pid)
        }
    }

    @Test(expected = InvalidTransitionException::class)
    fun addTransitionToSameStateTest() {
        var pid = -1
        try {
            // Create project
            pid = projectRepository.createProject(Project(name = pName))

            stateRepository.addProjectState(pid, sName)
            transitionRepository.addProjectTransition(pid, sName, sName)
        } finally {
            // Delete testing data
            projectRepository.deleteProject(pid)
        }
    }

    @Test(expected = EntityNotFoundException::class)
    fun addTransitionToNonExistentStateTest() {
        var pid = -1
        try {
            // Create project
            pid = projectRepository.createProject(Project(name = pName))

            transitionRepository.addProjectTransition(pid, sName, "closed")
        } finally {
            // Delete testing data
            projectRepository.deleteProject(pid)
        }
    }

    @Test
    fun deleteTransitionTest() {
        var pid = -1
        try {
            // Create project
            pid = projectRepository.createProject(Project(name = pName))

            stateRepository.addProjectState(pid, sName)
            val tid = transitionRepository.addProjectTransition(pid, sName, "closed")
            transitionRepository.removeProjectTransition(pid, tid)

            val transitions = transitionRepository.readProjectTransitions(pid)

            assertEquals(1, transitions.size) // Projects have for default one transition
        } finally {
            // Delete testing data
            projectRepository.deleteProject(pid)
        }
    }
}