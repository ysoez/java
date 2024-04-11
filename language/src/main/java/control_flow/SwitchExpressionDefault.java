package control_flow;

class SwitchExpressionDefault {

    enum When {
        DAY, NIGHT
    }

    public static void main(String[] args) {
        lights(When.DAY);
        lights(When.NIGHT);
    }

    private static void lights(When when) {
        switch (when) {
            case DAY -> System.out.println("Optional");
            case NIGHT -> System.out.println("Required");
            // ~ compiler generates default case that throws IncompatibleClassChangeError
            // ~ if an enum from 3rd party adds a new case and client code not compiled => runtime exception
        }
    }

}
