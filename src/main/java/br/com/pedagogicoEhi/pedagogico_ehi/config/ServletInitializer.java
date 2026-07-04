package br.com.pedagogicoEhi.pedagogico_ehi.config;

import br.com.pedagogicoEhi.pedagogico_ehi.PedagogicoEhiApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(PedagogicoEhiApplication.class);
    }
}
