package com.bt.logapi.listener;

import com.amazonaws.services.sqs.model.Message;
import com.bt.logapi.config.AmazonSQSComponent;
import com.bt.logapi.service.LogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.aws.messaging.config.annotation.EnableSqs;
import org.springframework.cloud.aws.messaging.listener.Acknowledgment;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@EnableSqs
public class AsyncLogListener {

    private final LogService service;

    public AsyncLogListener(LogService service) {
        this.service = service;
    }

    @SqsListener(value = "${aws.sqs.queue-url}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void receiveMessage(final String message) throws JsonProcessingException {
        log.info("Processing Async Log Listener Request : {}", message);
        service.saveLog(message);
    }
}
