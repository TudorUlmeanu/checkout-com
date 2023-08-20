package com.example.paymentgateway.activemq;

import com.example.paymentgateway.domain.TransactionRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ScheduledMessage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsOperations;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class ActiveMQProducer {

    @Value("${amq.transaction.queue}")
    private String queue;

    @Qualifier("jmsTemplate")
    private final JmsOperations jmsTemplate;

    public ActiveMQProducer(final JmsOperations jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public String publish(TransactionRequest message) {
        String messageId = UUID.randomUUID().toString();
        log.info("Sending Message with Message Id : {}", messageId);
        produceMessage(UUID.randomUUID().toString(), message);
        return "Done";
    }

    private void produceMessage(String messageUUID, TransactionRequest message) {
        try {
            log.info("Sending Message with JMSCorrelationID : {}", messageUUID);

            jmsTemplate.convertAndSend(queue, message, msgProcessor -> {
                msgProcessor.setJMSCorrelationID(messageUUID);
                msgProcessor.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, 10);
                msgProcessor.setIntProperty(ScheduledMessage.AMQ_SCHEDULED_REPEAT, 0);
                return msgProcessor;
            });


        } catch (Exception e) {
            log.error("Caught the exception!!! ", e);
            throw new RuntimeException("Cannot send message to the Queue");
        }
    }
}
