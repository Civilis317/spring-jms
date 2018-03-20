package nl.boip.eop.jms.main;

import nl.boip.eop.commons.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class JmsReceiver {
    private final Logger logger = LoggerFactory.getLogger(JmsReceiver.class);
    
    @JmsListener(destination = "opp-queue", containerFactory = "myFactory")
    public void receiveMessage(Message message) {
        logger.info(message.toString());
    }
}
