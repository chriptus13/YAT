package pt.isel.daw.daw_g06.controller.representation.output

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.http.HttpMethod
import pt.isel.daw.daw_g06.configs.Hateos
import pt.isel.daw.daw_g06.configs.Uri
import pt.isel.daw.daw_g06.controller.representation.output.hateoas.*
import pt.isel.daw.daw_g06.controller.util.HateoasMediaType
import pt.isel.daw.daw_g06.model.State

data class StatesOutputModel(
        @JsonIgnore
        val project: Int,
        val count: Int,
        val states: List<StateOutputModel>
) : OutputModel() {
    override fun toHalOutputModel() = StatesOutputModelHal(
            states.size,
            mapOf(
                    Hateos.Relations.SELF to HalLink(Uri.forProjectStates(project).toString()),
                    Hateos.Relations.PROJECT to HalLink(Uri.forProject(project).toString())
            ),
            mapOf(
                    Hateos.Relations.STATES to states.map {
                        StateOutputModelHal(it.name)
                    }
            )
    )

    override fun toSirenOutputModel() = StatesOutputModelSiren(
            mapOf("count" to states.size),
            listOf(
                    SirenLink(
                            listOf(Hateos.Relations.SELF),
                            Uri.forProjectStates(project).toString(),
                            "All States from Project $project"
                    ),
                    SirenLink(
                            listOf(Hateos.Relations.PROJECT),
                            Uri.forProject(project).toString(),
                            "Project $project"
                    )
            ),
            listOf(
                    SirenAction(
                            Hateos.Actions.ADD_PROJECT_STATE,
                            "Add State",
                            HttpMethod.PATCH.name,
                            Uri.forProjectStates(project).toString(),
                            HateoasMediaType.APPLICATION_JSON_PATCH_VALUE,
                            listOf(
                                    SirenField("op", "text"),
                                    SirenField("path", "text"),
                                    SirenField("value", "text")
                            )
                    ),
                    SirenAction(
                            Hateos.Actions.REMOVE_PROJECT_STATE,
                            "Remove State",
                            HttpMethod.PATCH.name,
                            Uri.forProjectStates(project).toString(),
                            HateoasMediaType.APPLICATION_JSON_PATCH_VALUE,
                            listOf(
                                    SirenField("op", "text"),
                                    SirenField("path", "text")
                            )
                    )
            ),
            states.map {
                SirenEntity(
                        listOf(Hateos.Classes.STATE),
                        listOf(Hateos.Relations.ITEM),
                        mapOf("name" to it.name)
                )
            }
    )
}

data class StateOutputModel(
        val project: Int,
        val name: String
)

data class StateOutputModelHal(
        val name: String
) : SubHal()

data class StatesOutputModelHal(
        val count: Int,
        override val _links: Map<String, HalLink>?,
        override val _embedded: Map<String, List<SubHal>>?
) : Hal(_links = _links,
        _embedded = _embedded
)

data class StatesOutputModelSiren(
        override val properties: Map<String, Any?>,
        override val links: List<SirenLink>,
        override val actions: List<SirenAction>,
        override val entities: List<SirenEntity>
) : Siren(`class` = listOf(Hateos.Classes.STATES),
        properties = properties,
        links = links,
        actions = actions,
        entities = entities
)

fun State.toOutputModel() = StateOutputModel(project, name)