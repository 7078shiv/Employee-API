package com.Kapture.Employee.kafka;
import com.Kapture.Employee.entity.Employee;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {
    @KafkaListener(topics = "Employee-details",groupId = "group",containerFactory = "userKafkaListenerContainerFactory")
    public void consumeJson(Employee employee){
        System.out.println("Consumed JSON Message "+ employee);
    }
}
