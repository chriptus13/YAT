package pt.isel.daw.daw_g06.repository.contract

import pt.isel.daw.daw_g06.model.Member
import pt.isel.daw.daw_g06.model.Project

interface IProjectRepository {
    fun createProject(project: Project): Int

    fun readProjects(): List<Project>

    fun readProject(pid: Int): Project

    fun updateProject(project: Project)

    fun deleteProject(pid: Int)

    fun addProjectMember(pid: Int, uid: Int)

    fun removeProjectMember(pid: Int, uid: Int)

    fun readProjectMembers(pid: Int): List<Member>

    fun validateMemberInProject(uid: Int, pid: Int)
}