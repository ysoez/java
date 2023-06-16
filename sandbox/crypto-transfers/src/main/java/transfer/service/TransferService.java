package transfer.service;

import transfer.domain.entity.TransferEntity;
import transfer.domain.TransferRequest;
import transfer.domain.TransferType;

public interface TransferService {

    TransferEntity startTransfer(TransferRequest transferRequest);

    void completeTransfer(TransferEntity transfer);

    TransferType transferType();

    boolean isInstant();

}
