package pt.isel.daw.daw_g06.controller.representation.output.hateoas

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonPropertyOrder

/**
 * Abstract class to be used as a base class for HAL representations as described in
 * <a href="https://tools.ietf.org/html/draft-kelly-json-hal-08">
 * JSON Hypertext Application Language</a>
 */
@JsonPropertyOrder("_links", "_embedded")
@JsonInclude(JsonInclude.Include.NON_NULL)
abstract class Hal(
        open val _links: Map<String, HalLink>? = null,
        open val _embedded: Map<String, List<SubHal>>? = null
)

@JsonInclude(JsonInclude.Include.NON_NULL)
abstract class SubHal(
        open val _links: Map<String, Any>? = null
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class HalLink(
        val href: String,
        val name: String? = null,
        val templated: Boolean? = null,
        val type: String? = null,
        val title: String? = null
)