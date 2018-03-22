package nl.boip.eop.jms.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

@Service
public class JmsService {
    private final Logger logger = LoggerFactory.getLogger(JmsService.class);

    private final JmsTemplate jmsTemplate;

    public JmsService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @JmsListener(destination = "EOpposition", containerFactory = "jmsConnectionFactory")
    public void receiveMessage(Message message) throws JMSException {
        TextMessage txtMessage = (TextMessage) message;
        logger.info(txtMessage.getText());
    }

    public void publish(String msg) {
        logger.info("sending new Message: {}", msg);
        jmsTemplate.convertAndSend("EOpposition", msg);
    }
}
