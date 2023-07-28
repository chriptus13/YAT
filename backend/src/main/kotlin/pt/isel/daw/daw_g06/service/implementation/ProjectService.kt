package pt.isel.daw.daw_g06.service.implementation

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pt.isel.daw.daw_g06.model.Member
import pt.isel.daw.daw_g06.model.Project
import pt.isel.daw.daw_g06.repository.contract.IProjectRepository
import pt.isel.daw.daw_g06.repository.contract.IUserRepository
import pt.isel.daw.daw_g06.repository.exceptions.EntityNotFoundException
import pt.isel.daw.daw_g06.repository.exceptions.InvalidOperationException
import pt.isel.daw.daw_g06.service.contract.IProjectService
import pt.isel.daw.daw_g06.service.exceptions.AuthorizationException

@Service
class ProjectService(
        private val projectRepository: IProjectRepository,
        private val userRepository: IUserRepository
) : IProjectService {

    @Transactional
    override fun create(uid: Int, project: Project): Int {
        val pid = projectRepository.createProject(project)
        projectRepository.addProjectMember(pid, uid)
        return pid
    }

    override fun readProjects() = projectRepository.readProjects()

    override fun readProject(pid: Int) = projectRepository.readProject(pid)

    override fun updateProject(uid: Int, project: Project) {
        validateProjectAndUserPermission(uid, project.id)
        projectRepository.updateProject(project)
    }

    override fun updateProjectName(uid: Int, pid: Int, name: String) {
        val old = validateProjectAndUserPermission(uid, pid)
        projectRepository.updateProject(old.replacing(name = name))
    }

    override fun updateProjectDescription(uid: Int, pid: Int, description: String) {
        val old = validateProjectAndUserPermission(uid, pid)
        projectRepository.updateProject(old.replacing(description = description))
    }

    override fun updateProjectInitialState(uid: Int, pid: Int, state: String) {
        val old = validateProjectAndUserPermission(uid, pid)
        projectRepository.updateProject(old.replacing(initialState = state))
    }

    override fun deleteProject(uid: Int, pid: Int) {
        validateProjectAndUserPermission(uid, pid)
        projectRepository.deleteProject(pid)
    }

    override fun addProjectMember(uid: Int, member: Member) {
        validateProjectAndUserPermission(uid, member.project)
        val user = userRepository.readUser(member.username)
        projectRepository.addProjectMember(member.project, user.id)
    }

    override fun readProjectMembers(pid: Int): List<Member> {
        projectRepository.readProject(pid)
        return projectRepository.readProjectMembers(pid)
    }

    override fun removeProjectMember(uid: Int, member: Member) {
        validateProjectAndUserPermission(uid, member.project)
        val user = userRepository.readUser(member.username)
        if (user.id == uid)
            throw InvalidOperationException("Users cannot remove themselves.")
        projectRepository.removeProjectMember(member.project, user.id)
    }

    private fun validateProjectAndUserPermission(uid: Int, pid: Int): Project {
        try {
            val project = projectRepository.readProject(pid)
            projectRepository.validateMemberInProject(uid, pid)
            return project
        } catch (e: EntityNotFoundException) {
            if (e.detail == "Project not found.") throw e
            throw AuthorizationException("User does not have permission to change project with id $pid.")
        }
    }
}