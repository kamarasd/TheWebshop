package hu.webuni.shippingservice.config;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hu.webuni.shippingservice.xml.SystemXmlWs;
import javax.xml.ws.Endpoint;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebserviceConfig {
	
	private final Bus bus;
	private final SystemXmlWs systemXmlWs;
	
	@Bean
	public Endpoint endpoint() {
		EndpointImpl endpoint = new EndpointImpl(bus, systemXmlWs);
		endpoint.publish("/shipmentService");
		return endpoint;
	}

}
