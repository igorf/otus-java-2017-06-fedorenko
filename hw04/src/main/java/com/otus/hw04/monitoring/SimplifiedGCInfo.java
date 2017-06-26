package com.otus.hw04.monitoring;

import lombok.Setter;

public class SimplifiedGCInfo {
    private GCGeneration generation = GCGeneration.Unknown;
    @Setter private String name;

    @Override
    public String toString() {
        return "[" + generation + "] " + name;
    }

    void defineGenByAction(String action) {
        if ("end of minor GC".equals(action)) {
            generation = GCGeneration.Young;
        } else if ("end of major GC".equals(action)) {
            generation = GCGeneration.Old;
        } else {
            generation = GCGeneration.Unknown;
        }
    }
}
