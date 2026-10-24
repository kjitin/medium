package com.example.java8.var;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class D01_Var {

    public static void main(String[] args) {
        Map<String, List<Integer>> readingsByServer = new HashMap<String, List<Integer>>();
        List<Integer> readings = new ArrayList<>();
        readings.add(100);
        readings.add(200);
        readingsByServer.put("api-1", readings);

        for (Map.Entry<String, List<Integer>> entry : readingsByServer.entrySet()) {
            System.out.println(entry.getKey()+ " -> " + entry.getValue());
        }
    }
}
