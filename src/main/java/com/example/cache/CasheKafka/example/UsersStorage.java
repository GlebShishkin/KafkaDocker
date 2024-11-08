package com.example.cache.CasheKafka.example;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Repository
public class UsersStorage {
    private static final Map<Integer, User> users = new HashMap<>();

    static {
        for (int i=0; i < 10000; i++) {
            users.put(i, new User(i, "User_" + i));
        }
    }

    public void notifyById(int id, String msg) {
        if (users.containsKey(id))
            users.get(id).notify(msg);
    }

    public Set<Integer> getAllUserIds(){
        return users.keySet();
    }
}
