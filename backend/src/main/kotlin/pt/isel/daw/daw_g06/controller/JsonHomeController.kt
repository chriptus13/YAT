package pt.isel.daw.daw_g06.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import pt.isel.daw.daw_g06.configs.Uri
import pt.isel.daw.daw_g06.controller.util.HateoasMediaType

@Controller
class JsonHomeController {
    @RequestMapping(path = [Uri.HOME], method = [RequestMethod.GET], produces = [HateoasMediaType.APPLICATION_JSON_HOME_VALUE])
    fun getJsonHome() = "json_home.json"
}