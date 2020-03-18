package org.springframework.samples.petclinic;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class HelloWorld {
    private static final Logger logger = LoggerFactory.getLogger(HelloWorld.class);

    public static void main(String[] args) throws Exception {
        logger.debug("It is starting the application");
        final Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8079);
        server.setConnectors(new ServerConnector[]{connector});
        server.setHandler(new JettyHandler());
        server.start();
        logger.debug("The application is ready");
    }
}
