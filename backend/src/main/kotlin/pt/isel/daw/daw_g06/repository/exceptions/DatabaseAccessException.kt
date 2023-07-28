package pt.isel.daw.daw_g06.repository.exceptions

class DatabaseAccessException(detail: String) : RepositoryException("Database access error.", detail)