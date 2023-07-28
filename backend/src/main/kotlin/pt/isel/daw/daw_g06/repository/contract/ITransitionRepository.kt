package pt.isel.daw.daw_g06.repository.contract

import pt.isel.daw.daw_g06.model.Transition

interface ITransitionRepository {
    fun addProjectTransition(pid: Int, startState: String, endState: String): Int
    fun removeProjectTransition(pid: Int, tid: Int)
    fun readProjectTransitions(pid: Int): List<Transition>
    fun readProjectTransition(pid: Int, tid: Int): Transition
}