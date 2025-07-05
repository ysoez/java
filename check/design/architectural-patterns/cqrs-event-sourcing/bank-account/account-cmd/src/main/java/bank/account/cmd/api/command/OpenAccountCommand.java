package bank.account.cmd.api.command;

import bank.cqrs.command.AbstractCommand;
import bank.domain.AccountType;
import lombok.Data;

@Data
public class OpenAccountCommand extends AbstractCommand {
    private String accountHolder;
    private AccountType accountType;
    private double openingBalance;
}