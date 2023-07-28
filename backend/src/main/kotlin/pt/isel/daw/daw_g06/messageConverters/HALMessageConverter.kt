package pt.isel.daw.daw_g06.messageConverters

import org.springframework.http.HttpOutputMessage
import org.springframework.http.MediaType
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import pt.isel.daw.daw_g06.controller.representation.output.OutputModel
import pt.isel.daw.daw_g06.controller.util.HateoasMediaType
import java.lang.reflect.Type

class HALMessageConverter : MappingJackson2HttpMessageConverter() {
    override fun writeInternal(`object`: Any, type: Type?, outputMessage: HttpOutputMessage) {
        super.writeInternal((`object` as OutputModel).toHalOutputModel(), type, outputMessage)
    }

    override fun canWrite(mediaType: MediaType?) =
            mediaType?.isCompatibleWith(HateoasMediaType.APPLICATION_JSON_HAL) ?: false

}