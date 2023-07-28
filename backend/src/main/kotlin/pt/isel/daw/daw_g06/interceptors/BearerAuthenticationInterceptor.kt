package pt.isel.daw.daw_g06.interceptors

import khttp.post
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import pt.isel.daw.daw_g06.configs.Auth
import pt.isel.daw.daw_g06.service.contract.IUserService
import pt.isel.daw.daw_g06.service.exceptions.AuthenticationException
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class BearerAuthenticationInterceptor(private val userService: IUserService) : HandlerInterceptor {

    private val headerAuthorizationRegex = """Bearer (.+)""".toRegex()

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (request.method != HttpMethod.OPTIONS.name && request.requestURI.startsWith("/projects")) {
            val auth = request.getHeader(HttpHeaders.AUTHORIZATION)
                    ?: throw AuthenticationException("Authorization header required.")
            if (headerAuthorizationRegex.matches(auth)) {
                val (accessToken) = headerAuthorizationRegex.find(auth)!!.destructured
                val resp = post(url = Auth.TOKEN_INTROSPECTION_ENDPOINT,
                        headers = mapOf(
                                "Authorization" to "Basic " + Base64.getEncoder().encodeToString("${Auth.CLIENT_ID}:${Auth.CLIENT_SECRET}".toByteArray()),
                                "Content-Type" to "application/x-www-form-urlencoded"
                        ),
                        data = "token=$accessToken"
                )
                if(resp.statusCode != 200) throw AuthenticationException("Invalid Authorization header.")
                val respBody = resp.jsonObject
                if(!respBody["active"].toString().toBoolean()) throw AuthenticationException("Invalid Authorization header.")
                request.setAttribute("uid", userService.getUser(respBody["sub"].toString()).id)
                return true
            }
            throw AuthenticationException("Invalid Authorization header.")
        }
        return true
    }
}