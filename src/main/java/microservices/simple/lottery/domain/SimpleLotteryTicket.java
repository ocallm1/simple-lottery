package microservices.simple.lottery.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import microservices.simple.lottery.service.Error.ServiceError;

import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class SimpleLotteryTicket
{
    @JsonProperty
    private List<SimpleLotteryTicketLine> lines;
    @JsonProperty
    private List<ServiceError> errors;
    @JsonProperty
    private Boolean statusChecked = false;

    public SimpleLotteryTicket(final List<SimpleLotteryTicketLine> lines)
    {
        this.lines = lines;
    }

    public SimpleLotteryTicket()
    {
    }

    public List<ServiceError> getErrors()
    {
        return errors;
    }

    public void setErrors(final List<ServiceError> errors)
    {
        this.errors = errors;
    }

    public Boolean getStatusChecked()
    {
        return statusChecked;
    }

    public void setStatusChecked(final Boolean statusChecked)
    {
        this.statusChecked = statusChecked;
    }

    public List<SimpleLotteryTicketLine> getLines()
    {
        return lines;
    }

    public void setLines(final List<SimpleLotteryTicketLine> lines)
    {
        this.lines = lines;
    }

}
