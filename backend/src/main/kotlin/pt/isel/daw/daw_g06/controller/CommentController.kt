package pt.isel.daw.daw_g06.controller

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.daw.daw_g06.configs.Uri
import pt.isel.daw.daw_g06.controller.representation.input.CommentInputModel
import pt.isel.daw.daw_g06.controller.representation.output.CommentsOutputModel
import pt.isel.daw.daw_g06.controller.representation.output.toOutputModel
import pt.isel.daw.daw_g06.controller.util.HateoasMediaType
import pt.isel.daw.daw_g06.service.contract.ICommentService

@RestController
class CommentController(private val commentService: ICommentService) {

    @PostMapping(path = [Uri.PROJECT_ISSUE_COMMENTS], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createComment(
            @RequestAttribute("uid") uid: Int,
            @PathVariable("pid") pid: Int,
            @PathVariable("iid") iid: Int,
            @RequestBody body: CommentInputModel
    ): ResponseEntity<Unit> {
        val cid = commentService.create(uid, pid, body.mapToComment(iid))
        return ResponseEntity
                .created(Uri.forComment(pid, iid, cid))
                .build()
    }

    @GetMapping(
            path = [Uri.PROJECT_ISSUE_COMMENTS],
            produces = [
                MediaType.APPLICATION_JSON_VALUE,
                HateoasMediaType.APPLICATION_JSON_SIREN_VALUE,
                HateoasMediaType.APPLICATION_JSON_HAL_VALUE
            ]
    )
    fun getComments(
            @PathVariable("pid") pid: Int,
            @PathVariable("iid") iid: Int
    ): CommentsOutputModel {
        val comments = commentService.readComments(pid, iid).map { it.toOutputModel(pid) }
        return CommentsOutputModel(pid, iid, comments.size, comments)
    }

    @GetMapping(
            path = [Uri.PROJECT_ISSUE_COMMENT],
            produces = [
                MediaType.APPLICATION_JSON_VALUE,
                HateoasMediaType.APPLICATION_JSON_SIREN_VALUE,
                HateoasMediaType.APPLICATION_JSON_HAL_VALUE
            ]
    )
    fun getComment(
            @PathVariable("pid") pid: Int,
            @PathVariable("iid") iid: Int,
            @PathVariable("cid") cid: Int
    ) = commentService.readComment(pid, iid, cid).toOutputModel(pid)

    @PutMapping(path = [Uri.PROJECT_ISSUE_COMMENT], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun updateComment(
            @RequestAttribute("uid") uid: Int,
            @PathVariable("pid") pid: Int,
            @PathVariable("iid") iid: Int,
            @PathVariable("cid") cid: Int,
            @RequestBody body: CommentInputModel
    ): ResponseEntity<Unit> {
        commentService.updateComment(uid, pid, iid, cid, body.text)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping(path = [Uri.PROJECT_ISSUE_COMMENT])
    fun deleteComment(
            @RequestAttribute("uid") uid: Int,
            @PathVariable("pid") pid: Int,
            @PathVariable("iid") iid: Int,
            @PathVariable("cid") cid: Int
    ): ResponseEntity<Unit> {
        commentService.deleteComment(uid, pid, iid, cid)
        return ResponseEntity.noContent().build()
    }
}