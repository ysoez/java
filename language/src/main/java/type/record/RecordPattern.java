package type.record;

class RecordPattern {

    public static void main(String[] args) {
        printSum(new Point(2, 4));
    }

    private static void printSum(Object obj) {
        if (obj instanceof Point(int x, int y)) {
            System.out.println(x + y);
        }
    }

    record Point(int x, int y) {}

}
