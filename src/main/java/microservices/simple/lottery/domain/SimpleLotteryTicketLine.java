package microservices.simple.lottery.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class represents a SimpleLotteryTicketLine in our application.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class SimpleLotteryTicketLine implements Comparable<SimpleLotteryTicketLine>
{

    // Both factors
    @JsonProperty
    private final int numberOne;
    @JsonProperty
    private final int numberTwo;
    @JsonProperty
    private final int numberThree;

    @JsonProperty
    private int outcome;

    // The result of the operation A * B
    @JsonProperty
    private int result;

    public SimpleLotteryTicketLine(final int numberOne, final int numberTwo, final int numberThree)
    {
        this.numberOne = numberOne;
        this.numberTwo = numberTwo;
        this.numberThree = numberThree;

    }

    public int getNumberOne()
    {
        return this.numberOne;
    }

    public int getNumberTwo()
    {
        return this.numberTwo;
    }

    public int getNumberThree()
    {
        return this.numberThree;
    }

    public int getResult()
    {
        return this.result;
    }

    public int getOutcome()
    {
        return this.outcome;
    }

    public void setOutcome(int outcome)
    {
        this.outcome = outcome;
    }

    @Override
    public String toString()
    {
        return "SimpleLotteryTicketLine{" + "numberOne=" + this.numberOne + ", numberTwo=" + this.numberTwo + ", result(A*B)=" + this.result
                + '}';
    }

    @Override
    public int compareTo(SimpleLotteryTicketLine compareTicketLine)
    {
        final int comparePosition = compareTicketLine.getOutcome();

        //ascending order
        return outcome - comparePosition;
    }
}
