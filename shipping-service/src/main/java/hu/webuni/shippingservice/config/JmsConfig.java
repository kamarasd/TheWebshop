package hu.webuni.shippingservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.jms.ConnectionFactory;

import org.apache.activemq.broker.BrokerService;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
public class JmsConfig {

    @Bean
    public MessageConverter jacksonJmsMessageConverter(ObjectMapper objectMapper) {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setObjectMapper(objectMapper);
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }
    
   /* @Bean
    public JmsListenerContainerFactory<?> deliveryListener(ConnectionFactory cF, DefaultJmsListenerContainerFactoryConfigurer config) { 
    	DefaultJmsListenerContainerFactory defaultListener = new DefaultJmsListenerContainerFactory();
    	((SingleConnectionFactory) cF).setClientId("shipping-service");
    	config.configure(defaultListener, cF);
    	defaultListener.setSubscriptionDurable(true);
    	return defaultListener;
    	
    }*/
}
