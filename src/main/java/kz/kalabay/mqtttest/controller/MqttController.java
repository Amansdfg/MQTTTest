package kz.kalabay.mqtttest.controller;

import kz.kalabay.mqtttest.config.MqttConfig;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MqttController {
    private final MqttConfig mqttConfig;
    public MqttController(MqttConfig mqttConfig) {
        this.mqttConfig = mqttConfig;
    }
    @GetMapping("/publish/{message}")
    public String publish(@PathVariable String message) throws MqttException {
        mqttConfig.publishMessage(message);
        return "Published message: " + message;
    }
    @GetMapping("/subscribe")
    public String subscribe(String topic) throws MqttException {
        mqttConfig.subscribe();
        return "Subscribed message: " + topic;
    }
}
