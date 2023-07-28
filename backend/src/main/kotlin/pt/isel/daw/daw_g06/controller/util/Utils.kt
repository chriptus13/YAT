package pt.isel.daw.daw_g06.controller.util

import pt.isel.daw.daw_g06.controller.representation.input.hateoas.JsonPatchEntry
import pt.isel.daw.daw_g06.controller.representation.input.hateoas.JsonPatchOperation

fun validateJsonPatchOperations(operations: List<JsonPatchEntry>, pathRegex: Regex) =
        operations.all {
            it.op == JsonPatchOperation.ADD.op && it.path == "/" && it.value != null
                    || it.op == JsonPatchOperation.REMOVE.op && pathRegex.matches(it.path)
        }