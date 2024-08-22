package library.reflection.modifier;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

class ModifierInspector {

    public static void main(String[] args) throws ClassNotFoundException {
        printClassModifiers(Auction.class);
        printClassModifiers(Bid.class);
        printClassModifiers(Bid.Builder.class);
        printClassModifiers(Class.forName("reflection.modifier.ModifierInspector$Bid$Builder$BidImpl"));

        printMethodsModifiers(Auction.class.getDeclaredMethods());

        printFieldsModifiers(Auction.class.getDeclaredFields());
        printFieldsModifiers(Bid.class.getDeclaredFields());
    }

    public static void printFieldsModifiers(Field[] fields) {
        for (Field field : fields) {
            int modifier = field.getModifiers();
            System.out.printf("Field \"%s\" access modifier is %s%n", field.getName(), getAccessModifierName(modifier));
            if (Modifier.isVolatile(modifier)) {
                System.out.println("The field is volatile");
            }
            if (Modifier.isFinal(modifier)) {
                System.out.println("The field is final");
            }
            if (Modifier.isTransient(modifier)) {
                System.out.println("The field is transient and will not be serialized");
            }
            System.out.println();
        }
    }

    public static void printMethodsModifiers(Method[] methods) {
        for (Method method : methods) {
            int modifier = method.getModifiers();
            System.out.printf("%s() access modifier is %s%n", method.getName(), getAccessModifierName(modifier));
            if (Modifier.isSynchronized(modifier)) {
                System.out.println("The method is synchronized");
            } else {
                System.out.println("The method is not synchronized");
            }
            System.out.println();
        }
    }

    public static void printClassModifiers(Class<?> clazz) {
        int modifier = clazz.getModifiers();
        System.out.printf("Class %s access modifier is %s%n", clazz.getSimpleName(), getAccessModifierName(modifier));
        if (Modifier.isAbstract(modifier)) {
            System.out.println("The class is abstract");
        }
        if (Modifier.isInterface(modifier)) {
            System.out.println("The class is an interface");
        }
        if (Modifier.isStatic(modifier)) {
            System.out.println("The class is static");
        }
    }

    private static String getAccessModifierName(int modifier) {
        if (Modifier.isPublic(modifier)) {
            return "public";
        } else if (Modifier.isPrivate(modifier)) {
            return "private";
        } else if (Modifier.isProtected(modifier)) {
            return "protected";
        } else {
            return "package-private";
        }
    }

    static class Auction implements Serializable {

        private final List<Bid> bids = new ArrayList<>();

        private transient volatile boolean isAuctionStarted;

        public synchronized void addBid(Bid bid) {
            this.bids.add(bid);
        }

        public synchronized List<Bid> getAllBids() {
            return Collections.unmodifiableList(bids);
        }

        public synchronized Optional<Bid> getHighestBid() {
            return bids.stream().max(Comparator.comparing(bid -> bid.price));
        }

        public void startAuction() {
            this.isAuctionStarted = true;
        }

        public void stopAuction() {
            this.isAuctionStarted = false;
        }

        public boolean isAuctionRunning() {
            return isAuctionStarted;
        }

    }

    static abstract class Bid implements Serializable {

        protected int price;
        protected String bidderName;

        public static Builder builder() {
            return new Builder();
        }

        @Override
        public String toString() {
            return "Bid{" +
                    "price=" + price +
                    ", bidderName='" + bidderName + '\'' +
                    '}';
        }

        public static class Builder {
            private int price;
            private String bidderName;

            public Builder setPrice(int price) {
                this.price = price;
                return this;
            }

            public Builder setBidderName(String bidderName) {
                this.bidderName = bidderName;
                return this;
            }

            public Bid build() {
                return new BidImpl();
            }

            private class BidImpl extends Bid {
                private BidImpl() {
                    super.price = Builder.this.price;
                    super.bidderName = Builder.this.bidderName;
                }
            }
        }
    }

}
