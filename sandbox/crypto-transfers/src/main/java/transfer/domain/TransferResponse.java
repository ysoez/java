package transfer.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import transfer.domain.exception.TransferException;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferResponse {
    private Long transferTxId;
    private TransferStatus status;
    private TransferException.ErrorReason reason;
}
