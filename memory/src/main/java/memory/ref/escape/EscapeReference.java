package memory.ref.escape;

import java.util.HashMap;
import java.util.Map;

class EscapeReference {

    public static void main(String[] args) {
        var records = new CustomerRecords().get();
        // ~ broken abstraction & encapsulation
        records.clear();
    }

    private static class CustomerRecords {

        private final Map<String, Integer> records;

        CustomerRecords() {
            this.records = new HashMap<>();
        }

        Map<String, Integer> get() {
            return records;
        }
    }

}