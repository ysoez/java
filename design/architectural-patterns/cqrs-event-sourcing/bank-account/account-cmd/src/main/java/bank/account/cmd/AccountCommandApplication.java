package bank.account.cmd;

import bank.cqrs.command.CommandDispatcher;
import bank.cqrs.command.CommandHandler;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class AccountCommandApplication {

    @Autowired
    private CommandDispatcher commandDispatcher;
    @Autowired
    private List<CommandHandler<?>> commandHandlers;

    public static void main(String[] args) {
        SpringApplication.run(AccountCommandApplication.class, args);
    }

    @PostConstruct
    public void registerHandlers() {
        for (CommandHandler<?> commandHandler : commandHandlers) {
            commandDispatcher.registerHandler(commandHandler);
        }
    }

}
