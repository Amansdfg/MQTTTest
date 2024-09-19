package kz.kalabay.mqtttest.config;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqttConfig {
    private static final String BROKER_URL = "tcp://localhost:1883";
    private static final String CLIENT_ID = "spring-client";
    private static final String TOPIC = "test/topic";
    @Bean
    public MqttClient mqttClient() throws MqttException {
        MqttClient mqttClient = new MqttClient(BROKER_URL,CLIENT_ID,new MemoryPersistence());
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setCleanSession(true);
        mqttClient.connect(mqttConnectOptions);
        return mqttClient;
    }
    public void publishMessage(String content) throws MqttPersistenceException, MqttException {
        MqttMessage mqttMessage = new MqttMessage(content.getBytes());
        mqttMessage.setQos(2);
        mqttClient().publish(TOPIC,mqttMessage);
    }
    public void subscribe() throws MqttException {
        mqttClient().subscribe(TOPIC,(topic,msg)->{
            String receivedMsg = new String(msg.getPayload());
            System.out.println("Message received"+receivedMsg);
        });

    }
}
