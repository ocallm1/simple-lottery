package microservices.simple.lottery.service;

import microservices.simple.lottery.domain.SimpleLotteryTicket;
import microservices.simple.lottery.domain.SimpleLotteryTicketLine;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
class SimpleLotteryServiceImpl implements SimpleLotteryService
{
    static final Logger logger = Logger.getLogger(SimpleLotteryServiceImpl.class);

    private final RandomGeneratorService randomGeneratorService;

    // Note tickets stored in memory would be database backed in a real system
    private final List<SimpleLotteryTicket> simpleLotteryTickets = new ArrayList<>();


    @Autowired
    public SimpleLotteryServiceImpl(final RandomGeneratorService randomGeneratorService) {

        this.randomGeneratorService = randomGeneratorService;
    }

    public List<SimpleLotteryTicket> getSimpleLotteryTickets()
    {
        return this.simpleLotteryTickets;
    }

    public SimpleLotteryTicketLine createRandomNumbersForLineOfThree() {
        final int numberOne = this.randomGeneratorService.generateRandomValue();
        final int numberTwo = this.randomGeneratorService.generateRandomValue();
        final int numberThree = this.randomGeneratorService.generateRandomValue();

        return new SimpleLotteryTicketLine(numberOne, numberTwo, numberThree);
    }

    public SimpleLotteryTicket createTicket(final int n)
    {
        final List<SimpleLotteryTicketLine> simpleLotteryTicketLines = new ArrayList<>();
        SimpleLotteryTicket simpleLotteryTicket = null;
        for(int i = 1; i<=n; i++)
        {
            simpleLotteryTicket = new SimpleLotteryTicket();
            final SimpleLotteryTicketLine simpleLotteryTicketLine = this.createRandomNumbersForLineOfThree();

            simpleLotteryTicketLines.add(simpleLotteryTicketLine);
        }

        simpleLotteryTicket.setLines(simpleLotteryTicketLines);

        this.simpleLotteryTickets.add(simpleLotteryTicket);

        return simpleLotteryTicket;
    }

    public List<SimpleLotteryTicket> getListOfTickets() throws SimpleLotteryServiceException {
        if (getSimpleLotteryTickets()== null) {
            final String errorMsg = "Could not retrieve ticket(s)";

            SimpleLotteryServiceImpl.logger.warn(errorMsg);
            throw new SimpleLotteryServiceException(errorMsg);
        }

        return getSimpleLotteryTickets();
    }

    public SimpleLotteryTicket getTicket(int ticketNumber) throws SimpleLotteryServiceException
    {
        SimpleLotteryTicket simpleLotteryTicket = null;
        try {
             simpleLotteryTicket = this.simpleLotteryTickets.get(ticketNumber);
        }
        catch(final Exception ex) {
            final String errorMsg = "Could not retrieve ticket";

            SimpleLotteryServiceImpl.logger.warn(errorMsg +" for ticket: "+ticketNumber);
            throw new SimpleLotteryServiceException(errorMsg);
        }
        return simpleLotteryTicket;
    }

    public SimpleLotteryTicket amendTicketLines(
            int ticketNumber, int additionalLines) throws SimpleLotteryServiceException
    {
        SimpleLotteryTicket simpleLotteryTicket = simpleLotteryTicket = this.getTicket(ticketNumber);

        // add new lines to ticket
        try{
            for(int i=0;i<additionalLines;i++) {
                final SimpleLotteryTicketLine simpleLotteryTicketLine = this.createRandomNumbersForLineOfThree();

                if(simpleLotteryTicket.getStatusChecked().equals(Boolean.FALSE))
                {
                    simpleLotteryTicket.getLines().add(simpleLotteryTicketLine);
                }
                else {
                    SimpleLotteryServiceImpl.logger.debug("Ticket locked for Status check!");
                    return simpleLotteryTicket;
                }
            }
        }
        catch(final Exception ex)
        {
            final String errorMsg = "Could add line to ticket";

            SimpleLotteryServiceImpl.logger.warn(errorMsg + "for {}" + ticketNumber);
            throw new SimpleLotteryServiceException(errorMsg);
        }

        return simpleLotteryTicket;
    }

    public SimpleLotteryTicket getTicketStatus(int ticketNumber) throws SimpleLotteryServiceException
    {
        // mark ticket as status checked
        final SimpleLotteryTicket simpleLotteryTicket = this.getListOfTickets().get(ticketNumber);

        // sort lines into outcomes
        final List<SimpleLotteryTicketLine> simpleLotteryTicketLines = simpleLotteryTicket.getLines();
        for (final SimpleLotteryTicketLine simpleLotteryTicketLine : simpleLotteryTicketLines) {
            final int lineValue = this.checkLineValues(simpleLotteryTicketLine);
            // set the tickets position
            simpleLotteryTicketLine.setOutcome(lineValue);
        }
        // Sort ascending 0..10
        Collections.sort(simpleLotteryTicketLines);

        simpleLotteryTicket.setStatusChecked(true);

        return simpleLotteryTicket;
    }

    /**
     * Assign lines, positions for sorting
     *
     * @param simpleLotteryTicketLine
     * @return
     */
    private int checkLineValues(SimpleLotteryTicketLine simpleLotteryTicketLine)
    {
        final int num1 = simpleLotteryTicketLine.getNumberOne();
        final int num2 = simpleLotteryTicketLine.getNumberTwo();
        final int num3 = simpleLotteryTicketLine.getNumberThree();

        final int sumLineValues = num1 + num2 + num3;

        if(sumLineValues == 2) {
            return 10;
        }
        else if ((num1 == num2) && (num2 == num3)) {
            return 5;
        }
        else if ((num1 != num2) && (num1 != num3)) {
            return 1;
        }
        else {
            return 0;
        }
    }
}
