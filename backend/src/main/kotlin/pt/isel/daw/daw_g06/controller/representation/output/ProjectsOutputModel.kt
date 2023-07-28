package pt.isel.daw.daw_g06.controller.representation.output

import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import pt.isel.daw.daw_g06.configs.Hateos
import pt.isel.daw.daw_g06.configs.Uri
import pt.isel.daw.daw_g06.controller.representation.output.hateoas.*

data class ProjectsOutputModel(
        val count: Int,
        val projects: List<ProjectOutputModel>
) : OutputModel() {

    override fun toSirenOutputModel() =
            ProjectsOutputModelSiren(
                    mapOf("count" to projects.size),
                    listOf(
                            SirenLink(
                                    listOf(Hateos.Relations.SELF),
                                    Uri.PROJECTS,
                                    "All Projects"
                            )
                    ),
                    listOf(
                            SirenAction(
                                    Hateos.Actions.CREATE_PROJECT,
                                    "Create project",
                                    HttpMethod.POST.name,
                                    Uri.PROJECTS,
                                    MediaType.APPLICATION_JSON_VALUE,
                                    listOf(
                                            SirenField("name", "text"),
                                            SirenField("description", "text"),
                                            SirenField("initialState", "text")
                                    )
                            )
                    ),
                    projects.map {
                        SirenEntity(
                                listOf(Hateos.Classes.PROJECT, Hateos.Classes.SHORT),
                                listOf("item"),
                                mapOf("name" to it.name),
                                listOf(SirenLink(
                                        listOf(Hateos.Relations.SELF),
                                        Uri.forProject(it.id).toString(),
                                        "Project ${it.id}"
                                ))
                        )
                    }
            )

    override fun toHalOutputModel() =
            ProjectsOutputModelHal(
                    projects.size,
                    mapOf(Hateos.Relations.SELF to HalLink(Uri.PROJECTS)),
                    mapOf(
                            Hateos.Relations.PROJECTS to projects.map {
                                ShortProjectOutputModelHal(it.name, mapOf(
                                        Hateos.Relations.SELF to HalLink(Uri.forProject(it.id).toString())
                                ))
                            }
                    )
            )
}

data class ProjectsOutputModelHal(
        val count: Int,
        override val _links: Map<String, HalLink>,
        override val _embedded: Map<String, List<SubHal>>
) : Hal(_links = _links,
        _embedded = _embedded
)

data class ProjectsOutputModelSiren(
        override val properties: Map<String, Any?>,
        override val links: List<SirenLink>,
        override val actions: List<SirenAction>,
        override val entities: List<SirenEntity>
) : Siren(`class` = listOf(Hateos.Classes.PROJECTS),
        properties = properties,
        links = links,
        actions = actions,
        entities = entities
)