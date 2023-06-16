package transfer.service;

import org.springframework.stereotype.Service;
import transfer.domain.TradeRequest;
import transfer.domain.TradeResponse;
import transfer.domain.TradeStatus;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class CryptoTradeService {

    public TradeResponse buyCrypto(TradeRequest trade) {
        return TradeResponse.builder()
                .transactionId(String.valueOf(ThreadLocalRandom.current().nextLong()))
                .amount(trade.getAmount())
                .currency(trade.getBaseCurrency())
                .status(TradeStatus.PENDING)
                .build();
    }

}
