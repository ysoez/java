package algorithm.encode;

public class Base10Encoder {

    private final int base;
    private final String characters;

    public final static Base10Encoder BASE_2 = new Base10Encoder(2, "01");
    public final static Base10Encoder BASE_16 = new Base10Encoder(16, "0123456789ABCDEF");
    public final static Base10Encoder BASE_62 = new Base10Encoder(62, "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");
    public final static Base10Encoder BASE_58 = new Base10Encoder(58, "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz");

    public Base10Encoder(int base, String characters) {
        this.base = base;
        this.characters = characters;
    }

    public String encode(long number) {
        StringBuilder stringBuilder = new StringBuilder(1);
        do {
            stringBuilder.insert(0, characters.charAt((int) (number % base)));
            number /= base;
        } while (number > 0);
        return stringBuilder.toString();
    }

    public long decode(String number) {
        long result = 0L;
        int length = number.length();
        for (int i = 0; i < length; i++) {
            result += (long) Math.pow(base, i) * characters.indexOf(number.charAt(length - i - 1));
        }
        return result;
    }

}
