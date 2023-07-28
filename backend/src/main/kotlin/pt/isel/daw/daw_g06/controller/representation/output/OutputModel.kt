package pt.isel.daw.daw_g06.controller.representation.output

import pt.isel.daw.daw_g06.controller.representation.output.hateoas.Hal
import pt.isel.daw.daw_g06.controller.representation.output.hateoas.Siren

abstract class OutputModel {
    abstract fun toSirenOutputModel(): Siren
    abstract fun toHalOutputModel(): Hal
}