package transfer.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import transfer.domain.TransferRequest;
import transfer.domain.TransferResponse;
import transfer.service.TransferManager;

@Slf4j
@RestController
@RequiredArgsConstructor
class TransferController {

    private final TransferManager transferManager;

    @PostMapping("/api/transfer")
    TransferResponse startTransfer(@RequestBody TransferRequest request) {
        return transferManager.transfer(request);
    }

}
