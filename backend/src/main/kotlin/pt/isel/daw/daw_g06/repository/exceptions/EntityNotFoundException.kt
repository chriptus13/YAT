package pt.isel.daw.daw_g06.repository.exceptions

class EntityNotFoundException(detail: String) : RepositoryException("Entity not found.", detail)