package bank.cqrs.command;

public interface CommandHandler<T extends AbstractCommand> {

    Class<T> commandType();

    void handle(T command);
}
