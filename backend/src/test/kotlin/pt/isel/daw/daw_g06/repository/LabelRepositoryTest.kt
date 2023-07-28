package pt.isel.daw.daw_g06.repository

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import pt.isel.daw.daw_g06.model.Issue
import pt.isel.daw.daw_g06.model.Project
import pt.isel.daw.daw_g06.repository.contract.IIssueRepository
import pt.isel.daw.daw_g06.repository.contract.ILabelRepository
import pt.isel.daw.daw_g06.repository.contract.IProjectRepository
import pt.isel.daw.daw_g06.repository.exceptions.EntityNotFoundException
import pt.isel.daw.daw_g06.utils.UserUtilsTest

@RunWith(SpringRunner::class)
@SpringBootTest
class LabelRepositoryTest {
    @Autowired
    private lateinit var labelRepository: ILabelRepository

    @Autowired
    private lateinit var projectRepository: IProjectRepository

    @Autowired
    private lateinit var issueRepository: IIssueRepository

    val pName = "Test Project"
    val pState = "closed"
    val iName = "Test Issue"
    val iDescription = "Test Issue Description"
    val lNameA = "Test Label A"
    val lNameB = "Test Label B"

    @Test
    fun addLabelToProjectTest() {
        var pid = -1
        try {
            // Create project
            pid = projectRepository.createProject(Project(name = pName))
            // Add labels to project
            labelRepository.addProjectLabel(pid, lNameA)
            labelRepository.addProjectLabel(pid, lNameB)

            val labels = labelRepository.readProjectLabels(pid)

            val labelA = labels.find { it.name == lNameA }
            val labelB = labels.find { it.name == lNameB }

            assertEquals(lNameA, labelA?.name)
            assertEquals(pid, labelA?.project)
            assertEquals(lNameB, labelB?.name)
            assertEquals(pid, labelB?.project)
        } finally {
            projectRepository.deleteProject(pid)
        }
    }

    @Test(expected = EntityNotFoundException::class)
    fun addNonExistentLabelInProjectToIssue() {
        var pid = -1
        try {
            // Create project and issue
            pid = projectRepository.createProject(Project(name = pName))
            projectRepository.updateProject(Project(id = pid, name = pName, initialState = pState))
            val iid = issueRepository.createIssue(
                    UserUtilsTest.userA.id,
                    Issue(project = pid, name = iName, description = iDescription)
            )
            // Add label to issue
            labelRepository.addIssueLabel(pid, iid, lNameA)
        } finally {
            projectRepository.deleteProject(pid)
        }
    }


    @Test
    fun addLabelToIssueTest() {
        var pid = -1
        try {
            // Create project and issue
            pid = projectRepository.createProject(Project(name = pName))
            projectRepository.updateProject(Project(id = pid, name = pName, initialState = pState))
            val iid = issueRepository.createIssue(
                    UserUtilsTest.userA.id,
                    Issue(project = pid, name = iName, description = iDescription)
            )
            // Add label to project and issue
            labelRepository.addProjectLabel(pid, lNameA)
            labelRepository.addIssueLabel(pid, iid, lNameA)

            val labels = labelRepository.readIssueLabels(pid, iid)
            val label = labels.find { it.name == lNameA }

            assertNotNull(label)
        } finally {
            projectRepository.deleteProject(pid)
        }
    }

    @Test
    fun deleteLabelFromProject() {
        var pid = -1
        try {
            // Create project and issue
            pid = projectRepository.createProject(Project(name = pName))

            // Add label to project and remove it
            labelRepository.addProjectLabel(pid, lNameA)
            labelRepository.addProjectLabel(pid, lNameB)

            var labels = labelRepository.readProjectLabels(pid)
            assertEquals(2, labels.size)
            labelRepository.removeProjectLabel(pid, lNameA)
            labels = labelRepository.readProjectLabels(pid)
            assertEquals(1, labels.size)
        } finally {
            projectRepository.deleteProject(pid)
        }
    }

    @Test
    fun deleteLabelFromIssue() {
        var pid = -1
        try {
            // Create project and issue
            pid = projectRepository.createProject(Project(name = pName))
            projectRepository.updateProject(Project(id = pid, name = pName, initialState = pState))
            val iid = issueRepository.createIssue(
                    UserUtilsTest.userA.id,
                    Issue(project = pid, name = iName, description = iDescription)
            )

            // Add label to project and remove it
            labelRepository.addProjectLabel(pid, lNameA)
            labelRepository.addIssueLabel(pid, iid, lNameA)
            var labels = labelRepository.readIssueLabels(pid, iid)
            assertEquals(1, labels.size)
            labelRepository.removeIssueLabel(pid, iid, lNameA)
            labels = labelRepository.readIssueLabels(pid, iid)
            assertEquals(0, labels.size)
        } finally {
            projectRepository.deleteProject(pid)
        }
    }
}