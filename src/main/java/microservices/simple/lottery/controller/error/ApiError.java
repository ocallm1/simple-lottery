package microservices.simple.lottery.controller.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by ocallm1 on 18/12/19.
 */
class ApiError
{
    private HttpStatus        status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private String            message;
    private String            debugMessage;
    private List<ApiSubError> subErrors;

    private ApiError()
    {
        LocalDateTime timestamp = LocalDateTime.now();
    }

    ApiError(HttpStatus status)
    {
        this();
        this.status = status;
    }

    ApiError(HttpStatus status, Throwable ex)
    {
        this();
        this.status = status;
        this.message = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
    }

    ApiError(HttpStatus status, String message, Throwable ex)
    {
        this();
        this.status = status;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();

    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(final String message)
    {
        this.message = message;
    }

    public HttpStatus getStatus()
    {
        return status;
    }

    public void setStatus(final HttpStatus status)
    {
        this.status = status;
    }
}

abstract class ApiSubError
{

}

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
class ApiValidationError extends ApiSubError
{
    private final String object;
    private final String message;
    private       String field;
    private       Object rejectedValue;

    ApiValidationError(String object, String message)
    {
        this.object = object;
        this.message = message;
    }
}
