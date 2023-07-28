package pt.isel.daw.daw_g06.controller

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.daw.daw_g06.configs.Uri
import pt.isel.daw.daw_g06.controller.exceptions.JsonPatchException
import pt.isel.daw.daw_g06.controller.representation.input.hateoas.JsonPatchEntry
import pt.isel.daw.daw_g06.controller.representation.input.hateoas.JsonPatchOperation
import pt.isel.daw.daw_g06.controller.representation.output.StatesOutputModel
import pt.isel.daw.daw_g06.controller.representation.output.toOutputModel
import pt.isel.daw.daw_g06.controller.util.HateoasMediaType
import pt.isel.daw.daw_g06.controller.util.validateJsonPatchOperations
import pt.isel.daw.daw_g06.model.State
import pt.isel.daw.daw_g06.service.contract.IStateService

@RestController
class StateController(private val stateService: IStateService) {

    private val pathRegex = """/([^/]+)""".toRegex()

    @PatchMapping(path = [Uri.PROJECT_STATES], consumes = [HateoasMediaType.APPLICATION_JSON_PATCH_VALUE])
    fun patchProjectStates(
            @RequestAttribute("uid") uid: Int,
            @PathVariable("pid") pid: Int,
            @RequestBody body: List<JsonPatchEntry>
    ): ResponseEntity<Unit> {
        // Validate operations
        if (!validateJsonPatchOperations(body, pathRegex))
            throw JsonPatchException("Invalid JSON Patch operation. Allowed operations: { Add, Remove }.")

        body.forEach {
            if (it.op == JsonPatchOperation.REMOVE.op) {
                val (v) = pathRegex.find(it.path)!!.destructured
                val stateName = v.replace("""(%20|\+)""".toRegex(), " ")
                stateService.removeState(uid, State(pid, stateName))
            } else stateService.addState(uid, State(pid, it.value!!))
        }

        return ResponseEntity.noContent().build()
    }

    @GetMapping(
            path = [Uri.PROJECT_STATES],
            produces = [
                MediaType.APPLICATION_JSON_VALUE,
                HateoasMediaType.APPLICATION_JSON_SIREN_VALUE,
                HateoasMediaType.APPLICATION_JSON_HAL_VALUE
            ]
    )
    fun getProjectStates(
            @RequestAttribute("uid") uid: Int,
            @PathVariable("pid") pid: Int
    ): StatesOutputModel {
        val states = stateService.readStates(pid).map { it.toOutputModel() }
        return StatesOutputModel(pid, states.size, states)
    }
}