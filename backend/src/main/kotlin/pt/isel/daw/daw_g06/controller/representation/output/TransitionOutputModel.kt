package pt.isel.daw.daw_g06.controller.representation.output

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.http.HttpMethod
import pt.isel.daw.daw_g06.configs.Hateos
import pt.isel.daw.daw_g06.configs.Uri
import pt.isel.daw.daw_g06.controller.representation.output.hateoas.*
import pt.isel.daw.daw_g06.model.Transition

data class TransitionOutputModel(
        val id: Int,
        @JsonIgnore
        val project: Int,
        val startState: String,
        val endState: String
) : OutputModel() {
    override fun toHalOutputModel() = TransitionOutputModelHal(
            startState, endState,
            mapOf(
                    Hateos.Relations.SELF to HalLink(Uri.forTransition(project, id).toString()),
                    Hateos.Relations.PROJECT to HalLink(Uri.forProject(project).toString())
            )
    )

    override fun toSirenOutputModel() = TransitionOutputModelSiren(
            mapOf(
                    "id" to id,
                    "startState" to startState,
                    "endState" to endState
            ),
            listOf(
                    SirenLink(
                            listOf(Hateos.Relations.SELF),
                            Uri.forTransition(project, id).toString(),
                            "Transition $id"
                    ),
                    SirenLink(
                            listOf(Hateos.Relations.PROJECT),
                            Uri.forProject(project).toString(),
                            "Parent Project $project"
                    )
            ),
            listOf(
                    SirenAction(
                            Hateos.Actions.DELETE_TRANSITION,
                            "Delete transition",
                            HttpMethod.DELETE.name,
                            Uri.forTransition(project, id).toString()
                    )
            )
    )
}

data class ShortTransitionOutputModelHal(
        val startState: String,
        val endState: String,
        override val _links: Map<String, HalLink>?
) : SubHal(_links = _links)

data class TransitionOutputModelHal(
        val startState: String,
        val endState: String,
        override val _links: Map<String, HalLink>?
) : Hal(_links = _links)

data class TransitionOutputModelSiren(
        override val properties: Map<String, Any?>,
        override val links: List<SirenLink>,
        override val actions: List<SirenAction>
) : Siren(`class` = listOf(Hateos.Classes.TRANSITION),
        properties = properties,
        links = links,
        actions = actions,
        entities = listOf()
)

fun Transition.toOutputModel() = TransitionOutputModel(id, project, startState, endState)