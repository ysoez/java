package transfer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import transfer.domain.TransferRequest;
import transfer.domain.TransferResponse;
import transfer.domain.TransferStatus;
import transfer.domain.exception.TransferException;

@Service
@RequiredArgsConstructor
public class TransferManager {

    private final TransferExecutor transferExecutor;

    public TransferResponse transfer(TransferRequest request) {
        try {
            return transferExecutor.execute(request);
        } catch (TransferException error) {
            return TransferResponse.builder()
                    .status(TransferStatus.REJECTED)
                    .reason(error.getReason())
                    .build();
        }
    }

}
