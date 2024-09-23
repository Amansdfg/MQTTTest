package kz.kalabay.mqtttest.config;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqttConfig {

    private String BROKER_URL="tcp://localhost:1883";
    private String CLIENT_ID = "spring-client";
    private String TOPIC = "test/topic";
    @Value("${mqtt.username}")
    private String USERNAME;
    @Value("${mqtt.password}")
    private String PASSWORD;
    private MqttClient mqttClient;
    @Bean
    public MqttClient mqttClient() throws MqttException {
        if(mqttClient == null) {
            mqttClient = new MqttClient(BROKER_URL, CLIENT_ID, new MemoryPersistence());
            MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
//            mqttConnectOptions.setCleanSession(true);
            mqttConnectOptions.setUserName(USERNAME);
            mqttConnectOptions.setPassword(PASSWORD.toCharArray());
            mqttClient.connect(mqttConnectOptions);
        }
        return mqttClient;
    }
    public void publishMessage(String content)throws MqttException {
        MqttMessage mqttMessage = new MqttMessage(content.getBytes());
        mqttMessage.setQos(2);
        mqttClient.publish(TOPIC,mqttMessage);
    }
    public void subscribe() throws MqttException {
        mqttClient.subscribe(TOPIC,(topic,msg)->{
            String receivedMsg = new String(msg.getPayload());
            System.out.println("Message received: "+receivedMsg);
        });

    }
}
