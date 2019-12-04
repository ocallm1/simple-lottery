package microservices.simple.lottery.controller.error;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler
{

    //other exception handlers
    @ExceptionHandler(AmendTicketException.class)
    protected ResponseEntity<Object> handleAmendEntity(AmendTicketException ex)
    {
        ApiError apiError = new ApiError(NOT_FOUND);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(TicketNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(TicketNotFoundException ex)
    {
        ApiError apiError = new ApiError(NOT_FOUND);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(CheckTicketException.class)
    protected ResponseEntity<Object> handleEntityNotFound(CheckTicketException ex)
    {
        ApiError apiError = new ApiError(NOT_FOUND);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    private ResponseEntity<Object> buildResponseEntity(final ApiError apiError)
    {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
