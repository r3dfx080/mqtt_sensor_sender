package com.client.mqttclient;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Service
public class MqttService {
    private final MqttClient mqttClient;

    public MqttService(MqttClient mqttClient) {
        this.mqttClient = mqttClient;
    }

    public void publishMessage() {
        String payload = "18";
        String topic = "some shit idk";
        try {
            MqttMessage message = new MqttMessage(payload.getBytes());
            message.setQos(1); // QoS 1 для гарантированной доставки
            mqttClient.publish(topic, message);
            System.out.println("Message published to topic: " + topic);
        } catch (MqttException e) {
            throw new RuntimeException("Failed to publish message", e);
        }
    }

    public void eternalPublishMessage() throws InterruptedException {

        int minute4Send = LocalDateTime.now().getMinute();
        while (true){
            if(LocalDateTime.now().getMinute() == minute4Send){
                publishMessage();
                TimeUnit.SECONDS.sleep(60);
            }


        }
    }


    /*
    public void subscribeToTopic(String topic) {
        try {
            mqttClient.subscribe(topic, (receivedTopic, message) -> {
                System.out.println("Message received from topic: " + receivedTopic);
                System.out.println("Message: " + new String(message.getPayload()));
            });
            System.out.println("Subscribed to topic: " + topic);
        } catch (MqttException e) {
            throw new RuntimeException("Failed to subscribe to topic", e);
        }
    } */
}
