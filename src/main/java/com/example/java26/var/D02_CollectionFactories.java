package com.example.java26.var;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class D02_CollectionFactories {

    static void main() {
        //Java 9+: one line factory methods that return immutable collections
        var responseTimes = List.of(100, 200, 150, 300);
        var limits = Map.of("warn", 200, "critical", 500);
        var servers = Set.of("api-1", "api-2", "api-3", "api-4", "api-5");

        System.out.println("times = "+ responseTimes);
        System.out.println("warn = "+ limits.get("warn"));
        System.out.println("api2? = "+ servers.contains("api-2"));

        try {
            responseTimes.add(999);
        } catch(UnsupportedOperationException e) {
            System.out.println("add on the list -> UnsupportedOperationException");
        }

        try {
            List.of(1, null);
        } catch(NullPointerException e) {
            System.out.println("List.of(1, null) -> NullPointerException");
        }
    }
}
