package com.example.java8.var;

import java.util.*;

public class D02_CollectionFactories {

    public static void main(String[] args) {

        //Java 8: build a mutable collection, then wrap it to make it read-only
        List<Integer> responseTimes = Collections.unmodifiableList(Arrays.asList(100, 200, 150, 300));

        Map<String, Integer> limits = new HashMap<>();
        limits.put("warn", 200);
        limits.put("critical", 500);
        limits = Collections.unmodifiableMap(limits);

        Set<String> servers = Collections.unmodifiableSet(new HashSet<>(Arrays.asList("api-1", "api-2", "api-3")));

        System.out.println("times = " + responseTimes);
        System.out.println("warn = " + limits.get("warn"));
        System.out.println("api-2? = " + servers.contains("api-2"));

        try {
            responseTimes.add(999);
        } catch (UnsupportedOperationException e) {
            System.out.println("add on the list -> UnsupportedOperationException");
        }

        List<Integer> withNull = Arrays.asList(999, null);
        System.out.println("Arrays.asList , accepts null elements"+ withNull);
    }
}
