package pt.isel.daw.daw_g06.controller.exceptions

class JsonPatchException(detail: String) : ControllerException("JSON Patch error.", detail)