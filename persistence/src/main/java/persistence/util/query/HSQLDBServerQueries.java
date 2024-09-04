package persistence.util.query;

public class HSQLDBServerQueries implements Queries {

    public static final Queries INSTANCE = new HSQLDBServerQueries();

    @Override
    public String transactionId() {
        return "VALUES (TRANSACTION_ID())";
    }
}
