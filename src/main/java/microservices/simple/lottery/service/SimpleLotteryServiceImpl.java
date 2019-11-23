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
    final static Logger logger = Logger.getLogger(SimpleLotteryServiceImpl.class);

    private RandomGeneratorService randomGeneratorService;

    // Note tickets stored in memory would be database backed in a real system
    private List<SimpleLotteryTicket> simpleLotteryTickets = new ArrayList<>();


    @Autowired
    public SimpleLotteryServiceImpl(RandomGeneratorService randomGeneratorService) {

        this.randomGeneratorService = randomGeneratorService;
    }

    public List<SimpleLotteryTicket> getSimpleLotteryTickets()
    {
        return simpleLotteryTickets;
    }

    @Override
    public SimpleLotteryTicketLine createRandomNumbersForLineOfThree() {
        int numberOne = randomGeneratorService.generateRandomValue();
        int numberTwo = randomGeneratorService.generateRandomValue();
        int numberThree = randomGeneratorService.generateRandomValue();

        return new SimpleLotteryTicketLine(numberOne, numberTwo, numberThree);
    }

    @Override
    public SimpleLotteryTicket createTicket(int n)
    {
        List<SimpleLotteryTicketLine> simpleLotteryTicketLines = new ArrayList<>();
        SimpleLotteryTicket simpleLotteryTicket = null;
        for(int i = 1; i<=n; i++)
        {
            simpleLotteryTicket = new SimpleLotteryTicket();
            SimpleLotteryTicketLine simpleLotteryTicketLine = createRandomNumbersForLineOfThree();

            simpleLotteryTicketLines.add(simpleLotteryTicketLine);
        }

        simpleLotteryTicket.setLines(simpleLotteryTicketLines);

        simpleLotteryTickets.add(simpleLotteryTicket);

        return simpleLotteryTicket;
    }

    @Override
    public List<SimpleLotteryTicket> getListOfTickets() throws SimpleLotteryServiceException {
        if (this.getSimpleLotteryTickets()== null) {
            String errorMsg = "Could not retrieve ticket(s)";

            logger.warn(errorMsg);
            throw new SimpleLotteryServiceException(errorMsg);
        }

        return this.getSimpleLotteryTickets();
    }

    @Override
    public SimpleLotteryTicket getTicket(final int ticketNumber) throws SimpleLotteryServiceException
    {
        SimpleLotteryTicket simpleLotteryTicket = null;
        try {
             simpleLotteryTicket = simpleLotteryTickets.get(ticketNumber);
        }
        catch(Exception ex) {
            String errorMsg = "Could not retrieve ticket";

            logger.warn(errorMsg +" for ticket: "+ticketNumber);
            throw new SimpleLotteryServiceException(errorMsg);
        }
        return simpleLotteryTicket;
    }

    @Override
    public SimpleLotteryTicket amendTicketLines(
            final int ticketNumber, final int additionalLines) throws SimpleLotteryServiceException
    {
        SimpleLotteryTicket simpleLotteryTicket = simpleLotteryTicket = getTicket(ticketNumber);

        // add new lines to ticket
        try{
            for(int i=0;i<additionalLines;i++) {
                SimpleLotteryTicketLine simpleLotteryTicketLine = createRandomNumbersForLineOfThree();

                if(simpleLotteryTicket.getStatusChecked().equals(Boolean.FALSE))
                {
                    simpleLotteryTicket.getLines().add(simpleLotteryTicketLine);
                }
                else {
                    logger.debug("Ticket locked for Status check!");
                    return simpleLotteryTicket;
                }
            }
        }
        catch(Exception ex)
        {
            String errorMsg = "Could add line to ticket";

            logger.warn(errorMsg + "for {}" + ticketNumber);
            throw new SimpleLotteryServiceException(errorMsg);
        }

        return simpleLotteryTicket;
    }

    @Override
    public SimpleLotteryTicket getTicketStatus(final int ticketNumber) throws SimpleLotteryServiceException
    {
        // mark ticket as status checked
        SimpleLotteryTicket simpleLotteryTicket = getListOfTickets().get(ticketNumber);

        // sort lines into outcomes
        List<SimpleLotteryTicketLine> simpleLotteryTicketLines = simpleLotteryTicket.getLines();
        for (SimpleLotteryTicketLine simpleLotteryTicketLine : simpleLotteryTicketLines) {
            int lineValue = checkLineValues(simpleLotteryTicketLine);
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
    private int checkLineValues(final SimpleLotteryTicketLine simpleLotteryTicketLine)
    {
        int num1 = simpleLotteryTicketLine.getNumberOne();
        int num2 = simpleLotteryTicketLine.getNumberTwo();
        int num3 = simpleLotteryTicketLine.getNumberThree();

        int sumLineValues = num1 + num2 + num3;

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
