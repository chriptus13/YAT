package pt.isel.daw.daw_g06.controller.representation.input.hateoas

/**
 * JSON Patch as described in <a href="https://tools.ietf.org/html/rfc6902">JSON Patch</a>
 */
data class JsonPatchEntry(
        val op: String,
        val path: String,
        val value: String?
)

enum class JsonPatchOperation(val op: String) {
    ADD("add"), REMOVE("remove")
}