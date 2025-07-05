package bank.account.query.api.dto;

import bank.account.query.domain.BankAccount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountLookupResponse {
    private String message;
    private List<BankAccount> accounts;

    public AccountLookupResponse(String message) {
        this.message = message;
    }
}
