package main.java.model.response;

import java.util.Hashtable;
import java.util.Map;
import java.util.stream.Collectors;

public class ResponseHeaders {
    Hashtable<String, String> responseHashTable;

    public ResponseHeaders(Hashtable<String, String> responseHashTable) {
        this.responseHashTable = responseHashTable;
    }

    @Override
    public String toString() {
        return responseHashTable.entrySet().stream()
                .map(entry ->
                        entry.getKey() + ":" + entry.getValue()
                ).collect(Collectors.joining("\n"));

        /*StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, String> entry : responseHashTable.entrySet()) {
            sb.append(entry.getKey()).append(":").append(entry.getValue()).append("\n");
        }

        responseHashTable.entrySet().stream()
                .map(entry->
                        entry.getKey() + ":" + entry.getValue() + "\n"
                ).toString();

        return sb.toString();*/
    }
}
