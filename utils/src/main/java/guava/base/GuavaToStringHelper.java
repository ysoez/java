package guava.base;

import com.google.common.base.MoreObjects;

class GuavaToStringHelper {

    public static void main(String[] args) {
        System.out.println(new Entry("1", "A"));
    }

    private record Entry(String key, String value) {
        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("key", key)
                    .add("value", value)
                    .omitNullValues()
                    .toString();
        }

    }

}