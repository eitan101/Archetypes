#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.*;

public class MyApp extends Application<Configuration> {
    
    public static void main(String[] args) throws Exception {
        new MyApp().run(args);
    }

    @Override
    public void initialize(Bootstrap<Configuration> bootstrap) {
    }

    @Override
    public void run(Configuration configuration, Environment environment) throws InvalidKeySpecException, NoSuchAlgorithmException, InterruptedException, ExecutionException {
        environment.jersey().register(new RestResource());
    }

    @Path("/")
    @Timed
    public static class RestResource {
        @GET
        @Produces(MediaType.APPLICATION_JSON)
        @Path("/hello")
        public Object get() {            
            return Map.of(
                "Hello", 1,
                "World", 2,
                "ABC", List.of("A", "B", "C"));
        }
    }

}
