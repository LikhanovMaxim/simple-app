package org.springframework.samples.petclinic

import mu.KotlinLogging
import org.eclipse.jetty.server.Request
import org.eclipse.jetty.server.handler.AbstractHandler
import java.io.OutputStreamWriter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.text.Charsets.UTF_8

private val logger = KotlinLogging.logger {}

class JettyHandler : AbstractHandler() {
    override fun handle(target: String?, baseRequest: Request, request: HttpServletRequest, response: HttpServletResponse) {
        logger.debug { "Handle a request with the target=$target" }
        baseRequest.isHandled = true
        response.contentType = "text/plain"
        val content = "Target $target"
        response.setContentLength(content.length)
        val outputStream = response.outputStream
        val writer = OutputStreamWriter(outputStream, UTF_8)
        writer.write(content)
        writer.flush()
    }

}
