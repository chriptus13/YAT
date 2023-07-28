package pt.isel.daw.daw_g06.service

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import pt.isel.daw.daw_g06.model.Member
import pt.isel.daw.daw_g06.model.Project
import pt.isel.daw.daw_g06.repository.exceptions.EntityNotFoundException
import pt.isel.daw.daw_g06.repository.exceptions.UniqueAttributeViolationException
import pt.isel.daw.daw_g06.service.contract.IProjectService
import pt.isel.daw.daw_g06.service.exceptions.AuthorizationException
import pt.isel.daw.daw_g06.utils.UserUtilsTest

@RunWith(SpringRunner::class)
@SpringBootTest
class ProjectServiceTest {
    @Autowired
    private lateinit var projectService: IProjectService

    private val name = "Test Project"
    private val description = "Test Description"
    private val newName = "New Test Project"
    private val newDescription = "New Test Description"
    private val newState = "closed"

    @Test
    fun createProjectTest() {
        var pid = -1
        try {
            // Create project
            pid = projectService.create(UserUtilsTest.userA.id, Project(name = name, description = description))

            // Read created project
            val project = projectService.readProject(pid)

            // Assert
            assertEquals(pid, project.id)
            assertEquals(name, project.name)
            assertEquals(description, project.description)

        } finally {
            // Delete data used for test
            projectService.deleteProject(UserUtilsTest.userA.id, pid)
        }
    }

    @Test(expected = UniqueAttributeViolationException::class)
    fun createProjectWithAlreadyExistentName() {
        var pid = -1
        try {
            // Create project
            pid = projectService.create(UserUtilsTest.userA.id, Project(name = name, description = description))

            // Create other project with same name
            projectService.create(UserUtilsTest.userA.id, Project(name = name))

        } finally {
            // Delete data used for test
            projectService.deleteProject(UserUtilsTest.userA.id, pid)
        }
    }

    @Test
    fun readProjectsTest() {
        var pid = -1
        try {
            // Create project
            pid = projectService.create(UserUtilsTest.userA.id, Project(name = name, description = description))

            // Read all projects
            val projects = projectService.readProjects()

            // Find in all projects the recently created project
            val project = projects.find { it.id == pid }

            // Assert
            assertNotNull(project)
            assertEquals(pid, project?.id)
            assertEquals(name, project?.name)
            assertEquals(description, project?.description)

        } finally {
            // Delete data used for test
            projectService.deleteProject(UserUtilsTest.userA.id, pid)
        }
    }

    @Test
    fun readProjectTest() {
        var pid = -1
        try {
            // Create project
            pid = projectService.create(UserUtilsTest.userA.id, Project(name = name, description = description))

            // Read created project
            val project = projectService.readProject(pid)

            // Assert
            assertEquals(pid, project.id)
            assertEquals(name, project.name)
            assertEquals(description, project.description)

        } finally {
            // Delete data used for test
            projectService.deleteProject(UserUtilsTest.userA.id, pid)
        }
    }

    @Test(expected = EntityNotFoundException::class)
    fun readNonExistentProjectTest() {
        projectService.readProject(Integer.MIN_VALUE)
    }

    @Test
    fun updateProjectNameTest() {
        var pid = -1
        try {
            // Create project
            pid = projectService.create(UserUtilsTest.userA.id, Project(name = name, description = description))

            // Update project
            projectService.updateProjectName(UserUtilsTest.userA.id, pid, newName)

            // Read updated project
            val project = projectService.readProject(pid)

            // Assert
            assertEquals(pid, project.id)
            assertEquals(newName, project.name)
            assertEquals(description, project.description)

        } finally {
            // Delete data used for test
            projectService.deleteProject(UserUtilsTest.userA.id, pid)
        }
    }

    @Test(expected = UniqueAttributeViolationException::class)
    fun updateProjectNameToExistentNameTest() {
        var pidA = -1
        var pidB = -1
        try {
            // Create project A
            pidA = projectService.create(UserUtilsTest.userA.id, Project(name = name, description = description))

            // Create project B
            pidB = projectService.create(UserUtilsTest.userA.id, Project(name = newName))

            // Update project A with the same name of project B
            projectService.updateProjectName(UserUtilsTest.userA.id, pidA, newName)

        } finally {
            // Delete data used for test
            projectService.deleteProject(UserUtilsTest.userA.id, pidA)
            projectService.deleteProject(UserUtilsTest.userA.id, pidB)
        }
    }

    @Test(expected = AuthorizationException::class)
    fun updateProjectNameWithUserThatDoNotBelongToProjectTest() {
        var pid = -1
        try {
            // Create project with user A
            pid = projectService.create(UserUtilsTest.userA.id, Project(name = name, description = description))

            // Update project with user C
            projectService.updateProjectName(UserUtilsTest.userC.id, pid, newName)

        } finally {
            // Delete data used for test
            projectService.deleteProject(UserUtilsTest.userA.id, pid)
        }
    }

    @Test
    fun updateProjectNameWithADifferentUserThatCreatedItTest() {
        var pid = -1
        try {
            // Create project with user A
            pid = projectService.create(UserUtilsTest.userA.id, Project(name = name, description = description))

            // Add user B to project created
            projectService.addProjectMember(UserUtilsTest.userA.id, Member(pid, UserUtilsTest.userC.username))

            // Update project with user C
            projectService.updateProjectName(UserUtilsTest.userC.id, pid, newName)

            // Read updated project
            val project = projectService.readProject(pid)

            // Assert
            assertEquals(pid, project.id)
            assertEquals(newName, project.name)
            assertEquals(description, project.description)

        } finally {
            // Delete data used for test
            projectService.deleteProject(UserUtilsTest.userA.id, pid)
        }
    }

    @Test
    fun updateProjectDescriptionTest() {
        var pid = -1
        try {
            // Create project
            pid = projectService.create(UserUtilsTest.userA.id, Project(name = name, description = description))

            // Update project
            projectService.updateProjectDescription(UserUtilsTest.userA.id, pid, newDescription)

            // Read updated project
            val project = projectService.readProject(pid)

            // Assert
            assertEquals(pid, project.id)
            assertEquals(name, project.name)
            assertEquals(newDescription, project.description)

        } finally {
            // Delete data used for test
            projectService.deleteProject(UserUtilsTest.userA.id, pid)
        }
    }

    @Test(expected = AuthorizationException::class)
    fun updateProjectDescriptionWithUserThatDoNotBelongToProjectTest() {
        var pid = -1
        try {
            // Create project with user A
            pid = projectService.create(UserUtilsTest.userA.id, Project(name = name, description = description))

            // Update project with user C
            projectService.updateProjectDescription(UserUtilsTest.userC.id, pid, newDescription)

        } finally {
            // Delete data used for test
            projectService.deleteProject(UserUtilsTest.userA.id, pid)
        }
    }

    @Test
    fun updateProjectDescriptionWithADifferentUserThatCreatedItTest() {
        var pid = -1
        try {
            // Create project with user A
            pid = projectService.create(UserUtilsTest.userA.id, Project(name = name, description = description))

            // Add user B to project created
            projectService.addProjectMember(UserUtilsTest.userA.id, Member(pid, UserUtilsTest.userC.username))

            // Update project with user C
            projectService.updateProjectDescription(UserUtilsTest.userC.id, pid, newDescription)

            // Read updated project
            val project = projectService.readProject(pid)

            // Assert
            assertEquals(pid, project.id)
            assertEquals(name, project.name)
            assertEquals(newDescription, project.description)

        } finally {
            // Delete data used for test
            projectService.deleteProject(UserUtilsTest.userA.id, pid)
        }
    }

    @Test
    fun updateProjectInitialStateTest() {
        var pid = -1
        try {
            // Create project
            pid = projectService.create(UserUtilsTest.userA.id, Project(name = name))

            // Update project
            projectService.updateProjectInitialState(UserUtilsTest.userA.id, pid, newState)

            // Read updated project
            val project = projectService.readProject(pid)

            // Assert
            assertEquals(pid, project.id)
            assertEquals(name, project.name)
            assertEquals(newState, project.initialState)

        } finally {
            // Delete data used for test
            projectService.deleteProject(UserUtilsTest.userA.id, pid)
        }
    }

    /*
    @Test(expected = EntityNotFoundException::class)
    fun updateProjectInitialStateWithNonExistentStateTest() {
        var pid = -1
        try {
            // Create project
            pid = projectService.create(UserUtilsTest.userA.id, Project(name = name))

            // Update project
            projectService.updateProjectInitialState(UserUtilsTest.userA.id, pid, "Testing test of tester")

        } finally {
            // Delete data used for test
            projectService.deleteProject(UserUtilsTest.userA.id, pid)
        }
    }*/

    @Test(expected = AuthorizationException::class)
    fun updateProjectInitialStateWithUserThatDoNotBelongToProjectTest() {
        var pid = -1
        try {
            // Create project with user A
            pid = projectService.create(UserUtilsTest.userA.id, Project(name = name, description = description))

            // Update project with user C
            projectService.updateProjectInitialState(UserUtilsTest.userC.id, pid, newState)

        } finally {
            // Delete data used for test
            projectService.deleteProject(UserUtilsTest.userA.id, pid)
        }
    }

    @Test
    fun updateProjectInitialStateWithADifferentUserThatCreatedItTest() {
        var pid = -1
        try {
            // Create project with user A
            pid = projectService.create(UserUtilsTest.userA.id, Project(name = name, description = description))

            // Add user B to project created
            projectService.addProjectMember(UserUtilsTest.userA.id, Member(pid, UserUtilsTest.userC.username))

            // Update project with user C
            projectService.updateProjectInitialState(UserUtilsTest.userC.id, pid, newState)

            // Read updated project
            val project = projectService.readProject(pid)

            // Assert
            assertEquals(pid, project.id)
            assertEquals(name, project.name)
            assertEquals(newState, project.initialState)

        } finally {
            // Delete data used for test
            projectService.deleteProject(UserUtilsTest.userA.id, pid)
        }
    }

    @Test
    fun updateProjectTest() {
        var pid = -1
        try {
            // Create project
            pid = projectService.create(UserUtilsTest.userA.id, Project(name = name, description = description))

            // Update project
            projectService.updateProject(UserUtilsTest.userA.id, Project(pid, newName, newDescription, newState))

            // Read updated project
            val project = projectService.readProject(pid)

            // Assert
            assertEquals(pid, project.id)
            assertEquals(newName, project.name)
            assertEquals(newDescription, project.description)
            assertEquals(newState, project.initialState)

        } finally {
            // Delete data used for test
            projectService.deleteProject(UserUtilsTest.userA.id, pid)
        }
    }

    @Test(expected = UniqueAttributeViolationException::class)
    fun updateProjectWithExistentNameTest() {
        var pidA = -1
        var pidB = -1
        try {
            // Create project A
            pidA = projectService.create(UserUtilsTest.userA.id, Project(name = name, description = description))

            // Create project B
            pidB = projectService.create(UserUtilsTest.userA.id, Project(name = newName))

            // Update project A with the same name of project B
            projectService.updateProject(UserUtilsTest.userA.id, Project(pidA, newName, newDescription, newState))

        } finally {
            // Delete data used for test
            projectService.deleteProject(UserUtilsTest.userA.id, pidA)
            projectService.deleteProject(UserUtilsTest.userA.id, pidB)
        }
    }

    @Test(expected = AuthorizationException::class)
    fun updateProjectWithUserThatDoNotBelongToProjectTest() {
        var pid = -1
        try {
            // Create project with user A
            pid = projectService.create(UserUtilsTest.userA.id, Project(name = name, description = description))

            // Update project with user C
            projectService.updateProject(UserUtilsTest.userC.id, Project(pid, newName, newDescription, newState))

        } finally {
            // Delete data used for test
            projectService.deleteProject(UserUtilsTest.userA.id, pid)
        }
    }

    @Test
    fun updateProjectWithADifferentUserThatCreatedItTest() {
        var pid = -1
        try {
            // Create project with user A
            pid = projectService.create(UserUtilsTest.userA.id, Project(name = name, description = description))

            // Add user B to project created
            projectService.addProjectMember(UserUtilsTest.userA.id, Member(pid, UserUtilsTest.userC.username))

            // Update project with user C
            projectService.updateProject(UserUtilsTest.userC.id, Project(pid, newName, newDescription, newState))

            // Read updated project
            val project = projectService.readProject(pid)

            // Assert
            assertEquals(pid, project.id)
            assertEquals(newName, project.name)
            assertEquals(newDescription, project.description)
            assertEquals(newState, project.initialState)

        } finally {
            // Delete data used for test
            projectService.deleteProject(UserUtilsTest.userA.id, pid)
        }
    }

    @Test
    fun deleteProjectTest() {
        var pid = -1
        try {
            // Create project
            pid = projectService.create(UserUtilsTest.userA.id, Project(name = name))

            // Delete created project
            projectService.deleteProject(UserUtilsTest.userA.id, pid)

            // Read all project
            val projects = projectService.readProjects()

            // Try to find deleted project
            val project = projects.find { it.id == pid }

            // Assert
            assertNull(project)

        } finally {
            try {
                // Delete data used for test
                projectService.deleteProject(UserUtilsTest.userA.id, pid)
            } catch (ignored: Exception) {
            }
        }
    }

    @Test(expected = AuthorizationException::class)
    fun deleteProjectWithUserThatDoNotBelongToProjectTest() {
        var pid = -1
        try {
            // Create project with user A
            pid = projectService.create(UserUtilsTest.userA.id, Project(name = name))

            // Delete created project with user C
            projectService.deleteProject(UserUtilsTest.userC.id, pid)

        } finally {
            // Delete data used for test
            projectService.deleteProject(UserUtilsTest.userA.id, pid)
        }
    }

    @Test
    fun deleteProjectWithADifferentUserThatCreatedItTest() {
        var pid = -1
        try {
            // Create project with user A
            pid = projectService.create(UserUtilsTest.userA.id, Project(name = name))

            // Add user C to project created
            projectService.addProjectMember(UserUtilsTest.userA.id, Member(pid, UserUtilsTest.userC.username))

            // Delete created project with user C
            projectService.deleteProject(UserUtilsTest.userC.id, pid)

            // Read all project
            val projects = projectService.readProjects()

            // Try to find deleted project
            val project = projects.find { it.id == pid }

            // Assert
            assertNull(project)

        } finally {
            try {
                // Delete data used for test
                projectService.deleteProject(UserUtilsTest.userA.id, pid)
            } catch (ignored: Exception) {
            }
        }
    }

    @Test
    fun readProjectMembersTest() {
        var pid = -1
        try {
            // Create project
            pid = projectService.create(UserUtilsTest.userA.id, Project(name = name))

            // Read project members
            val members = projectService.readProjectMembers(pid)

            // Assert
            assertEquals(1, members.size) // Project should have only the creator as a member
            assertEquals(pid, members[0].project)
            assertEquals(UserUtilsTest.userA.username, members[0].username)

        } finally {
            // Delete data used for test
            projectService.deleteProject(UserUtilsTest.userA.id, pid)
        }
    }

    @Test(expected = EntityNotFoundException::class)
    fun readProjectMembersThatDoNotExistTest() {
        projectService.readProjectMembers(Int.MIN_VALUE)
    }

    @Test
    fun addProjectMemberTest() {
        var pid = -1
        try {
            // Create project
            pid = projectService.create(UserUtilsTest.userA.id, Project(name = name))

            // Add user C to project created
            projectService.addProjectMember(UserUtilsTest.userA.id, Member(pid, UserUtilsTest.userC.username))

            // Read project members
            val members = projectService.readProjectMembers(pid)

            // Assert
            assertEquals(2, members.size) // Project should have two members(creator and recently added member)

        } finally {
            // Delete data used for test
            projectService.deleteProject(UserUtilsTest.userA.id, pid)
        }
    }

    @Test(expected = AuthorizationException::class)
    fun addProjectMemberWithUserThatDoNotBelongToProjectTest() {
        var pid = -1
        try {
            // Create project with user A
            pid = projectService.create(UserUtilsTest.userA.id, Project(name = name))

            // Add user M to project created using user C
            projectService.addProjectMember(UserUtilsTest.userC.id, Member(pid, UserUtilsTest.userM.username))

        } finally {
            // Delete data used for test
            projectService.deleteProject(UserUtilsTest.userA.id, pid)
        }
    }

    @Test
    fun addProjectMemberWithADifferentUserThatCreatedItTest() {
        var pid = -1
        try {
            // Create project
            pid = projectService.create(UserUtilsTest.userA.id, Project(name = name))

            // Add user C to project created using creator
            projectService.addProjectMember(UserUtilsTest.userA.id, Member(pid, UserUtilsTest.userC.username))

            // Add user M to project created using user C
            projectService.addProjectMember(UserUtilsTest.userC.id, Member(pid, UserUtilsTest.userM.username))

            // Read project members
            val members = projectService.readProjectMembers(pid)

            // Assert
            assertEquals(3, members.size) // Project should have three members(creator and two members)

        } finally {
            // Delete data used for test
            projectService.deleteProject(UserUtilsTest.userA.id, pid)
        }
    }

    @Test
    fun removeProjectMember() {
        var pid = -1
        try {
            // Create project
            pid = projectService.create(UserUtilsTest.userA.id, Project(name = name))

            // Add user C to project created using creator
            projectService.addProjectMember(UserUtilsTest.userA.id, Member(pid, UserUtilsTest.userC.username))

            // Remove user C from project created using user A
            projectService.removeProjectMember(UserUtilsTest.userA.id, Member(pid, UserUtilsTest.userC.username))

            // Read project members
            val members = projectService.readProjectMembers(pid)

            // Assert
            assertEquals(1, members.size) // Project should have only the creator as member

        } finally {
            // Delete data used for test
            projectService.deleteProject(UserUtilsTest.userA.id, pid)
        }
    }

    @Test(expected = AuthorizationException::class)
    fun removeProjectMemberWithUserThatDoNotBelongToProjectTest() {
        var pid = -1
        try {
            // Create project with user A
            pid = projectService.create(UserUtilsTest.userA.id, Project(name = name))

            // Remove user A from project using user C
            projectService.removeProjectMember(UserUtilsTest.userC.id, Member(pid, UserUtilsTest.userA.username))

        } finally {
            // Delete data used for test
            projectService.deleteProject(UserUtilsTest.userA.id, pid)
        }
    }

    @Test
    fun removeProjectMemberWithADifferentUserThatCreatedItTest() {
        var pid = -1
        try {
            // Create project
            pid = projectService.create(UserUtilsTest.userA.id, Project(name = name))

            // Add user C to project created using creator
            projectService.addProjectMember(UserUtilsTest.userA.id, Member(pid, UserUtilsTest.userC.username))

            // Add user M to project created using creator
            projectService.addProjectMember(UserUtilsTest.userA.id, Member(pid, UserUtilsTest.userM.username))

            // Remove user C from project using user M
            projectService.removeProjectMember(UserUtilsTest.userM.id, Member(pid, UserUtilsTest.userC.username))

            // Read project members
            val members = projectService.readProjectMembers(pid)

            // Assert
            // Project should have only two member (creator added two member and one of the members removed the other)
            assertEquals(2, members.size)

        } finally {
            // Delete data used for test
            projectService.deleteProject(UserUtilsTest.userA.id, pid)
        }
    }
}