package pt.isel.daw.daw_g06.controller.util

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import pt.isel.daw.daw_g06.controller.representation.output.hateoas.ProblemJson

fun ResponseEntity.BodyBuilder.of(problemJson: ProblemJson) =
        this.contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(problemJson)