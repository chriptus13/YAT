package pt.isel.daw.daw_g06.service.implementation

import org.springframework.stereotype.Service
import pt.isel.daw.daw_g06.model.State
import pt.isel.daw.daw_g06.repository.contract.IProjectRepository
import pt.isel.daw.daw_g06.repository.contract.IStateRepository
import pt.isel.daw.daw_g06.repository.exceptions.EntityNotFoundException
import pt.isel.daw.daw_g06.service.contract.IStateService
import pt.isel.daw.daw_g06.service.exceptions.AuthorizationException

@Service
class StateService(
        private val projectRepository: IProjectRepository,
        private val stateRepository: IStateRepository
) : IStateService {
    override fun addState(uid: Int, state: State) {
        validateMemberInProject(uid, state.project)
        stateRepository.addProjectState(state.project, state.name)
    }

    override fun readStates(pid: Int): List<State> {
        projectRepository.readProject(pid) // Validate project existence
        return stateRepository.readProjectStates(pid)
    }

    override fun removeState(uid: Int, state: State) {
        validateMemberInProject(uid, state.project)
        stateRepository.removeProjectState(state.project, state.name)
    }

    private fun validateMemberInProject(uid: Int, pid: Int) {
        try {
            projectRepository.readProject(pid)
            projectRepository.validateMemberInProject(uid, pid)
        } catch (e: EntityNotFoundException) {
            if (e.detail == "Project not found.") throw e
            throw AuthorizationException(
                    "User does not have permission to manage the set of available set of the project with id $pid."
            )
        }
    }
}