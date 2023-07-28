package pt.isel.daw.daw_g06.controller.representation.output

import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import pt.isel.daw.daw_g06.configs.Hateos
import pt.isel.daw.daw_g06.configs.Uri
import pt.isel.daw.daw_g06.controller.representation.output.hateoas.*
import pt.isel.daw.daw_g06.model.Project

data class ProjectOutputModel(
        val id: Int,
        val name: String,
        val description: String?,
        val initialState: String?
) : OutputModel() {

    override fun toSirenOutputModel() =
            ProjectOutputModelSiren(
                    mapOf(
                            "name" to name,
                            "description" to description,
                            "initialState" to initialState
                    ),
                    listOf(
                            SirenLink(
                                    listOf(Hateos.Relations.SELF),
                                    Uri.forProject(id).toString(),
                                    "Project $id"
                            ),
                            SirenLink(
                                    listOf(Hateos.Relations.COLLECTION),
                                    Uri.PROJECTS,
                                    "All Projects"
                            ),
                            SirenLink(
                                    listOf(Hateos.Relations.ISSUES),
                                    Uri.forProjectIssues(id).toString(),
                                    "Project $id's Issues"
                            ),
                            SirenLink(
                                    listOf(Hateos.Relations.PROJECT_LABELS),
                                    Uri.forProjectLabels(id).toString(),
                                    "Project $id's Labels"
                            ),
                            SirenLink(
                                    listOf(Hateos.Relations.STATES),
                                    Uri.forProjectStates(id).toString(),
                                    "Project $id's States"
                            ),
                            SirenLink(
                                    listOf(Hateos.Relations.TRANSITIONS),
                                    Uri.forProjectTransitions(id).toString(),
                                    "Project $id's Transitions"
                            ),
                            SirenLink(
                                    listOf(Hateos.Relations.MEMBERS),
                                    Uri.forProjectMembers(id).toString(),
                                    "Project $id's Members"
                            )
                    ),
                    listOf(
                            SirenAction(
                                    Hateos.Actions.DELETE_PROJECT,
                                    "Delete project",
                                    HttpMethod.DELETE.name,
                                    Uri.forProject(id).toString()
                            ),
                            SirenAction(
                                    Hateos.Actions.UPDATE_PROJECT_NAME,
                                    "Update project name",
                                    HttpMethod.PATCH.name,
                                    Uri.forProjectName(id).toString(),
                                    MediaType.APPLICATION_JSON_VALUE,
                                    listOf(SirenField("name", "text"))
                            ),
                            SirenAction(
                                    Hateos.Actions.UPDATE_PROJECT_DESCRIPTION,
                                    "Update project description",
                                    HttpMethod.PATCH.name,
                                    Uri.forProjectDescription(id).toString(),
                                    MediaType.APPLICATION_JSON_VALUE,
                                    listOf(SirenField("description", "text"))
                            ),
                            SirenAction(
                                    Hateos.Actions.UPDATE_PROJECT_INITIAL_STATE,
                                    "Update project initial State",
                                    HttpMethod.PATCH.name,
                                    Uri.forProjectInitialState(id).toString(),
                                    MediaType.APPLICATION_JSON_VALUE,
                                    listOf(SirenField("name", "text"))
                            ),
                            SirenAction(
                                    Hateos.Actions.UPDATE_PROJECT,
                                    "Update project",
                                    HttpMethod.PUT.name,
                                    Uri.forProject(id).toString(),
                                    MediaType.APPLICATION_JSON_VALUE,
                                    listOf(
                                            SirenField("name", "text"),
                                            SirenField("description", "text"),
                                            SirenField("initialState", "text")
                                    )
                            )
                    )
            )

    override fun toHalOutputModel() =
            ProjectOutputModelHal(
                    name,
                    description,
                    initialState,
                    mapOf(
                            Hateos.Relations.SELF to HalLink(Uri.forProject(id).toString()),
                            Hateos.Relations.COLLECTION to HalLink(Uri.PROJECTS),
                            Hateos.Relations.ISSUES to HalLink(Uri.forProjectIssues(id).toString()),
                            Hateos.Relations.PROJECT_LABELS to HalLink(Uri.forProjectLabels(id).toString()),
                            Hateos.Relations.STATES to HalLink(Uri.forProjectStates(id).toString()),
                            Hateos.Relations.TRANSITIONS to HalLink(Uri.forProjectTransitions(id).toString()),
                            Hateos.Relations.MEMBERS to HalLink(Uri.forProjectMembers(id).toString())
                    )
            )
}

data class ShortProjectOutputModelHal(
        val name: String,
        override val _links: Map<String, HalLink>
) : SubHal(_links = _links)

data class ProjectOutputModelHal(
        val name: String,
        val description: String?,
        val initialState: String?,
        override val _links: Map<String, HalLink>
) : Hal(_links = _links)

data class ProjectOutputModelSiren(
        override val properties: Map<String, Any?>,
        override val links: List<SirenLink>,
        override val actions: List<SirenAction>
) : Siren(`class` = listOf(Hateos.Classes.PROJECT),
        properties = properties,
        links = links,
        actions = actions
)

fun Project.toOutputModel() = ProjectOutputModel(id, name, description, initialState)