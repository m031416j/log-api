package com.bt.logapi.config;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AmazonSQSComponent {


    private final AmazonSQSAsync amazonSQSAsync;
    private final String queueUrl;

    public AmazonSQSComponent(AmazonSQSAsync amazonSQSAsync, @Value("${aws.sqs.queue-url}") String queueUrl) {
        this.amazonSQSAsync = amazonSQSAsync;
        this.queueUrl = queueUrl;
    }

    public void sendMessage(String messageBody) {
        SendMessageRequest sendMessageRequest = new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageBody(messageBody);
        amazonSQSAsync.sendMessage(sendMessageRequest);
    }

//    public List<Message> receiveMessages() {
//        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest()
//                .withQueueUrl(queueUrl)
//                .withMaxNumberOfMessages(10);
//        ReceiveMessageResult receiveMessageResult = amazonSQSAsync.receiveMessage(receiveMessageRequest);
//        return receiveMessageResult.getMessages();
//    }
}

