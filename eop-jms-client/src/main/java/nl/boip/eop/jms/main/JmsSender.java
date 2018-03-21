package nl.boip.eop.jms.main;

import org.hornetq.api.core.HornetQException;
import org.hornetq.api.core.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class JmsSender {
    private static final Logger logger = LoggerFactory.getLogger(JmsSender.class);
    private final static String QUEUE_NAME = "queue.exampleQueue";
    private final static String MSG_KEY = "msgKey";

    @Autowired
    private final ClientSessionFactory factory;

    public JmsSender(ClientSessionFactory factory) {
        this.factory = factory;
    }

    public void sendMessage(String msgContent) throws HornetQException {
        ClientSession session = factory.createSession();

        ClientProducer producer = session.createProducer(QUEUE_NAME);
        ClientMessage message = session.createMessage(false);
        message.putStringProperty(MSG_KEY, msgContent);
        logger.info("Sending message: {}", msgContent);
        producer.send(message);

        ClientConsumer messageConsumer = session.createConsumer(QUEUE_NAME);
        session.start();
        int n = 0;
        boolean flag = true;
        while (flag) {
            n++;
            ClientMessage messageReceived = messageConsumer.receive(1000);
            if (messageReceived == null) {
                flag = false;
            }
            logger.info("Msg: {}", n);
            logger.info("Received message: {}", messageReceived.getStringProperty(MSG_KEY));
        }
        session.close();
    }
}
