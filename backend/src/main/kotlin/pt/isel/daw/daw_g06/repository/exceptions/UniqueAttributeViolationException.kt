package pt.isel.daw.daw_g06.repository.exceptions

class UniqueAttributeViolationException(detail: String) : RepositoryException("Unique attribute violation error.", detail)