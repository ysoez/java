package bank.cqrs.command;

public interface CommandDispatcher {

    <T extends AbstractCommand> void registerHandler(CommandHandler<T> handler);

    void send(AbstractCommand command);

}
