package bank.account.cmd.dispatch;

import bank.cqrs.command.AbstractCommand;
import bank.cqrs.command.CommandDispatcher;
import bank.cqrs.command.CommandHandler;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class AccountCommandDispatcher implements CommandDispatcher {

    private final Map<Class<? extends AbstractCommand>, List<CommandHandler>> routes = new HashMap<>();

    @Override
    public <T extends AbstractCommand> void registerHandler(CommandHandler<T> handler) {
        var handlers = routes.computeIfAbsent(handler.commandType(), c -> new LinkedList<>());
        handlers.add(handler);
    }

    @Override
    public void send(AbstractCommand command) {
        var handlers = routes.get(command.getClass());
        if (handlers == null || handlers.size() == 0) {
            throw new RuntimeException("No command handler was registered!");
        }
        if (handlers.size() > 1) {
            throw new RuntimeException("Cannot send command to more than one handler!");
        }
        handlers.getFirst().handle(command);
    }
}
