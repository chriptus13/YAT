package pt.isel.daw.daw_g06.controller.representation.output

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import pt.isel.daw.daw_g06.configs.Hateos
import pt.isel.daw.daw_g06.configs.Uri
import pt.isel.daw.daw_g06.controller.representation.output.hateoas.*

data class CommentsOutputModel(
        @JsonIgnore val project: Int,
        @JsonIgnore val issue: Int,
        val count: Int,
        val comments: List<CommentOutputModel>
) : OutputModel() {
    override fun toSirenOutputModel() =
            CommentsOutputModelSiren(
                    mapOf("count" to comments.size),
                    listOf(
                            SirenLink(
                                    listOf(Hateos.Relations.SELF),
                                    Uri.forIssueComments(project, issue).toString(),
                                    "All Comments for Issue $issue"
                            ),
                            SirenLink(
                                    listOf(Hateos.Relations.ISSUE),
                                    Uri.forIssue(project, issue).toString(),
                                    "Issue $issue"
                            )
                    ),
                    listOf(
                            SirenAction(
                                    Hateos.Actions.CREATE_COMMENT,
                                    "Create comment",
                                    HttpMethod.POST.name,
                                    Uri.forIssueComments(project, issue).toString(),
                                    MediaType.APPLICATION_JSON_VALUE,
                                    listOf(
                                            SirenField("text", "text")
                                    )
                            )
                    ),
                    comments.map {
                        SirenEntity(
                                listOf(Hateos.Classes.COMMENT),
                                listOf(Hateos.Relations.ITEM),
                                mapOf(
                                        "creator" to it.creator,
                                        "text" to it.text,
                                        "created" to it.created
                                ),
                                listOf(SirenLink(
                                        listOf(Hateos.Relations.SELF),
                                        Uri.forComment(it.project, it.issue, it.id).toString(),
                                        "Comment ${it.id}"
                                ))
                        )
                    }
            )

    override fun toHalOutputModel() =
            CommentsOutputModelHal(
                    comments.size,
                    mapOf(
                            Hateos.Relations.SELF to HalLink(Uri.forIssueComments(project, issue).toString()),
                            Hateos.Relations.ISSUE to HalLink(Uri.forIssue(project, issue).toString())
                    ),
                    mapOf(
                            "comments" to comments.map {
                                ShortCommentOutputModelHal(
                                        it.creator,
                                        it.text,
                                        mapOf(Hateos.Relations.SELF to HalLink(Uri.forComment(it.project, it.issue, it.id).toString()))
                                )
                            }
                    )
            )
}

data class CommentsOutputModelHal(
        val count: Int,
        override val _links: Map<String, HalLink>,
        override val _embedded: Map<String, List<SubHal>>
) : Hal(_links = _links,
        _embedded = _embedded
)

data class CommentsOutputModelSiren(
        override val properties: Map<String, Any?>,
        override val links: List<SirenLink>,
        override val actions: List<SirenAction>,
        override val entities: List<SirenEntity>
) : Siren(`class` = listOf(Hateos.Classes.COMMENTS),
        properties = properties,
        links = links,
        actions = actions,
        entities = entities
)