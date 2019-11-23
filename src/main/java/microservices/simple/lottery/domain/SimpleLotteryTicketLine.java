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
    private int numberOne;
    @JsonProperty
    private int numberTwo;
    @JsonProperty
    private int numberThree;

    @JsonProperty
    private int outcome;

    // The result of the operation A * B
    @JsonProperty
    private int result;

    public SimpleLotteryTicketLine(int numberOne, int numberTwo, int numberThree) {
        this.numberOne = numberOne;
        this.numberTwo = numberTwo;
        this.numberThree = numberThree;

    }

    public int getNumberOne() {
        return numberOne;
    }

    public int getNumberTwo() {
        return numberTwo;
    }

    public int getNumberThree() {
        return numberThree;
    }

    public int getResult() {
        return result;
    }


    public int getOutcome()
    {
        return outcome;
    }

    public void setOutcome(final int outcome)
    {
        this.outcome = outcome;
    }

    @Override
    public String toString() {
        return "SimpleLotteryTicketLine{" +
                "numberOne=" + numberOne +
                ", numberTwo=" + numberTwo +
                ", result(A*B)=" + result +
                '}';
    }

    @Override
    public int compareTo(final SimpleLotteryTicketLine compareTicketLine)
    {
        int comparePosition = ((SimpleLotteryTicketLine) compareTicketLine).getOutcome();

        //ascending order
        return this.outcome - comparePosition;
    }
}
