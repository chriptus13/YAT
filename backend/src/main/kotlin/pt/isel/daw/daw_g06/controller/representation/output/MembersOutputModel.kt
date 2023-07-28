package pt.isel.daw.daw_g06.controller.representation.output

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.http.HttpMethod
import pt.isel.daw.daw_g06.configs.Hateos
import pt.isel.daw.daw_g06.configs.Uri
import pt.isel.daw.daw_g06.controller.representation.output.hateoas.*
import pt.isel.daw.daw_g06.controller.util.HateoasMediaType
import pt.isel.daw.daw_g06.model.Member

data class MemberOutputModel(
        val username: String
)

data class MembersOutputModel(
        @JsonIgnore val project: Int,
        val count: Int,
        val members: List<MemberOutputModel>
) : OutputModel() {
    override fun toSirenOutputModel() =
            MembersOutputModelSiren(
                    mapOf("count" to members.size),
                    listOf(
                            SirenLink(
                                    listOf(Hateos.Relations.SELF),
                                    Uri.forProjectMembers(project).toString(),
                                    "All Members from Project $project"
                            ),
                            SirenLink(
                                    listOf(Hateos.Relations.PROJECT),
                                    Uri.forProject(project).toString(),
                                    "Project $project"
                            )
                    ),
                    listOf(
                            SirenAction(
                                    Hateos.Actions.ADD_PROJECT_MEMBER,
                                    "Add Member",
                                    HttpMethod.PATCH.name,
                                    Uri.forProjectMembers(project).toString(),
                                    HateoasMediaType.APPLICATION_JSON_PATCH_VALUE,
                                    listOf(
                                            SirenField("op", "text"),
                                            SirenField("path", "text"),
                                            SirenField("value", "text")
                                    )
                            ),
                            SirenAction(
                                    Hateos.Actions.REMOVE_PROJECT_MEMBER,
                                    "Remove Member",
                                    HttpMethod.PATCH.name,
                                    Uri.forProjectMembers(project).toString(),
                                    HateoasMediaType.APPLICATION_JSON_PATCH_VALUE,
                                    listOf(
                                            SirenField("op", "text"),
                                            SirenField("path", "text")
                                    )
                            )
                    ),
                    members.map {
                        SirenEntity(
                                listOf(Hateos.Classes.MEMBER),
                                listOf("item"),
                                mapOf("username" to it.username)
                        )
                    }
            )

    override fun toHalOutputModel() =
            MembersOutputModelHal(
                    members.size,
                    mapOf(
                            Hateos.Relations.SELF to HalLink(Uri.forProjectMembers(project).toString()),
                            Hateos.Relations.PROJECT to HalLink(Uri.forProject(project).toString())
                    ),
                    mapOf(
                            Hateos.Relations.MEMBERS to members.map {
                                MemberOutputModelHal(it.username, mapOf(
                                        Hateos.Relations.PROJECT to HalLink(Uri.forProject(project).toString(), "Project $project")
                                ))
                            }
                    )
            )
}

data class MemberOutputModelHal(
        val username: String,
        override val _links: Map<String, HalLink>
) : SubHal(_links = _links)

data class MembersOutputModelHal(
        val count: Int,
        override val _links: Map<String, HalLink>,
        override val _embedded: Map<String, List<SubHal>>
) : Hal(_links = _links,
        _embedded = _embedded
)

data class MembersOutputModelSiren(
        override val properties: Map<String, Any?>,
        override val links: List<SirenLink>,
        override val actions: List<SirenAction>,
        override val entities: List<SirenEntity>
) : Siren(`class` = listOf(Hateos.Classes.MEMBERS),
        properties = properties,
        links = links,
        actions = actions,
        entities = entities
)

fun Member.toOutputModel() = MemberOutputModel(username)