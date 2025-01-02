package bank.account.cmd.api;

import bank.account.cmd.api.command.CloseAccountCommand;
import bank.account.cmd.api.command.DepositFundsCommand;
import bank.account.cmd.api.command.OpenAccountCommand;
import bank.account.cmd.api.command.WithdrawFundsCommand;
import bank.cqrs.command.CommandDispatcher;
import bank.cqrs.query.exception.AggregateNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/account")
@RequiredArgsConstructor
public class AccountController {

    private final CommandDispatcher cmdDispatcher;

    @PostMapping(path = "/open")
    public ResponseEntity<String> openAccount(@RequestBody OpenAccountCommand cmd) {
        var id = UUID.randomUUID().toString();
        cmd.setId(id);
        try {
            cmdDispatcher.send(cmd);
            return new ResponseEntity<>("Created account: " + id, HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            log.warn("Client made a bad request: {}", e, e);
            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            var safeErrorMessage = MessageFormat.format("Cannot open account: {0}", id);
            log.error(safeErrorMessage, e);
            return new ResponseEntity<>(safeErrorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/{id}/deposit")
    public ResponseEntity<String> depositFunds(@PathVariable("id") String id,
                                               @RequestBody DepositFundsCommand cmd) {
        try {
            cmd.setId(id);
            cmdDispatcher.send(cmd);
            return new ResponseEntity<>("Deposit success: " + id, HttpStatus.OK);
        } catch (IllegalStateException | AggregateNotFoundException e) {
            log.warn("Client made a bad request: {}", e, e);
            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            var safeErrorMessage = MessageFormat.format("Cannot deposit funds to bank account: {0}", id);
            log.error(safeErrorMessage, e);
            return new ResponseEntity<>(safeErrorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/{id}/withdraw")
    public ResponseEntity<String> withdrawFunds(@PathVariable("id") String id,
                                                @RequestBody WithdrawFundsCommand cmd) {
        try {
            cmd.setId(id);
            cmdDispatcher.send(cmd);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalStateException | AggregateNotFoundException e) {
            log.warn("Client made a bad request: {}", e, e);
            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            var safeErrorMessage = MessageFormat.format("Cannot withdraw funds from bank account: {0}", id);
            log.error(safeErrorMessage, e);
            return new ResponseEntity<>(safeErrorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/{id}/close")
    public ResponseEntity<String> closeAccount(@PathVariable(value = "id") String id) {
        try {
            cmdDispatcher.send(new CloseAccountCommand(id));
            return new ResponseEntity<>("Closed account: " + id, HttpStatus.OK);
        } catch (IllegalStateException | AggregateNotFoundException e) {
            log.warn("Client made a bad request: {}", e, e);
            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            var safeErrorMessage = MessageFormat.format("Cannot close bank account: {0}", id);
            log.error(safeErrorMessage, e);
            return new ResponseEntity<>(safeErrorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
