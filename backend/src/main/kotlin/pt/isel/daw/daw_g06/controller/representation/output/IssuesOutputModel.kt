package pt.isel.daw.daw_g06.controller.representation.output

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import pt.isel.daw.daw_g06.configs.Hateos
import pt.isel.daw.daw_g06.configs.Uri
import pt.isel.daw.daw_g06.controller.representation.output.hateoas.*

data class IssuesOutputModel(
        @JsonIgnore
        val project: Int,
        val count: Int,
        val issues: List<IssueOutputModel>
) : OutputModel() {
    override fun toHalOutputModel() = IssuesOutputModelHal(
            issues.size,
            mapOf(
                    Hateos.Relations.SELF to HalLink(Uri.forProjectIssues(project).toString()),
                    Hateos.Relations.PROJECT to HalLink(Uri.forProject(project).toString())
            ),
            mapOf<String, List<SubHal>>(
                    Hateos.Relations.ISSUES to issues.map {
                        ShortIssueOutputModelHal(it.name, mapOf(
                                Hateos.Relations.SELF to HalLink(Uri.forIssue(project, it.id).toString())
                        ))
                    }
            )
    )

    override fun toSirenOutputModel() = IssuesOutputModelSiren(
            mapOf("count" to issues.size),
            listOf(
                    SirenLink(listOf(Hateos.Relations.SELF), Uri.forProjectIssues(project).toString(), "All Issues from Project $project"),
                    SirenLink(listOf(Hateos.Relations.PROJECT), Uri.forProject(project).toString(), "Parent Projects $project")
            ),
            listOf(
                    SirenAction(
                            Hateos.Actions.CREATE_ISSUE,
                            "Create issue",
                            HttpMethod.POST.name,
                            Uri.forProjectIssues(project).toString(),
                            MediaType.APPLICATION_JSON_VALUE,
                            listOf(
                                    SirenField("name", "text"),
                                    SirenField("description", "text")
                            )
                    )
            ),
            issues.map {
                SirenEntity(
                        listOf(Hateos.Classes.ISSUE, Hateos.Classes.SHORT),
                        listOf(Hateos.Relations.ITEM),
                        mapOf("name" to it.name),
                        listOf(SirenLink(
                                listOf(Hateos.Relations.SELF),
                                Uri.forIssue(project, it.id).toString(),
                                "Issue ${it.id}"
                        ))
                )
            }
    )
}

data class IssuesOutputModelHal(
        val count: Int,
        override val _links: Map<String, HalLink>,
        override val _embedded: Map<String, List<SubHal>>?
) : Hal(_links = _links,
        _embedded = _embedded
)

data class IssuesOutputModelSiren(
        override val properties: Map<String, Any?>,
        override val links: List<SirenLink>,
        override val actions: List<SirenAction>,
        override val entities: List<SirenEntity>
) : Siren(`class` = listOf(Hateos.Classes.ISSUES),
        properties = properties,
        links = links,
        actions = actions,
        entities = entities
)