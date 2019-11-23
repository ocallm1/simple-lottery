package microservices.simple.lottery.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class SimpleLotteryTicket
{
    @JsonProperty
    private List<SimpleLotteryTicketLine> lines;

    public SimpleLotteryTicket(final List<SimpleLotteryTicketLine> lines)
    {
        this.lines = lines;
    }

    public SimpleLotteryTicket()
    {
    }

    public Boolean getStatusChecked()
    {
        return statusChecked;
    }

    public void setStatusChecked(final Boolean statusChecked)
    {
        this.statusChecked = statusChecked;
    }

    @JsonProperty
    private Boolean statusChecked = false;

    public List<SimpleLotteryTicketLine> getLines()
    {
        return lines;
    }

    public void setLines(final List<SimpleLotteryTicketLine> lines)
    {
        this.lines = lines;
    }


}
