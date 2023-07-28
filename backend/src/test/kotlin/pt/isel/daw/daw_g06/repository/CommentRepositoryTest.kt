package pt.isel.daw.daw_g06.repository

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import pt.isel.daw.daw_g06.model.Comment
import pt.isel.daw.daw_g06.model.Issue
import pt.isel.daw.daw_g06.model.Project
import pt.isel.daw.daw_g06.repository.contract.ICommentRepository
import pt.isel.daw.daw_g06.repository.contract.IIssueRepository
import pt.isel.daw.daw_g06.repository.contract.IProjectRepository
import pt.isel.daw.daw_g06.repository.exceptions.ArchivedCommentException
import pt.isel.daw.daw_g06.utils.UserUtilsTest

@RunWith(SpringRunner::class)
@SpringBootTest
class CommentRepositoryTest {
    @Autowired
    private lateinit var commentRepository: ICommentRepository

    @Autowired
    private lateinit var projectRepository: IProjectRepository

    @Autowired
    private lateinit var issueRepository: IIssueRepository

    val pName = "Test Project"
    val closedState = "closed"
    val archivedState = "archived"
    val iName = "Test Issue"
    val iDescription = "Test Issue Description"
    val cText = "Text for comment"
    val cNewText = "New Text for comment"

    @Test
    fun addAndReadCommentTest() {
        var pid = -1
        try {
            // Create project and issue
            pid = projectRepository.createProject(Project(name = pName))
            projectRepository.updateProject(Project(id = pid, name = pName, initialState = closedState))

            val iid = issueRepository.createIssue(
                    UserUtilsTest.userA.id,
                    Issue(project = pid, name = iName, description = iDescription)
            )

            // Operations to test
            val cid = commentRepository.createComment(UserUtilsTest.userA.id, pid,
                    Comment(issue = iid, creator = UserUtilsTest.userA.username, text = cText)
            )
            val comment = commentRepository.readComment(pid, iid, cid)

            assertEquals(iid, comment.issue)
            assertEquals(cid, comment.id)
            assertEquals(cText, comment.text)
            assertEquals(UserUtilsTest.userA.username, comment.creator)
        } finally {
            // Delete data used for test
            projectRepository.deleteProject(pid)
        }
    }

    @Test
    fun getAllCommentsTest() {
        var pid = -1
        try {
            // Create project and issue
            pid = projectRepository.createProject(Project(name = pName))
            projectRepository.updateProject(Project(id = pid, name = pName, initialState = closedState))

            val iid = issueRepository.createIssue(
                    UserUtilsTest.userA.id,
                    Issue(project = pid, name = iName, description = iDescription)
            )

            // Operations to test
            val cid = commentRepository.createComment(UserUtilsTest.userA.id, pid,
                    Comment(issue = iid, creator = UserUtilsTest.userA.username, text = cText)
            )
            val comments = commentRepository.readComments(pid, iid)
            val comment = comments.find { it.id == cid }

            assertEquals(iid, comment?.issue)
            assertEquals(cid, comment?.id)
            assertEquals(cText, comment?.text)
            assertEquals(UserUtilsTest.userA.username, comment?.creator)
        } finally {
            // Delete data used for test
            projectRepository.deleteProject(pid)
        }
    }

    @Test
    fun updateCommentTest() {
        var pid = -1
        try {
            // Create project and issue
            pid = projectRepository.createProject(Project(name = pName))
            projectRepository.updateProject(Project(id = pid, name = pName, initialState = closedState))

            val iid = issueRepository.createIssue(
                    UserUtilsTest.userA.id,
                    Issue(project = pid, name = iName, description = iDescription)
            )

            // Operations to test
            val cid = commentRepository.createComment(UserUtilsTest.userA.id, pid,
                    Comment(issue = iid, creator = UserUtilsTest.userA.username, text = cText)
            )
            commentRepository.updateComment(
                    pid,
                    Comment(id = cid, issue = iid, creator = UserUtilsTest.userA.username, text = cNewText)
            )
            val comment = commentRepository.readComment(pid, iid, cid)

            assertEquals(iid, comment.issue)
            assertEquals(cid, comment.id)
            assertEquals(cNewText, comment.text)
            assertEquals(UserUtilsTest.userA.username, comment.creator)
        } finally {
            // Delete data used for test
            projectRepository.deleteProject(pid)
        }
    }

    @Test
    fun deleteComment() {
        var pid = -1
        try {
            // Create project and issue
            pid = projectRepository.createProject(Project(name = pName))
            projectRepository.updateProject(Project(id = pid, name = pName, initialState = closedState))

            val iid = issueRepository.createIssue(
                    UserUtilsTest.userA.id,
                    Issue(project = pid, name = iName, description = iDescription)
            )

            // Operations to test
            val cid = commentRepository.createComment(UserUtilsTest.userA.id, pid,
                    Comment(issue = iid, creator = UserUtilsTest.userA.username, text = cText)
            )
            commentRepository.deleteComment(pid, iid, cid)
            val comments = commentRepository.readComments(pid, iid)

            assertEquals(0, comments.size)
        } finally {
            // Delete data used for test
            projectRepository.deleteProject(pid)
        }
    }

    @Test(expected = ArchivedCommentException::class)
    fun tryToAddCommentToArchivedIssue() {
        var pid = -1
        try {
            // Create project and issue
            pid = projectRepository.createProject(Project(name = pName))
            projectRepository.updateProject(Project(id = pid, name = pName, initialState = archivedState))

            val iid = issueRepository.createIssue(
                    UserUtilsTest.userA.id,
                    Issue(project = pid, name = iName, description = iDescription)
            )

            // Operations to test
            commentRepository.createComment(UserUtilsTest.userA.id, pid,
                    Comment(issue = iid, creator = UserUtilsTest.userA.username, text = cText)
            )
        } finally {
            // Delete data used for test
            projectRepository.deleteProject(pid)
        }
    }
}