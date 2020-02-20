package org.zee.app.zeemon.extensions.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.messaging.MessageChannel;

@Configuration
@EnableIntegration
public class IntegrationConfig {

    @Bean
    public MessageChannel taskExecutionInputChannel() {
    	QueueChannel channel =  new QueueChannel(1000);
    	return channel;
    }
    
    @Bean
    public MessageChannel actionExecutionInputChannel() {
    	QueueChannel channel =  new QueueChannel(1000);
    	return channel;
    }
}
