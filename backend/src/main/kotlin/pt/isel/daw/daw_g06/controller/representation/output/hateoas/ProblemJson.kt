package pt.isel.daw.daw_g06.controller.representation.output.hateoas

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonPropertyOrder

/**
 * Class used for error models, based on the <a href="https://tools.ietf.org/html/rfc7807">Problem Json spec</a>
 */
@JsonPropertyOrder("type", "title", "detail", "instance")
@JsonInclude(JsonInclude.Include.NON_NULL)
data class ProblemJson(
        val type: String = "about:blank",
        val title: String?,
        val detail: String?,
        val instance: String?
)