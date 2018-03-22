package nl.boip.eop.jms.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

@Service
public class JmsService {
    private final Logger logger = LoggerFactory.getLogger(JmsService.class);

    @Value("${hornetq.queue}")
    private String queueName;

    private final JmsTemplate jmsTemplate;
    public JmsService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @JmsListener(destination = "${hornetq.queue}", containerFactory = "jmsConnectionFactory")
    public void receiveMessage(Message message) throws JMSException {
        TextMessage txtMessage = (TextMessage) message;
        logger.info("received message, {}", txtMessage.getText());
        processMessage(txtMessage.getText());
    }

    @JmsListener(destination = "${hornetq.deadLetterQueue}", containerFactory = "jmsConnectionFactory")
    public void receiveDeadLetterMessages(Message message) throws JMSException {
        TextMessage txtMessage = (TextMessage) message;
        logger.info("Dead letter message {}", txtMessage.getText());
    }

    private void processMessage(String msg) {
        throw new RuntimeException("Message processing error. Causing roll-back of trasnaction");
    }

    public void publish(String msg) {
        logger.info("sending new Message: {}", msg);
        jmsTemplate.convertAndSend(queueName, msg);
    }
}
