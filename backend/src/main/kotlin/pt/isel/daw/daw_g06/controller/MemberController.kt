package pt.isel.daw.daw_g06.controller

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.daw.daw_g06.configs.Uri
import pt.isel.daw.daw_g06.controller.exceptions.JsonPatchException
import pt.isel.daw.daw_g06.controller.representation.input.hateoas.JsonPatchEntry
import pt.isel.daw.daw_g06.controller.representation.input.hateoas.JsonPatchOperation
import pt.isel.daw.daw_g06.controller.representation.output.MembersOutputModel
import pt.isel.daw.daw_g06.controller.representation.output.toOutputModel
import pt.isel.daw.daw_g06.controller.util.HateoasMediaType
import pt.isel.daw.daw_g06.controller.util.validateJsonPatchOperations
import pt.isel.daw.daw_g06.model.Member
import pt.isel.daw.daw_g06.service.implementation.ProjectService

@RestController
class MemberController(private val projectService: ProjectService) {

    private val pathRegex = """/([^/]+)""".toRegex()

    @PatchMapping(path = [Uri.PROJECT_MEMBERS], consumes = [HateoasMediaType.APPLICATION_JSON_PATCH_VALUE])
    fun patchProjectMembers(
            @RequestAttribute("uid") uid: Int,
            @PathVariable("pid") pid: Int,
            @RequestBody body: List<JsonPatchEntry>
    ): ResponseEntity<Unit> {
        // Validate operations
        if (!validateJsonPatchOperations(body, pathRegex))
            throw JsonPatchException("Invalid JSON Patch operation. Allowed operations: { Add, Remove }.")

        body.forEach {
            if (it.op == JsonPatchOperation.REMOVE.op) {
                val (username) = pathRegex.find(it.path)!!.destructured
                projectService.removeProjectMember(uid, Member(project = pid, username = username))
            } else projectService.addProjectMember(uid, Member(project = pid, username = it.value!!))
        }

        return ResponseEntity.noContent().build()
    }

    @GetMapping(
            path = [Uri.PROJECT_MEMBERS],
            produces = [
                MediaType.APPLICATION_JSON_VALUE,
                HateoasMediaType.APPLICATION_JSON_SIREN_VALUE,
                HateoasMediaType.APPLICATION_JSON_HAL_VALUE
            ]
    )
    fun getProjectMembers(
            @RequestAttribute("uid") uid: Int,
            @PathVariable("pid") pid: Int
    ): MembersOutputModel {
        val members = projectService.readProjectMembers(pid).map { it.toOutputModel() }
        return MembersOutputModel(pid, members.size, members)
    }
}