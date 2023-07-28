package pt.isel.daw.daw_g06.controller.representation.output

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import pt.isel.daw.daw_g06.configs.Hateos
import pt.isel.daw.daw_g06.configs.Uri
import pt.isel.daw.daw_g06.controller.representation.output.hateoas.*
import pt.isel.daw.daw_g06.model.Issue
import java.util.*

data class IssueOutputModel(
        val id: Int,
        @JsonIgnore
        val project: Int,
        val creator: String,
        val name: String,
        val description: String,
        val created: Date,
        val closed: Date?,
        val state: String?
) : OutputModel() {
    override fun toHalOutputModel() = IssueOutputModelHal(
            creator, name, description, created, closed, state,
            mapOf(
                    Hateos.Relations.SELF to HalLink(Uri.forIssue(project, id).toString()),
                    Hateos.Relations.PROJECT to HalLink(Uri.forProject(project).toString()),
                    Hateos.Relations.ISSUE_LABELS to HalLink(Uri.forIssueLabels(project, id).toString()),
                    Hateos.Relations.COMMENTS to HalLink(Uri.forIssueComments(project, id).toString())
            )
    )

    override fun toSirenOutputModel() = IssueOutputModelSiren(
            mapOf(
                    "id" to id,
                    "name" to name,
                    "description" to description,
                    "creator" to creator,
                    "created" to created,
                    "closed" to closed,
                    "state" to state),
            listOf(
                    SirenLink(
                            listOf(Hateos.Relations.SELF),
                            Uri.forIssue(project, id).toString(),
                            "Issue $id"
                    ),
                    SirenLink(
                            listOf(Hateos.Relations.PROJECT),
                            Uri.forProject(project).toString(),
                            "Parent Project $project"
                    ),
                    SirenLink(
                            listOf(Hateos.Relations.ISSUE_LABELS),
                            Uri.forIssueLabels(project, id).toString(),
                            "Issue $id's Labels"
                    ),
                    SirenLink(
                            listOf(Hateos.Relations.COMMENTS),
                            Uri.forProjectLabels(id).toString(),
                            "Issue $id's Comments"
                    )
            ),
            listOf(
                    SirenAction(
                            Hateos.Actions.DELETE_ISSUE,
                            "Delete issue",
                            HttpMethod.DELETE.name,
                            Uri.forIssue(project, id).toString()
                    ),
                    SirenAction(
                            Hateos.Actions.UPDATE_ISSUE_NAME,
                            "Update issue name",
                            HttpMethod.PATCH.name,
                            Uri.forIssueName(project, id).toString(),
                            MediaType.APPLICATION_JSON_VALUE,
                            listOf(
                                    SirenField("name", "text")
                            )
                    ),
                    SirenAction(
                            Hateos.Actions.UPDATE_ISSUE_DESCRIPTION,
                            "Update issue description",
                            HttpMethod.PATCH.name,
                            Uri.forIssueDescription(project, id).toString(),
                            MediaType.APPLICATION_JSON_VALUE,
                            listOf(
                                    SirenField("description", "text")
                            )
                    ),
                    SirenAction(
                            Hateos.Actions.UPDATE_ISSUE_STATE,
                            "Update issue state",
                            HttpMethod.PATCH.name,
                            Uri.forIssueState(project, id).toString(),
                            MediaType.APPLICATION_JSON_VALUE,
                            listOf(
                                    SirenField("state", "text")
                            )
                    ),
                    SirenAction(
                            Hateos.Actions.UPDATE_ISSUE,
                            "Update issue",
                            HttpMethod.PUT.name,
                            Uri.forIssue(project, id).toString(),
                            MediaType.APPLICATION_JSON_VALUE,
                            listOf(
                                    SirenField("name", "text"),
                                    SirenField("description", "text")
                            )
                    )
            )
    )
}

data class ShortIssueOutputModelHal(
        val name: String,
        override val _links: Map<String, HalLink>
) : SubHal(_links = _links)

data class IssueOutputModelHal(
        val creator: String,
        val name: String,
        val description: String,
        val created: Date,
        val closed: Date?,
        val state: String?,
        override val _links: Map<String, HalLink>?
) : Hal(_links = _links)

data class IssueOutputModelSiren(
        override val properties: Map<String, Any?>,
        override val links: List<SirenLink>,
        override val actions: List<SirenAction>
) : Siren(`class` = listOf(Hateos.Classes.ISSUE),
        properties = properties,
        links = links,
        actions = actions
)

fun Issue.toOutputModel() = IssueOutputModel(id, project, creator, name, description, created, closed, state)