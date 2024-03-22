package htt.esportsfantasybe;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * ServletInitializer class.
 * @author Alberto Plaza Montes
 */
public class ServletInitializer extends SpringBootServletInitializer {

    /**
     * Servlet Initializer method.
     * @param application SpringApplicationBuilder SpringApplication.
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(EsportsfantasybeApplication.class);
    }

}
