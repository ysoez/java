package bank.api.mock;

import bank.api.model.Transaction;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

/**
 * ~ Mocks an HTTP server that receives purchase transactions in real time
 */
public class IncomingTransactionLoader implements Iterator<Transaction> {

    private static final String INPUT_TRANSACTIONS_FILE = "user-transactions.txt";
    private final Iterator<Transaction> transactionIterator;

    public IncomingTransactionLoader(){
        this.transactionIterator = loadTransactions().iterator();
    }

    @Override
    public boolean hasNext() {
        return transactionIterator.hasNext();
    }

    @Override
    public Transaction next() {
        return transactionIterator.next();
    }

    private List<Transaction> loadTransactions() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(INPUT_TRANSACTIONS_FILE);

        if (inputStream == null) {
            return Collections.emptyList();
        }

        var scanner = new Scanner(inputStream);
        List<Transaction> transactions = new ArrayList<>();
        while (scanner.hasNextLine()) {
            transactions.add(parseTransaction(scanner));
        }

        return Collections.unmodifiableList(transactions);
    }

    private Transaction parseTransaction(Scanner scanner) {
        String[] transaction = scanner.nextLine().split(" ");
        String user = transaction[0];
        String transactionLocation = transaction[1];
        var amount = new BigDecimal(transaction[2]);
        return new Transaction(user, amount, transactionLocation);
    }

}
