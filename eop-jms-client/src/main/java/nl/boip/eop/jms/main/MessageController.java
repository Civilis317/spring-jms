package nl.boip.eop.jms.main;

import nl.boip.eop.commons.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/send")
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    private final JmsService jmsService;

    public MessageController(JmsService jmsService) {
        this.jmsService = jmsService;
    }

    @PostMapping("/message")
    public @ResponseBody Message publish(@RequestBody Message message) {
        message.setReceived(System.currentTimeMillis());
        jmsService.publish(message.getMessage());
        return message;
    }

    @GetMapping("/example")
    public @ResponseBody Message example() {
        return new Message("E-Opposition", "eop", "Het regent buiten!");
    }

}
