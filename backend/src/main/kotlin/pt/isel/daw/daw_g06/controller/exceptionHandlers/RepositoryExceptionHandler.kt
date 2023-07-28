package pt.isel.daw.daw_g06.controller.exceptionHandlers

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import pt.isel.daw.daw_g06.controller.representation.output.hateoas.ProblemJson
import pt.isel.daw.daw_g06.controller.util.of
import pt.isel.daw.daw_g06.repository.exceptions.*

@RestControllerAdvice
class RepositoryExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(UniqueAttributeViolationException::class)
    @ResponseBody
    fun handleUniqueAttributeViolationException(ex: UniqueAttributeViolationException, request: WebRequest) =
            ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .of(ProblemJson("UniqueAttributeViolationException", ex.title, ex.detail, null))

    @ExceptionHandler(InvalidTransitionException::class)
    @ResponseBody
    fun handleInvalidTransitionException(ex: InvalidTransitionException, request: WebRequest) =
            ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .of(ProblemJson("InvalidTransitionException", ex.title, ex.detail, null))

    @ExceptionHandler(EntityNotFoundException::class)
    @ResponseBody
    fun handleEntityNotFoundException(ex: EntityNotFoundException, request: WebRequest) =
            ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .of(ProblemJson("EntityNotFoundException", ex.title, ex.detail, null))

    @ExceptionHandler(InvalidOperationException::class)
    @ResponseBody
    fun handleInvalidOperationException(ex: InvalidOperationException, request: WebRequest) =
            ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .of(ProblemJson("InvalidOperationException", ex.title, ex.detail, null))

    @ExceptionHandler(ArchivedCommentException::class)
    @ResponseBody
    fun handleArchivedCommentException(ex: ArchivedCommentException, request: WebRequest) =
            ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .of(ProblemJson("ArchivedCommentException", ex.title, ex.detail, null))

    @ExceptionHandler(DatabaseAccessException::class)
    @ResponseBody
    fun handleDatabaseAccessException(ex: DatabaseAccessException, request: WebRequest) =
            ResponseEntity
                    .status(HttpStatus.SERVICE_UNAVAILABLE)
                    .of(ProblemJson("DatabaseAccessException", ex.title, ex.detail, null))
}