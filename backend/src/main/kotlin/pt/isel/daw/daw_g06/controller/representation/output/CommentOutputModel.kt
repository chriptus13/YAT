package pt.isel.daw.daw_g06.controller.representation.output

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import pt.isel.daw.daw_g06.configs.Hateos
import pt.isel.daw.daw_g06.configs.Uri
import pt.isel.daw.daw_g06.controller.representation.output.hateoas.*
import pt.isel.daw.daw_g06.model.Comment
import java.util.*

data class CommentOutputModel(
        val id: Int,
        @JsonIgnore
        val project: Int,
        @JsonIgnore
        val issue: Int,
        val creator: String,
        val text: String,
        val created: Date?
) : OutputModel() {
    override fun toSirenOutputModel() =
            CommentOutputModelSiren(
                    mapOf(
                            "creator" to creator,
                            "text" to text,
                            "created" to created
                    ),
                    listOf(
                            SirenLink(
                                    listOf(Hateos.Relations.SELF),
                                    Uri.forComment(project, issue, id).toString(),
                                    "Comment $id"
                            ),
                            SirenLink(
                                    listOf(Hateos.Relations.ISSUE),
                                    Uri.forIssue(project, issue).toString(),
                                    "Issue $issue"
                            )
                    ),
                    listOf(
                            SirenAction(
                                    Hateos.Actions.DELETE_COMMENT,
                                    "Delete comment",
                                    HttpMethod.DELETE.name,
                                    Uri.forComment(project, issue, id).toString()
                            ),
                            SirenAction(
                                    Hateos.Actions.UPDATE_COMMENT,
                                    "Update comment",
                                    HttpMethod.PUT.name,
                                    Uri.forComment(project, issue, id).toString(),
                                    MediaType.APPLICATION_JSON_VALUE,
                                    listOf(
                                            SirenField("text", "text")
                                    )
                            )
                    )
            )

    override fun toHalOutputModel() =
            CommentOutputModelHal(
                    creator,
                    text,
                    created,
                    mapOf(
                            Hateos.Relations.SELF to HalLink(Uri.forComment(project, issue, id).toString()),
                            Hateos.Relations.ISSUE to HalLink(Uri.forIssue(project, issue).toString())
                    )
            )
}

data class ShortCommentOutputModelHal(
        val creator: String,
        val text: String,
        override val _links: Map<String, HalLink>
) : SubHal(_links = _links)

data class CommentOutputModelHal(
        val creator: String,
        val text: String,
        val created: Date?,
        override val _links: Map<String, HalLink>
) : Hal(_links = _links)

data class CommentOutputModelSiren(
        override val properties: Map<String, Any?>,
        override val links: List<SirenLink>,
        override val actions: List<SirenAction>
) : Siren(`class` = listOf(Hateos.Classes.COMMENT),
        properties = properties,
        links = links,
        actions = actions,
        entities = listOf()
)

fun Comment.toOutputModel(pid: Int) = CommentOutputModel(id, pid, issue, creator, text, created)