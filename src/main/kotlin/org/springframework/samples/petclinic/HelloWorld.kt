package org.springframework.samples.petclinic

import mu.KotlinLogging
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.server.ServerConnector
import org.eclipse.jetty.server.handler.HandlerCollection

private val logger = KotlinLogging.logger {}
@Throws(Exception::class)
fun main(args: Array<String>) {
    logger.debug { "It is starting the application" }
    val server = Server()
    val connector = ServerConnector(server)
    connector.port = 8078
    server.connectors = listOf(connector).toTypedArray()
    server.handler = JettyHandler()
    server.start()
    logger.debug { "The application is ready" }
}
