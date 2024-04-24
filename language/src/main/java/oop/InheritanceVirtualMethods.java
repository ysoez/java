package oop;

class InheritanceVirtualMethods {

    public static void main(String[] args) {
        new Sub("123");
    }

    static class Super {
        Super() {
            check();
        }

        void check() {
        }
    }

    static class Sub extends Super {
        String value;

        Sub(String value) {
            this.value = value;
        }

        void check() {
            if (value.isEmpty()) {
                throw new IllegalStateException("empty");
            }
        }
    }

}
