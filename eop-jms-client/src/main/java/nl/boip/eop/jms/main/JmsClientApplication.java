package nl.boip.eop.jms.main;

import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.api.jms.HornetQJMSClient;
import org.hornetq.api.jms.JMSFactoryType;
import org.hornetq.core.remoting.impl.netty.NettyConnectorFactory;
import org.hornetq.jms.client.HornetQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;

@SpringBootApplication
@EnableJms
public class JmsClientApplication {
    private static final Logger logger = LoggerFactory.getLogger(JmsClientApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(JmsClientApplication.class, args);
    }

    @Bean
    public JmsListenerContainerFactory<?> jmsConnectionFactory(
            ConnectionFactory hornetConnectionFactory,
            DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, hornetConnectionFactory);
        return factory;
    }

    @Bean
    public HornetQConnectionFactory hornetConnectionFactory() {
        return HornetQJMSClient.createConnectionFactoryWithoutHA(
                JMSFactoryType.CF,
                new TransportConfiguration(NettyConnectorFactory.class.getName()));
    }

}
