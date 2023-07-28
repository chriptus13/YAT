package pt.isel.daw.daw_g06.controller

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.daw.daw_g06.configs.Uri
import pt.isel.daw.daw_g06.controller.exceptions.JsonPatchException
import pt.isel.daw.daw_g06.controller.representation.input.hateoas.JsonPatchEntry
import pt.isel.daw.daw_g06.controller.representation.input.hateoas.JsonPatchOperation
import pt.isel.daw.daw_g06.controller.representation.output.IssueLabelsOutputModel
import pt.isel.daw.daw_g06.controller.representation.output.ProjectLabelsOutputModel
import pt.isel.daw.daw_g06.controller.representation.output.toOutputModel
import pt.isel.daw.daw_g06.controller.util.HateoasMediaType
import pt.isel.daw.daw_g06.controller.util.validateJsonPatchOperations
import pt.isel.daw.daw_g06.model.Label
import pt.isel.daw.daw_g06.service.contract.ILabelService

@RestController
class LabelController(private val labelService: ILabelService) {

    private val pathRegex = """/([^/]+)""".toRegex()

    @PatchMapping(path = [Uri.PROJECT_LABELS], consumes = [HateoasMediaType.APPLICATION_JSON_PATCH_VALUE])
    fun patchProjectLabels(
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
                val labelName = v.replace("""(%20|\+)""".toRegex(), " ")
                labelService.removeProjectLabel(uid, Label(project = pid, name = labelName))
            } else labelService.addLabelToProject(uid, Label(project = pid, name = it.value!!))
        }

        return ResponseEntity.noContent().build()
    }

    @GetMapping(
            path = [Uri.PROJECT_LABELS],
            produces = [
                MediaType.APPLICATION_JSON_VALUE,
                HateoasMediaType.APPLICATION_JSON_SIREN_VALUE,
                HateoasMediaType.APPLICATION_JSON_HAL_VALUE
            ]
    )
    fun getProjectLabels(
            @PathVariable("pid") pid: Int
    ): ProjectLabelsOutputModel {
        val labels = labelService.readProjectLabels(pid).map { it.toOutputModel() }
        return ProjectLabelsOutputModel(pid, labels.size, labels)
    }

    @PatchMapping(path = [Uri.PROJECT_ISSUE_LABELS], consumes = [HateoasMediaType.APPLICATION_JSON_PATCH_VALUE])
    fun patchIssueLabels(
            @RequestAttribute("uid") uid: Int,
            @PathVariable("pid") pid: Int,
            @PathVariable("iid") iid: Int,
            @RequestBody body: List<JsonPatchEntry>
    ): ResponseEntity<Unit> {
        // Validate operations
        if (!validateJsonPatchOperations(body, pathRegex))
            throw JsonPatchException("Invalid JSON Patch operation. Allowed operations: { Add, Remove }.")

        body.forEach {
            if (it.op == JsonPatchOperation.REMOVE.op) {
                val (v) = pathRegex.find(it.path)!!.destructured
                val labelName = v.replace("""(%20|\+)""".toRegex(), " ")
                labelService.removeIssueLabel(uid, iid, Label(project = pid, name = labelName))
            } else
                labelService.addLabelToIssue(uid, iid, Label(project = pid, name = it.value!!))
        }

        return ResponseEntity.noContent().build()
    }

    @GetMapping(
            path = [Uri.PROJECT_ISSUE_LABELS],
            produces = [
                MediaType.APPLICATION_JSON_VALUE,
                HateoasMediaType.APPLICATION_JSON_SIREN_VALUE,
                HateoasMediaType.APPLICATION_JSON_HAL_VALUE
            ]
    )
    fun getIssueLabels(
            @PathVariable("pid") pid: Int,
            @PathVariable("iid") iid: Int
    ): IssueLabelsOutputModel {
        val labels = labelService.readIssueLabels(pid, iid).map { it.toOutputModel() }
        return IssueLabelsOutputModel(pid, iid, labels.size, labels)
    }
}