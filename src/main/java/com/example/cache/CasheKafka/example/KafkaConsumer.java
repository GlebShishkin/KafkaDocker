package com.example.cache.CasheKafka.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.KafkaListeners;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
// косьюмер
public class KafkaConsumer {

    @Autowired
    private UsersStorage usersStorage;  // Map с User-ами

    // пул из 10 потоков для распараллеливания сообшений
    // !!!не нужно делать акцента на нескольких потоках - это сделано "просто так"
    // , чтобы показать обработку сообщений в потоках!!!
    private ExecutorService executorService = Executors.newFixedThreadPool(10);

    @KafkaListener(topics = KafkaCfg.TOPIC_CLIENT_NOTIFICATIONS)
    public void listen(String msg) {

        // из кафки достаем id и сообщение
        String[] split = msg.split("\\^");
        String userId = split[0];
        String userMsg = split[1];

        // когда получаем сообщение из кафки -> отправляем его в один из потоков пула
        executorService.submit(() -> {
            usersStorage.notifyById(Integer.parseInt(userId), userMsg);
        });
    }
}
