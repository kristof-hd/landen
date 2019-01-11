package be.vdab.landen;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
class WebServiceConfig {

	@Bean
	XsdSchema countriesSchema() {
	  return new SimpleXsdSchema(new ClassPathResource("serviceTypes.xsd"));
	}
	
	// WSDL zal er zijn op  http://localhost:8080/ws/countries.wsdl
	@Bean(name = "countries")
	DefaultWsdl11Definition defaultWsdl11Definition() {
	  DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
	  definition.setPortTypeName("Countries"); // naam van je service in het WSDL bestand
	  definition.setLocationUri("/ws"); // begin van de URL van het WSDL bestand
	  definition.setSchema(countriesSchema()); // gebruik bean op vorige slide
	  return definition;
	}

	@Bean	
	public ServletRegistrationBean messageDispatcherServlet(ApplicationContext context) {
	  MessageDispatcherServlet servlet = new MessageDispatcherServlet();
	  servlet.setApplicationContext(context);
	  servlet.setTransformWsdlLocations(true);
	  // requests met een URL die begint met /ws zijn geen REST requests, maar SOAP requests:
	  return new ServletRegistrationBean(servlet, "/ws/*");
	}
	
}