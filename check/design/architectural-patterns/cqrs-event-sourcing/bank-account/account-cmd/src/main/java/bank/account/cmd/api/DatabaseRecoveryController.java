package bank.account.cmd.api;

import bank.cqrs.command.CommandDispatcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/restoreReadDb")
public class DatabaseRecoveryController {

    @Autowired
    private CommandDispatcher commandDispatcher;

//    @PostMapping
//    public ResponseEntity<BaseResponse> restoreReadDb() {
//        try {
//            commandDispatcher.send(new RestoreReadDbCommand());
//            return new ResponseEntity<>(new BaseResponse("Read database restore request completed successfully!"), HttpStatus.CREATED);
//        } catch (IllegalStateException e) {
//            logger.log(Level.WARNING, MessageFormat.format("Client made a bad request - {0}.", e.toString()));
//            return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
//        } catch (Exception e) {
//            var safeErrorMessage = "Error while processing request to restore read database.";
//            logger.log(Level.SEVERE, safeErrorMessage, e);
//            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

}
