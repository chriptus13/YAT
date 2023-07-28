package pt.isel.daw.daw_g06.repository

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import pt.isel.daw.daw_g06.model.Project
import pt.isel.daw.daw_g06.repository.implementation.ProjectRepository

@RunWith(SpringRunner::class)
@SpringBootTest
class ProjectRepositoryTest {
    @Autowired
    lateinit var projectRepository: ProjectRepository

    private val name = "Test Project"
    private val description = "Test Description"
    private val newName = "New Test Project"
    private val newDescription = "New Test Description"
    private val newState = "Alive"

    @Test
    fun readProjectTest() {
        var pid = -1
        try {
            // Create project
            pid = projectRepository.createProject(
                    Project(name = name, description = description)
            )

            // Operation to test
            val project = projectRepository.readProject(pid)

            assertEquals(project.name, name)
            assertEquals(project.description, description)
            assertEquals(project.id, pid)
        } finally {
            // Delete testing data
            projectRepository.deleteProject(pid)
        }
    }

    @Test
    fun readProjectsTest() {
        var pid = -1
        try {
            // Create project
            pid = projectRepository.createProject(
                    Project(name = name, description = description)
            )

            // Operation to test
            val projects = projectRepository.readProjects()
            val project: Project? = projects.find { it.id == pid }

            assertEquals(project?.name, name)
            assertEquals(project?.description, description)
        } finally {
            // Delete testing data
            projectRepository.deleteProject(pid)
        }
    }

    @Test
    fun updateProject() {
        var pid = -1
        try {
            // Create project
            pid = projectRepository.createProject(
                    Project(name = name, description = description)
            )

            // Operation to test
            projectRepository.updateProject(Project(pid, newName, newDescription, newState))
            val project = projectRepository.readProject(pid)

            assertEquals(project.id, pid)
            assertEquals(project.name, newName)
            assertEquals(project.description, newDescription)
        } finally {
            // Delete testing data
            projectRepository.deleteProject(pid)
        }
    }
}