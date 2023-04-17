package command;

import command.framework.Command;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class SaveCustomerCommand implements Command {

    private final CustomerService customerService;

    @Override
    public void execute() {
        customerService.addCustomer();
    }

}
