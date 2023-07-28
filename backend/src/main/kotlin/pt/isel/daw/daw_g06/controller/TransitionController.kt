package pt.isel.daw.daw_g06.controller

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.daw.daw_g06.configs.Uri
import pt.isel.daw.daw_g06.controller.representation.input.TransitionInputModel
import pt.isel.daw.daw_g06.controller.representation.output.TransitionsOutputModel
import pt.isel.daw.daw_g06.controller.representation.output.toOutputModel
import pt.isel.daw.daw_g06.controller.util.HateoasMediaType
import pt.isel.daw.daw_g06.service.contract.ITransitionService

@RestController
class TransitionController(private val transitionService: ITransitionService) {

    @PostMapping(path = [Uri.PROJECT_TRANSITIONS], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun addProjectTransition(
            @RequestAttribute("uid") uid: Int,
            @PathVariable("pid") pid: Int,
            @RequestBody body: TransitionInputModel
    ): ResponseEntity<Unit> {
        val tid = transitionService.addTransition(uid, body.mapToTransition(pid))
        return ResponseEntity
                .created(Uri.forTransition(pid, tid))
                .build()
    }

    @GetMapping(
            path = [Uri.PROJECT_TRANSITIONS],
            produces = [
                MediaType.APPLICATION_JSON_VALUE,
                HateoasMediaType.APPLICATION_JSON_SIREN_VALUE,
                HateoasMediaType.APPLICATION_JSON_HAL_VALUE
            ]
    )
    fun getProjectTransitions(
            @PathVariable("pid") pid: Int
    ): TransitionsOutputModel {
        val transitions = transitionService.readProjectTransitions(pid).map { it.toOutputModel() }
        return TransitionsOutputModel(pid, transitions.size, transitions)
    }

    @GetMapping(
            path = [Uri.PROJECT_TRANSITION],
            produces = [
                MediaType.APPLICATION_JSON_VALUE,
                HateoasMediaType.APPLICATION_JSON_SIREN_VALUE,
                HateoasMediaType.APPLICATION_JSON_HAL_VALUE
            ]
    )
    fun getProjectTransition(
            @PathVariable("pid") pid: Int,
            @PathVariable("tid") tid: Int
    ) = transitionService.readProjectTransition(pid, tid).toOutputModel()

    @DeleteMapping(path = [Uri.PROJECT_TRANSITION])
    fun deleteProjectTransition(
            @RequestAttribute("uid") uid: Int,
            @PathVariable("pid") pid: Int,
            @PathVariable("tid") tid: Int
    ) = transitionService.removeProjectTransition(uid, pid, tid)
}