package pt.isel.daw.daw_g06.controller.representation.output.hateoas

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonPropertyOrder

/**
 * Siren+JSON as described in <a href="https://github.com/kevinswiber/siren">Siren</a>
 */
@JsonPropertyOrder("class", "properties", "links", "actions", "entities")
@JsonInclude(JsonInclude.Include.NON_NULL)
abstract class Siren(
        val `class`: List<String> = listOf(),
        open val properties: Map<String, Any?>,
        open val links: List<SirenLink> = listOf(),
        open val actions: List<SirenAction> = listOf(),
        open val entities: List<SirenEntity> = listOf()
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class SirenLink(
        val rel: List<String>,
        val href: String,
        val title: String?
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class SirenAction(
        val name: String,
        val title: String?,
        val method: String,
        val href: String,
        val type: String? = null,
        val fields: List<SirenField>? = null
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class SirenField(
        val name: String,
        val type: String,
        val value: Any? = null,
        val title: String? = null
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class SirenEntity(
        val `class`: List<String>,
        val rel: List<String>,
        val properties: Map<String, Any?>,
        val links: List<SirenLink>? = null
)