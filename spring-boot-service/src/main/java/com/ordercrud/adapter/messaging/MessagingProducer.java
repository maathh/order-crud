package com.ordercrud.adapter.messaging;

import com.ordercrud.util.enums.Topic;

public interface MessagingProducer {

    <T extends MessagingData> void send(Topic topic, T data);

}
