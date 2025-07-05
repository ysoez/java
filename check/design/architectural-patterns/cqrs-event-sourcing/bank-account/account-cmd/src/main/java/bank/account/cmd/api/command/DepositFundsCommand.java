package bank.account.cmd.api.command;

import bank.cqrs.command.AbstractCommand;
import lombok.Data;

@Data
public class DepositFundsCommand extends AbstractCommand {
    private double amount;
}
