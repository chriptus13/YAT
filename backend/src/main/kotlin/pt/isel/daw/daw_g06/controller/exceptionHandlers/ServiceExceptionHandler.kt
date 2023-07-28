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
import pt.isel.daw.daw_g06.service.exceptions.AuthenticationException
import pt.isel.daw.daw_g06.service.exceptions.AuthorizationException

@RestControllerAdvice
class ServiceExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(AuthorizationException::class)
    @ResponseBody
    fun handleAuthorizationException(ex: AuthorizationException, request: WebRequest) =
            ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .of(ProblemJson("AuthorizationException", ex.title, ex.detail, null))

    @ExceptionHandler(AuthenticationException::class)
    @ResponseBody
    fun handleAuthenticationException(ex: AuthenticationException, request: WebRequest) =
            ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .of(ProblemJson("AuthenticationException", ex.title, ex.detail, null))

    @ExceptionHandler(Throwable::class)
    @ResponseBody
    fun handleAllExceptions(ex: Throwable, request: WebRequest) =
            ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .of(ProblemJson(title = ex.toString(), detail = ex.message, instance = null))
}