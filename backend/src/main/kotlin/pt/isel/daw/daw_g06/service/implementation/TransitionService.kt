package pt.isel.daw.daw_g06.service.implementation

import org.springframework.stereotype.Service
import pt.isel.daw.daw_g06.model.Transition
import pt.isel.daw.daw_g06.repository.contract.IProjectRepository
import pt.isel.daw.daw_g06.repository.contract.ITransitionRepository
import pt.isel.daw.daw_g06.service.contract.ITransitionService

@Service
class TransitionService(
        private val projectRepository: IProjectRepository,
        private val transitionRepository: ITransitionRepository
) : ITransitionService {

    override fun addTransition(uid: Int, transition: Transition): Int {
        projectRepository.validateMemberInProject(uid, transition.project)
        return transitionRepository.addProjectTransition(transition.project, transition.startState, transition.endState)
    }

    override fun readProjectTransitions(pid: Int): List<Transition> {
        projectRepository.readProject(pid) // Validate project existence
        return transitionRepository.readProjectTransitions(pid)
    }

    override fun removeProjectTransition(uid: Int, pid: Int, tid: Int) {
        projectRepository.validateMemberInProject(uid, pid)
        transitionRepository.removeProjectTransition(pid, tid)
    }

    override fun readProjectTransition(pid: Int, tid: Int): Transition {
        projectRepository.readProject(pid) // Validate project existence
        return transitionRepository.readProjectTransition(pid, tid)
    }
}