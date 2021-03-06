package com.betassistant.resource;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;

/**
 * @author zenind
 */
public class BetResourceConfig extends ResourceConfig {

    public BetResourceConfig() {
        register(CompetitionResource.class);
        register(JacksonJaxbJsonProvider.class, MessageBodyReader.class, MessageBodyWriter.class);

        packages("com.betassistant.resource");
    }
}
