package microservices.simple.lottery.controller.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

class ApiError
{
    private HttpStatus        status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private String            message;
    private String            debugMessage;
    private List<ApiSubError> subErrors;

    private ApiError()
    {
        final LocalDateTime timestamp = LocalDateTime.now();
    }

    ApiError(final HttpStatus status)
    {
        this();
        this.status = status;
    }

    ApiError(final HttpStatus status, final Throwable ex)
    {
        this();
        this.status = status;
        message = "Unexpected error";
        debugMessage = ex.getLocalizedMessage();
    }

    ApiError(final HttpStatus status, final String message, final Throwable ex)
    {
        this();
        this.status = status;
        this.message = message;
        debugMessage = ex.getLocalizedMessage();

    }

    public String getMessage()
    {
        return this.message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public HttpStatus getStatus()
    {
        return this.status;
    }

    public void setStatus(HttpStatus status)
    {
        this.status = status;
    }
}

abstract class ApiSubError {

}

    @Data
    @EqualsAndHashCode(callSuper = false)
    @AllArgsConstructor
    class ApiValidationError extends ApiSubError {
        private final String object;
        private       String field;
        private       Object rejectedValue;
        private final String message;

        ApiValidationError(final String object, final String message) {
            this.object = object;
            this.message = message;
        }
}
