package pt.isel.daw.daw_g06.controller.exceptions

import pt.isel.daw.daw_g06.model.exceptions.ModelException

abstract class ControllerException(title: String, detail: String) : ModelException(title, detail)