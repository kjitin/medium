package com.example.java26.var;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class D01_Var {

    static void main() {
        // Java 10+ var lets the compiler infer the type from the right hand side
        var readingsByServer = new HashMap<String, List<Integer>>();
        var readings = new ArrayList<Integer>();
        readings.add(100);
        readings.add(200);
        readingsByServer.put("api-1", readings);

        for(var entry : readingsByServer.entrySet()) {
            System.out.println(entry.getKey() + "->" + entry.getValue());
        }
    }
}
