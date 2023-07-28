package pt.isel.daw.daw_g06.controller

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.daw.daw_g06.configs.Uri
import pt.isel.daw.daw_g06.controller.representation.input.DescriptionInputModel
import pt.isel.daw.daw_g06.controller.representation.input.IssueInputModel
import pt.isel.daw.daw_g06.controller.representation.input.NameInputModel
import pt.isel.daw.daw_g06.controller.representation.input.StateInputModel
import pt.isel.daw.daw_g06.controller.representation.output.IssuesOutputModel
import pt.isel.daw.daw_g06.controller.representation.output.toOutputModel
import pt.isel.daw.daw_g06.controller.util.HateoasMediaType
import pt.isel.daw.daw_g06.service.contract.IIssueService

@RestController
class IssueController(private val issueService: IIssueService) {

    @PostMapping(path = [Uri.PROJECT_ISSUES], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createIssue(
            @RequestAttribute("uid") uid: Int,
            @PathVariable("pid") pid: Int,
            @RequestBody body: IssueInputModel
    ): ResponseEntity<Unit> {
        val iid = issueService.create(uid, body.mapToIssue(pid))
        return ResponseEntity
                .created(Uri.forIssue(pid, iid))
                .build()
    }

    @GetMapping(
            path = [Uri.PROJECT_ISSUES],
            produces = [
                MediaType.APPLICATION_JSON_VALUE,
                HateoasMediaType.APPLICATION_JSON_SIREN_VALUE,
                HateoasMediaType.APPLICATION_JSON_HAL_VALUE
            ]
    )
    fun getIssues(
            @PathVariable("pid") pid: Int
    ): IssuesOutputModel {
        val issues = issueService.readIssues(pid).map { it.toOutputModel() }
        return IssuesOutputModel(pid, issues.size, issues)
    }

    @GetMapping(
            path = [Uri.PROJECT_ISSUE],
            produces = [
                MediaType.APPLICATION_JSON_VALUE,
                HateoasMediaType.APPLICATION_JSON_SIREN_VALUE,
                HateoasMediaType.APPLICATION_JSON_HAL_VALUE
            ]
    )
    fun getIssue(
            @PathVariable("pid") pid: Int,
            @PathVariable("iid") iid: Int
    ) = issueService.readIssue(pid, iid).toOutputModel()

    @PutMapping(path = [Uri.PROJECT_ISSUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun updateIssue(
            @RequestAttribute("uid") uid: Int,
            @PathVariable("pid") pid: Int,
            @PathVariable("iid") iid: Int,
            @RequestBody body: IssueInputModel
    ): ResponseEntity<Unit> {
        issueService.updateIssue(uid, body.mapToIssue(pid, iid))
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping(path = [Uri.PROJECT_ISSUE])
    fun deleteIssue(
            @RequestAttribute("uid") uid: Int,
            @PathVariable("pid") pid: Int,
            @PathVariable("iid") iid: Int
    ): ResponseEntity<Unit> {
        issueService.deleteIssue(uid, pid, iid)
        return ResponseEntity.noContent().build()
    }

    @PatchMapping(path = [Uri.PROJECT_ISSUE_NAME], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun updateIssueName(
            @RequestAttribute("uid") uid: Int,
            @PathVariable("pid") pid: Int,
            @PathVariable("iid") iid: Int,
            @RequestBody body: NameInputModel
    ): ResponseEntity<Unit> {
        issueService.updateIssueName(uid, pid, iid, body.name)
        return ResponseEntity.noContent().build()
    }

    @PatchMapping(path = [Uri.PROJECT_ISSUE_DESCRIPTION], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun updateIssueDescription(
            @RequestAttribute("uid") uid: Int,
            @PathVariable("pid") pid: Int,
            @PathVariable("iid") iid: Int,
            @RequestBody body: DescriptionInputModel
    ): ResponseEntity<Unit> {
        issueService.updateIssueDescription(uid, pid, iid, body.description)
        return ResponseEntity.noContent().build()
    }

    @PatchMapping(path = [Uri.PROJECT_ISSUE_STATE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun updateIssueState(
            @RequestAttribute("uid") uid: Int,
            @PathVariable("pid") pid: Int,
            @PathVariable("iid") iid: Int,
            @RequestBody body: StateInputModel
    ): ResponseEntity<Unit> {
        issueService.updateIssueState(uid, pid, iid, body.name)
        return ResponseEntity.noContent().build()
    }
}