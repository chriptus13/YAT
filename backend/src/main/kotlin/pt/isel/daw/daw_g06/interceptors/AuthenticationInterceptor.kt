package pt.isel.daw.daw_g06.interceptors

import org.apache.tomcat.util.codec.binary.Base64
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import pt.isel.daw.daw_g06.model.User
import pt.isel.daw.daw_g06.service.contract.IUserService
import pt.isel.daw.daw_g06.service.exceptions.AuthenticationException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthenticationInterceptor(private val userService: IUserService) : HandlerInterceptor {

    private val headerAuthorizationRegex = """Basic (.+)""".toRegex()
    private val basicAuthorizationRegex = """(\w+):([^:\s]+)""".toRegex()

    override fun preHandle(
            request: HttpServletRequest,
            response: HttpServletResponse,
            handler: Any): Boolean {
        if (request.method != HttpMethod.OPTIONS.name && request.requestURI.startsWith("/projects")) {
            val auth = request.getHeader(HttpHeaders.AUTHORIZATION)
                    ?: throw AuthenticationException("Authorization header required.")
            if (headerAuthorizationRegex.matches(auth)) {
                val (base64) = headerAuthorizationRegex.find(auth)!!.destructured
                val login = String(Base64.decodeBase64(base64))
                if (basicAuthorizationRegex.matches(login)) {
                    val (username, password) = basicAuthorizationRegex.find(login)!!.destructured
                    val userId = userService.authenticate(User(username = username, password = password))
                    request.setAttribute("uid", userId)
                    return true
                }
            }
            throw AuthenticationException("Invalid Authorization header.")
        }
        return true
    }
}

