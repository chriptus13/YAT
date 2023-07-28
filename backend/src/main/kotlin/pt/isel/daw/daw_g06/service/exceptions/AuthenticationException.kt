package pt.isel.daw.daw_g06.service.exceptions

class AuthenticationException(detail: String) : ServiceException("Authentication error.", detail)