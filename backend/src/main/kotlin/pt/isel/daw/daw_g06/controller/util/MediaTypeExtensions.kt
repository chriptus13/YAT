package pt.isel.daw.daw_g06.controller.util

import org.springframework.http.MediaType

object HateoasMediaType {

    // JSON Patch
    val APPLICATION_JSON_PATCH = MediaType("application", "json-patch+json")
    const val APPLICATION_JSON_PATCH_VALUE = "application/json-patch+json"

    // HAL
    val APPLICATION_JSON_HAL = MediaType("application", "hal+json")
    const val APPLICATION_JSON_HAL_VALUE = "application/hal+json"

    // Siren

    val APPLICATION_JSON_SIREN = MediaType("application", "vnd.siren+json")
    const val APPLICATION_JSON_SIREN_VALUE = "application/vnd.siren+json"

    // Json Home

    val APPLICATION_JSON_HOME = MediaType("application", "json-home")
    const val APPLICATION_JSON_HOME_VALUE = "application/json-home"
}