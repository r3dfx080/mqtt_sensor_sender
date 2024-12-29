package com.client.mqttclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.eclipse.paho.client.mqttv3.*;

@SpringBootApplication
public class MqttclientApplication   {

	public static void main(String[] args){
		SpringApplication.run(MqttclientApplication.class, args);
	}



}
