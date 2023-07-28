package pt.isel.daw.daw_g06.service.contract

import pt.isel.daw.daw_g06.model.Member
import pt.isel.daw.daw_g06.model.Project

interface IProjectService {
    fun create(uid: Int, project: Project): Int
    fun readProjects(): List<Project>
    fun readProject(pid: Int): Project
    fun updateProject(uid: Int, project: Project)
    fun updateProjectName(uid: Int, pid: Int, name: String)
    fun updateProjectDescription(uid: Int, pid: Int, description: String)
    fun updateProjectInitialState(uid: Int, pid: Int, state: String)
    fun deleteProject(uid: Int, pid: Int)
    fun addProjectMember(uid: Int, member: Member)
    fun readProjectMembers(pid: Int): List<Member>
    fun removeProjectMember(uid: Int, member: Member)
}