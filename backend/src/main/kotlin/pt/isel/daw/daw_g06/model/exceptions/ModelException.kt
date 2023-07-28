package pt.isel.daw.daw_g06.model.exceptions

abstract class ModelException(val title: String, val detail: String) : RuntimeException(title)