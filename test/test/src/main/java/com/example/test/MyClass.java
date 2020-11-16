package com.example.test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MyClass {
    public static void main(String[] args) {

        String[] participant = {"mislav", "stanko", "mislav", "ana"};
        String[] completion = {"stanko", "ana", "mislav"};

        HashMap<String, Integer> map = new HashMap<>();
        String answer = "";
        for (int i = 0; i < participant.length; i++) {
            if (map.containsKey(participant[i])) {
                int val = map.get(participant[i]);
                map.replace(participant[i], val + 1);
            }else{
                map.put(participant[i], 1);
            }
        }

        for (int i = 0; i < completion.length; i++) {
            int val = map.get(completion[i]);

            if (map.containsKey(completion[i])) {
                if (val == 1) map.remove(completion[i]);

            }else if(val>1) {
                map.replace(completion[i], val - 1);
            }else{
                answer = completion[i];
            }
        }

        Set set = map.entrySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String key = (String) entry.getKey();
            answer = key;
        }
        System.out.println(answer);
    }
}