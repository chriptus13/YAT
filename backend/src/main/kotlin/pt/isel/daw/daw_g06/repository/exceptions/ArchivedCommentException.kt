package pt.isel.daw.daw_g06.repository.exceptions

class ArchivedCommentException(detail: String) : RepositoryException("Cannot add comments to archived issues.", detail)