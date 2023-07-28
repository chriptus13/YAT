package pt.isel.daw.daw_g06.controller.representation.output

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.http.HttpMethod
import pt.isel.daw.daw_g06.configs.Hateos
import pt.isel.daw.daw_g06.configs.Uri
import pt.isel.daw.daw_g06.controller.representation.output.hateoas.*
import pt.isel.daw.daw_g06.controller.util.HateoasMediaType
import pt.isel.daw.daw_g06.model.Label

data class LabelOutputModel(
        val name: String
)

data class ProjectLabelsOutputModel(
        @JsonIgnore val project: Int,
        val count: Int,
        val labels: List<LabelOutputModel>
) : OutputModel() {
    override fun toSirenOutputModel() =
            LabelsOutputModelSiren(
                    mapOf("count" to labels.size),
                    listOf(
                            SirenLink(
                                    listOf(Hateos.Relations.SELF),
                                    Uri.forProjectLabels(project).toString(),
                                    "All Labels from Project $project"
                            ),
                            SirenLink(
                                    listOf(Hateos.Relations.PROJECT),
                                    Uri.forProject(project).toString(),
                                    "Project $project"
                            )
                    ),
                    listOf(
                            SirenAction(
                                    Hateos.Actions.ADD_PROJECT_LABEL,
                                    "Add Project Label",
                                    HttpMethod.PATCH.name,
                                    Uri.forProjectLabels(project).toString(),
                                    HateoasMediaType.APPLICATION_JSON_PATCH_VALUE,
                                    listOf(
                                            SirenField("op", "text"),
                                            SirenField("path", "text"),
                                            SirenField("value", "text")
                                    )
                            ),
                            SirenAction(
                                    Hateos.Actions.REMOVE_PROJECT_LABEL,
                                    "Remove Project Label",
                                    HttpMethod.PATCH.name,
                                    Uri.forProjectLabels(project).toString(),
                                    HateoasMediaType.APPLICATION_JSON_PATCH_VALUE,
                                    listOf(
                                            SirenField("op", "text"),
                                            SirenField("path", "text")
                                    )
                            )
                    ),
                    labels.map {
                        SirenEntity(
                                listOf(Hateos.Classes.LABEL),
                                listOf(Hateos.Relations.ITEM),
                                mapOf("name" to it.name)
                        )
                    }
            )

    override fun toHalOutputModel() =
            LabelsOutputModelHal(
                    labels.size,
                    mapOf(
                            Hateos.Relations.SELF to HalLink(Uri.forProjectLabels(project).toString()),
                            Hateos.Relations.PROJECT to HalLink(Uri.forProject(project).toString())
                    ),
                    mapOf(
                            Hateos.Relations.PROJECT_LABELS to labels.map {
                                LabelOutputModelHal(it.name, mapOf(Hateos.Relations.PROJECT to HalLink(Uri.forProject(project).toString(), "Project $project")))
                            }
                    )
            )
}

data class IssueLabelsOutputModel(
        @JsonIgnore val project: Int,
        @JsonIgnore val issue: Int,
        val count: Int,
        val labels: List<LabelOutputModel>
) : OutputModel() {
    override fun toSirenOutputModel() =
            LabelsOutputModelSiren(
                    mapOf("count" to labels.size),
                    listOf(
                            SirenLink(
                                    listOf(Hateos.Relations.SELF),
                                    Uri.forIssueLabels(project, issue).toString(),
                                    "All Labels from Issue $issue"
                            ),
                            SirenLink(
                                    listOf(Hateos.Relations.PARENT),
                                    Uri.forIssue(project, issue).toString(),
                                    "Parent Issue $issue"
                            )
                    ),
                    listOf(
                            SirenAction(
                                    Hateos.Actions.ADD_ISSUE_LABEL,
                                    "Add Issue Label",
                                    HttpMethod.PATCH.name,
                                    Uri.forIssueLabels(project, issue).toString(),
                                    HateoasMediaType.APPLICATION_JSON_PATCH_VALUE,
                                    listOf(
                                            SirenField("op", "text"),
                                            SirenField("path", "text"),
                                            SirenField("value", "text")
                                    )
                            ),
                            SirenAction(
                                    Hateos.Actions.REMOVE_ISSUE_LABEL,
                                    "Remove Issue Label",
                                    HttpMethod.PATCH.name,
                                    Uri.forIssueLabels(project, issue).toString(),
                                    HateoasMediaType.APPLICATION_JSON_PATCH_VALUE,
                                    listOf(
                                            SirenField("op", "text"),
                                            SirenField("path", "text")
                                    )
                            )
                    ),
                    labels.map {
                        SirenEntity(
                                listOf(Hateos.Classes.LABEL),
                                listOf(Hateos.Relations.ITEM),
                                mapOf("name" to it.name)
                        )
                    }
            )

    override fun toHalOutputModel() =
            LabelsOutputModelHal(
                    labels.size,
                    mapOf(
                            Hateos.Relations.SELF to HalLink(Uri.forIssueLabels(project, issue).toString()),
                            Hateos.Relations.PROJECT to HalLink(Uri.forIssue(project, issue).toString())
                    ),
                    mapOf(
                            "labels" to labels.map {
                                LabelOutputModelHal(it.name, mapOf(Hateos.Relations.ISSUE to HalLink(Uri.forIssue(project, issue).toString(), "Project $project Issue $issue")))
                            }
                    )
            )
}

data class LabelOutputModelHal(
        val name: String,
        override val _links: Map<String, HalLink>
) : SubHal(_links = _links)

data class LabelsOutputModelHal(
        val count: Int,
        override val _links: Map<String, HalLink>,
        override val _embedded: Map<String, List<SubHal>>
) : Hal(_links = _links,
        _embedded = _embedded
)

data class LabelsOutputModelSiren(
        override val properties: Map<String, Any?>,
        override val links: List<SirenLink>,
        override val actions: List<SirenAction>,
        override val entities: List<SirenEntity>
) : Siren(`class` = listOf(Hateos.Classes.LABELS),
        properties = properties,
        links = links,
        actions = actions,
        entities = entities
)

fun Label.toOutputModel() = LabelOutputModel(name)