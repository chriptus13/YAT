package pt.isel.daw.daw_g06.service.exceptions

import pt.isel.daw.daw_g06.model.exceptions.ModelException

abstract class ServiceException(title: String, detail: String) : ModelException(title, detail)