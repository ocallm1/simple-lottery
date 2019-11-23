package microservices.simple.lottery.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class SimpleLotteryTicket
{
    @JsonProperty
    private List<SimpleLotteryTicketLine> lines;

    @JsonProperty
    private List<Error> errors;

    public SimpleLotteryTicket(List<SimpleLotteryTicketLine> lines)
    {
        this.lines = lines;
    }

    public SimpleLotteryTicket()
    {
    }

    public Boolean getStatusChecked()
    {
        return this.statusChecked;
    }

    public void setStatusChecked(Boolean statusChecked)
    {
        this.statusChecked = statusChecked;
    }

    @JsonProperty
    private Boolean statusChecked = false;

    public List<SimpleLotteryTicketLine> getLines()
    {
        return this.lines;
    }

    public void setLines(List<SimpleLotteryTicketLine> lines)
    {
        this.lines = lines;
    }


}
