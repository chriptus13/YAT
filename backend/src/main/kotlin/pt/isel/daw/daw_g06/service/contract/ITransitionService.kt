package pt.isel.daw.daw_g06.service.contract

import pt.isel.daw.daw_g06.model.Transition

interface ITransitionService {
    fun addTransition(uid: Int, transition: Transition): Int
    fun readProjectTransitions(pid: Int): List<Transition>
    fun readProjectTransition(pid: Int, tid: Int): Transition
    fun removeProjectTransition(uid: Int, pid: Int, tid: Int)
}