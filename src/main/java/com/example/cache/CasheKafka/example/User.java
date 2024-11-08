package com.example.cache.CasheKafka.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int id;
    private String name;

    public void notify(String msg) {
        try {
            Thread.sleep(200);
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("thread: " + Thread.currentThread() + "; user: " + name + "; id: " + " was notify");
    }
}
