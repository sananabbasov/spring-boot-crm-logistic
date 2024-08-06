package az.websuper.crm.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String NOTIFICATION_FANOUT_EXCHANGE = "notification-fanout-exchange";
    public static final String EMAIL_QUEUE = "email-queue";
    public static final String SMS_QUEUE = "sms-queue";

    @Bean
    public FanoutExchange notificationFanoutExchange() {
        return new FanoutExchange(NOTIFICATION_FANOUT_EXCHANGE);
    }


    @Bean
    public Queue emailQueue() {
        return new Queue(EMAIL_QUEUE);
    }

    @Bean
    public Queue smsQueue() {
        return new Queue(SMS_QUEUE);
    }

    @Bean
    public Binding emailBinding()
    {
        return BindingBuilder.bind(emailQueue()).to(notificationFanoutExchange());
    }

    @Bean
    public Binding smsBinding()
    {
        return BindingBuilder.bind(smsQueue()).to(notificationFanoutExchange());
    }
}
