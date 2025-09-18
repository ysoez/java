package language.type;

class RecordCompactConstructor {

    public static void main(String[] args) {
        System.out.println(new Year(25));
    }

    record Year(int year) {
        Year {
            if (year < 0) {
                throw new IllegalArgumentException("negative number");
            }
            if (year < 100) {
                year += 200;
            }
        }
    }

}
