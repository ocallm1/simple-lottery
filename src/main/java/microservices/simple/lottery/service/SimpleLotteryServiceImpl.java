package microservices.simple.lottery.service;

import microservices.simple.lottery.domain.SimpleLotteryTicket;
import microservices.simple.lottery.domain.SimpleLotteryTicketLine;
import microservices.simple.lottery.service.Error.ServiceError;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
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
    public SimpleLotteryServiceImpl(RandomGeneratorService randomGeneratorService)
    {

        this.randomGeneratorService = randomGeneratorService;
    }

    private List<SimpleLotteryTicket> getSimpleLotteryTickets()
    {
        return simpleLotteryTickets;
    }

    public SimpleLotteryTicketLine createRandomNumbersForLineOfThree()
    {
        int numberOne = randomGeneratorService.generateRandomValue();
        int numberTwo = randomGeneratorService.generateRandomValue();
        int numberThree = randomGeneratorService.generateRandomValue();

        return new SimpleLotteryTicketLine(numberOne, numberTwo, numberThree);
    }

    public SimpleLotteryTicket createTicket(int n)
    {
        List<SimpleLotteryTicketLine> simpleLotteryTicketLines = new ArrayList<>();
        SimpleLotteryTicket simpleLotteryTicket = null;
        for (int i = 1; i <= n; i++)
        {
            simpleLotteryTicket = new SimpleLotteryTicket();
            SimpleLotteryTicketLine simpleLotteryTicketLine = createRandomNumbersForLineOfThree();

            simpleLotteryTicketLines.add(simpleLotteryTicketLine);
        }

        simpleLotteryTicket.setLines(simpleLotteryTicketLines);

        simpleLotteryTickets.add(simpleLotteryTicket);

        return simpleLotteryTicket;
    }

    public List<SimpleLotteryTicket> getListOfTickets() throws SimpleLotteryServiceException
    {
        if (null == this.getSimpleLotteryTickets() || !(this.getSimpleLotteryTickets().size() > 0))
        {
            String errorMsg = "Could not retrieve ticket(s)";
            logger.warn(errorMsg);

            final ServiceError error = new ServiceError(errorMsg);
            final SimpleLotteryTicket simpleLotterTicket = new SimpleLotteryTicket();
            final List<ServiceError> serviceError = new ArrayList<>();
            serviceError.add(error);

            simpleLotterTicket.setErrors(serviceError);

            throw new SimpleLotteryServiceException(errorMsg);
        }

        return this.getSimpleLotteryTickets();
    }

    public SimpleLotteryTicket getTicket(final int ticketNumber) throws SimpleLotteryServiceException
    {
        SimpleLotteryTicket simpleLotteryTicket = null;
        final List<ServiceError> serviceError = new ArrayList<>();
        try
        {
            simpleLotteryTicket = simpleLotteryTickets.get(ticketNumber);
        }
        catch (Exception ex)
        {
            String errorMsg = "Could not retrieve ticket";

            logger.warn(errorMsg + " for ticket: " + ticketNumber);
            throw new SimpleLotteryServiceException(errorMsg);
        }
        return simpleLotteryTicket;
    }

    public SimpleLotteryTicket amendTicketLines(final int ticketNumber, final int additionalLines) throws SimpleLotteryServiceException
    {
        SimpleLotteryTicket simpleLotteryTicket = simpleLotteryTicket = getTicket(ticketNumber);

        // add new lines to ticket
//        try
//        {
        final int[] A = new int[0];

        Arrays.sort(A);

        String errorMsg = "Could not add line to ticket";
        final List<ServiceError> serviceError = new ArrayList<>();
        if (simpleLotteryTicket!=null) {

            if (additionalLines>0)
            {
                for (int i = 0; i < additionalLines; i++)
                {
                    SimpleLotteryTicketLine simpleLotteryTicketLine = createRandomNumbersForLineOfThree();

                    if (simpleLotteryTicket.getStatusChecked().equals(Boolean.FALSE))
                    {
                        simpleLotteryTicket.getLines().add(simpleLotteryTicketLine);
                    }
                    else
                    {
                        logger.debug("Ticket locked for Status check!");
                        return simpleLotteryTicket;
                    }
                }
            }
            else {
                final ServiceError error = new ServiceError(errorMsg + " Please add >0 additonal lines" );
                final SimpleLotteryTicket simpleLotterTicket = new SimpleLotteryTicket();
                serviceError.add(error);
            }
        }
        else {
            final ServiceError error = new ServiceError(errorMsg + "Ticket Number did not exist" );
            final SimpleLotteryTicket simpleLotterTicket = new SimpleLotteryTicket();
            serviceError.add(error);
        }
//        catch (final Exception ex)
//        {
//            final String errorMsg = "Could add line to ticket";
//
//            SimpleLotteryServiceImpl.logger.warn(errorMsg + "for {}" + ticketNumber);
//            throw new SimpleLotteryServiceException(errorMsg);
//        }

        return simpleLotteryTicket;
    }

    public SimpleLotteryTicket getTicketStatus(final int ticketNumber) throws SimpleLotteryServiceException
    {
        // mark ticket as status checked
        SimpleLotteryTicket simpleLotteryTicket = getListOfTickets().get(ticketNumber);

        // sort lines into outcomes
        List<SimpleLotteryTicketLine> simpleLotteryTicketLines = simpleLotteryTicket.getLines();
        for (SimpleLotteryTicketLine simpleLotteryTicketLine : simpleLotteryTicketLines)
        {
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

        if (sumLineValues == 2)
        {
            return 10;
        }
        else if ((num1 == num2) && (num2 == num3))
        {
            return 5;
        }
        else if ((num1 != num2) && (num1 != num3))
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }
}
