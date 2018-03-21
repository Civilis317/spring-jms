package nl.boip.eop.jms.main;

import nl.boip.eop.commons.message.Message;
import org.hornetq.api.core.HornetQException;
import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.api.core.client.ClientSessionFactory;
import org.hornetq.api.core.client.HornetQClient;
import org.hornetq.api.core.client.ServerLocator;
import org.hornetq.core.remoting.impl.netty.NettyConnectorFactory;
import org.hornetq.jms.client.HornetQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableJms
public class JmsClientApplication {
    private static final Logger logger = LoggerFactory.getLogger(JmsClientApplication.class);

    @Bean
    public ClientSessionFactory sessionFactory() throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("host", "localhost");
        map.put("port", 5445);
        ServerLocator serverLocator = HornetQClient.createServerLocatorWithoutHA(new TransportConfiguration(NettyConnectorFactory.class.getName(), map));
        return serverLocator.createSessionFactory();
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(JmsClientApplication.class, args);
        try {
            context.getBean(JmsSender.class).sendMessage("Test");
        } catch (HornetQException e) {
            logger.error(e.getMessage(), e);
        }


    }
}
