package az.websuper.crm.consumers;

import az.websuper.crm.messages.OrderStatusChangeMessages;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConsumer {
    private final ObjectMapper objectMapper;
    // Declaring ANSI_RESET so that we can reset the color
    public static final String ANSI_RESET = "\u001B[0m";
    // Declaring the background color
    public static final String ANSI_RED_BACKGROUND
            = "\u001B[41m";
    public EmailConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "email-queue")
    public void handlerMessage(String message) throws JsonProcessingException, InterruptedException {
        OrderStatusChangeMessages result = objectMapper.readValue(message,OrderStatusChangeMessages.class);
        System.out.println(ANSI_RED_BACKGROUND
                +"Email " + result.getEmail() + " addresine gonderildi."+ ANSI_RESET);
    }
}
