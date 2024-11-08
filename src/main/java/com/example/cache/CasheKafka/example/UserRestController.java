package com.example.cache.CasheKafka.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@Slf4j
@RestController
@RequestMapping
public class UserRestController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private UsersStorage userStorage;

    // обрабатывает из Postman GET:localhost:8080/notify?msg=hello
    @GetMapping("/notify")
    public void notifyUsers(@RequestParam("msg") String msg) {
        log.info("############### msg = " + msg);
        Set<Integer> allUserIds = userStorage.getAllUserIds();  // список всех юзеров
        // вариант посылки сообщений без кафки
//        allUserIds.forEach(id -> userStorage.notifyById(id, msg));  // посылаем сообщение по id

        // вариант посылки сообщений через кафку
        allUserIds.forEach(id -> kafkaTemplate.send(KafkaCfg.TOPIC_CLIENT_NOTIFICATIONS, id + "^" + msg));
    }
}
