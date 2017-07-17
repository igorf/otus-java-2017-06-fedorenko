package com.otus.hw06.atm.util;

import java.util.*;

public class AtmCommandArgumentsParser {
    public static Map<Integer, Integer> parseBanknotesListArgs(String args) throws Exception {
        Map<Integer, Integer> result = new HashMap<>();

        String[] pairs = args.trim().split(",");
        for (String pair: pairs) {
            String[] kv = pair.trim().split(":");
            result.put(Integer.valueOf(kv[0]), Integer.valueOf(kv[1]));
        }
        return result;
    }

    public static List<String> splitIncomingCommand(String incoming) {
        return new ArrayList<String>(Arrays.asList(incoming.trim().split(" ")));
    }
}
