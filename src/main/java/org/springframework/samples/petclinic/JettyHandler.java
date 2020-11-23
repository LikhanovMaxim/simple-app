package org.springframework.samples.petclinic;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;

import static java.nio.charset.StandardCharsets.UTF_8;

public class JettyHandler extends AbstractHandler {
    private static final Logger logger = LoggerFactory.getLogger(JettyHandler.class);

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.debug("build 0.3.2; Handle request with target {}", target);
        baseRequest.setHandled(true);
        response.setContentType("text/plain");
        final String content = "Target " + target;
        opaTarget(target);
        response.setContentLength(content.length());
        final ServletOutputStream outputStream = response.getOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(outputStream, UTF_8);
        writer.write(content);
        writer.flush();
    }

    private void opaTarget(String target) {
        if (target.equals("/opa")) {
            logger.debug("Handle request with opa target {}. sleep...", target);
            waitMsc(200);
        }
    }

    private void waitMsc(int msc) {
        try {
            logger.debug("sleep msc {}...", msc);
            Thread.sleep(msc);
        } catch (InterruptedException e) {
            logger.error(String.valueOf(e));
        }
    }
}
