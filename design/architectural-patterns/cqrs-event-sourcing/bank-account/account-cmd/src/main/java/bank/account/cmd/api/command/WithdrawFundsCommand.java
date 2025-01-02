package bank.account.cmd.api.command;

import bank.cqrs.command.AbstractCommand;
import lombok.Data;

@Data
public class WithdrawFundsCommand extends AbstractCommand {
    private double amount;
}
