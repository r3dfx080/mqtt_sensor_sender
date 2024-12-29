package com.client.mqttclient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

@Configuration
public class MqttConfig {
    private static final String BROKER_URL = "tcp://mqtt.cloud.yandex.net:883";
    private static final String CLIENT_ID = "SensorDataPublisher";

    @Bean
    public MqttClient mqttClient() {
        try {
            MqttClient client = new MqttClient(BROKER_URL, CLIENT_ID);
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);
            client.connect(options);
            return client;
        } catch (MqttException e) {
            throw new RuntimeException("Failed to create MQTT client", e);
        }
    }
}
