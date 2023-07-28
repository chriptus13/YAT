package pt.isel.daw.daw_g06.repository

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import pt.isel.daw.daw_g06.model.Issue
import pt.isel.daw.daw_g06.model.Project
import pt.isel.daw.daw_g06.repository.contract.IIssueRepository
import pt.isel.daw.daw_g06.repository.contract.IProjectRepository
import pt.isel.daw.daw_g06.utils.UserUtilsTest

@RunWith(SpringRunner::class)
@SpringBootTest
class IssueRepositoryTest {
    @Autowired
    private lateinit var projectRepository: IProjectRepository

    @Autowired
    private lateinit var issueRepository: IIssueRepository

    val pName = "Test Project"
    val pState = "archived"
    val iName = "Test Issue"
    val iDescription = "Test Issue Description"
    val iNewName = "Test New Issue"
    val iNewDescription = "Test New Issue Description"

    @Test
    fun getIssueTest() {
        var pid = -1
        try {
            //Create project and issue
            pid = projectRepository.createProject(Project(name = pName))
            projectRepository.updateProject(Project(id = pid, name = pName, initialState = pState))
            val iid = issueRepository.createIssue(
                    UserUtilsTest.userA.id,
                    Issue(project = pid, name = iName, description = iDescription)
            )

            // Operation to test
            val issue = issueRepository.readIssue(pid, iid)

            assertEquals(iid, issue.id)
            assertEquals(pid, issue.project)
            assertEquals(UserUtilsTest.userA.username, issue.creator)
            assertEquals(iName, issue.name)
            assertEquals(iDescription, issue.description)
            assertEquals(pState, issue.state)
        } finally {
            // Delete data used for test
            projectRepository.deleteProject(pid)
        }
    }

    @Test
    fun getIssuesTest() {
        var pid = -1
        try {
            //Create project and issue
            pid = projectRepository.createProject(Project(name = pName))
            projectRepository.updateProject(Project(id = pid, name = pName, initialState = pState))
            val iid = issueRepository.createIssue(
                    UserUtilsTest.userA.id,
                    Issue(project = pid, name = iName, description = iDescription)
            )

            // Operation to test
            val issues = issueRepository.readIssues(pid)
            val issue = issues.find { it.id == iid }

            assertEquals(iid, issue?.id)
            assertEquals(pid, issue?.project)
            assertEquals(UserUtilsTest.userA.username, issue?.creator)
            assertEquals(iName, issue?.name)
            assertEquals(iDescription, issue?.description)
            assertEquals(pState, issue?.state)
        } finally {
            // Delete data used for test
            projectRepository.deleteProject(pid)
        }
    }

    @Test
    fun updateIssueTest() {
        var pid = -1
        try {
            //Create project and issue
            pid = projectRepository.createProject(Project(name = pName))
            projectRepository.updateProject(Project(id = pid, name = pName, initialState = pState))
            val iid = issueRepository.createIssue(
                    UserUtilsTest.userA.id,
                    Issue(project = pid, name = iName, description = iDescription)
            )

            // Operation to Test
            issueRepository.updateIssue(
                    Issue(id = iid, project = pid, name = iNewName, description = iNewDescription, state = pState)
            )
            val issue = issueRepository.readIssue(pid, iid)

            assertEquals(iid, issue.id)
            assertEquals(pid, issue.project)
            assertEquals(iNewName, issue.name)
            assertEquals(iNewDescription, issue.description)
        } finally {
            // Delete data used for test
            projectRepository.deleteProject(pid)
        }
    }

    @Test
    fun deleteIssueTest() {
        var pid = -1
        try {
            //Create project and issue
            pid = projectRepository.createProject(Project(name = pName))
            projectRepository.updateProject(Project(id = pid, name = pName, initialState = pState))
            val iid = issueRepository.createIssue(
                    UserUtilsTest.userA.id,
                    Issue(project = pid, name = iName, description = iDescription)
            )

            // Operation to test
            issueRepository.deleteIssue(pid, iid)
            val issues = issueRepository.readIssues(pid)
            val issue = issues.find { it.id == iid }

            assertNull(issue)
        } finally {
            // Delete data used for test
            projectRepository.deleteProject(pid)
        }
    }
}