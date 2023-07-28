package pt.isel.daw.daw_g06.service.exceptions

class AuthorizationException(detail: String) : ServiceException("Authorization error.", detail)