package transfer.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import transfer.domain.TransferRequest;
import transfer.domain.TransferResponse;
import transfer.domain.TransferType;
import transfer.domain.entity.TransferEntity;
import transfer.domain.exception.TransferException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static transfer.domain.exception.TransferException.ErrorReason.UNKNOWN_TRANSFER_TYPE;

@Service
@RequiredArgsConstructor
class TransferExecutor {

    private final List<TransferService> transferServiceList;
    private Map<TransferType, TransferService> transferServices;

    @PostConstruct
    public void init() {
        transferServices = new HashMap<>();
        transferServiceList.forEach(transferService -> transferServices.put(transferService.transferType(), transferService));
    }

    @Transactional
    public TransferResponse execute(TransferRequest request) {
        TransferService transferService = transferServices.get(request.getType());
        if (transferService == null) {
            throw new TransferException(UNKNOWN_TRANSFER_TYPE, "Not supported: " + request.getType());
        }
        TransferEntity transferEntity = transferService.startTransfer(request);
        if (transferService.isInstant()) {
            transferService.completeTransfer(transferEntity);
        }
        return TransferResponse.builder()
                .transferTxId(transferEntity.getId())
                .status(transferEntity.getStatus())
                .build();
    }

}
