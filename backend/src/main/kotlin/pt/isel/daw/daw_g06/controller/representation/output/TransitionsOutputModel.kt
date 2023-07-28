package pt.isel.daw.daw_g06.controller.representation.output

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import pt.isel.daw.daw_g06.configs.Hateos
import pt.isel.daw.daw_g06.configs.Uri
import pt.isel.daw.daw_g06.controller.representation.output.hateoas.*

data class TransitionsOutputModel(
        @JsonIgnore
        val project: Int,
        val count: Int,
        val transitions: List<TransitionOutputModel>
) : OutputModel() {
    override fun toHalOutputModel() = TransitionsOutputModelHal(
            count,
            mapOf(
                    Hateos.Relations.SELF to HalLink(Uri.forProjectTransitions(project).toString()),
                    Hateos.Relations.PROJECT to HalLink(Uri.forProject(project).toString())
            ),
            mapOf<String, List<SubHal>>(
                    Hateos.Relations.TRANSITIONS to transitions.map {
                        ShortTransitionOutputModelHal(it.startState, it.endState, mapOf(
                                Hateos.Relations.SELF to HalLink(Uri.forTransition(project, it.id).toString()))
                        )
                    }
            )
    )

    override fun toSirenOutputModel() = TransitionsOutputModelSiren(
            mapOf("count" to transitions.size),
            listOf(
                    SirenLink(
                            listOf(Hateos.Relations.SELF),
                            Uri.forProjectTransitions(project).toString(),
                            "All Transitions from Project $project"
                    ),
                    SirenLink(
                            listOf(Hateos.Relations.PROJECT),
                            Uri.forProject(project).toString(),
                            "Project $project"
                    )
            ),
            listOf(
                    SirenAction(
                            Hateos.Actions.CREATE_TRANSITION,
                            "Create transition",
                            HttpMethod.POST.name,
                            Uri.forProjectTransitions(project).toString(),
                            MediaType.APPLICATION_JSON_VALUE,
                            listOf(
                                    SirenField("startState", "text"),
                                    SirenField("endState", "text")
                            )
                    )
            ),
            transitions.map {
                SirenEntity(
                        listOf(Hateos.Classes.TRANSITION),
                        listOf(Hateos.Relations.ITEM),
                        mapOf(
                                "startState" to it.startState,
                                "endState" to it.endState
                        ),
                        listOf(SirenLink(
                                listOf(Hateos.Relations.SELF),
                                Uri.forTransition(project, it.id).toString(),
                                "Transition ${it.id}"
                        ))
                )
            }

    )
}

data class TransitionsOutputModelHal(
        val count: Int,
        override val _links: Map<String, HalLink>?,
        override val _embedded: Map<String, List<SubHal>>?
) : Hal(_links = _links,
        _embedded = _embedded
)

data class TransitionsOutputModelSiren(
        override val properties: Map<String, Any?>,
        override val links: List<SirenLink>,
        override val actions: List<SirenAction>,
        override val entities: List<SirenEntity>
) : Siren(`class` = listOf(Hateos.Classes.TRANSITIONS),
        properties = properties,
        links = links,
        actions = actions,
        entities = entities
)