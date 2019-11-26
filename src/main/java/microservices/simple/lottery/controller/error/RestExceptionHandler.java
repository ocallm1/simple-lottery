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
    protected ResponseEntity<Object> handleAmendEntity(final AmendTicketException ex)
    {
        final ApiError apiError = new ApiError(NOT_FOUND);
        apiError.setMessage(ex.getMessage());
        return this.buildResponseEntity(apiError);
    }

    @ExceptionHandler(TicketNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(final TicketNotFoundException ex)
    {
        final ApiError apiError = new ApiError(NOT_FOUND);
        apiError.setMessage(ex.getMessage());
        return this.buildResponseEntity(apiError);
    }

    @ExceptionHandler(CheckTicketException.class)
    protected ResponseEntity<Object> handleEntityNotFound(final CheckTicketException ex)
    {
        final ApiError apiError = new ApiError(NOT_FOUND);
        apiError.setMessage(ex.getMessage());
        return this.buildResponseEntity(apiError);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError)
    {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
