package pt.isel.daw.daw_g06.repository.exceptions

import pt.isel.daw.daw_g06.model.exceptions.ModelException

abstract class RepositoryException(title: String, detail: String) : ModelException(title, detail)
