package microservices.simple.lottery.service;

import microservices.simple.lottery.domain.SimpleLotteryTicketLine;
import microservices.simple.lottery.domain.SimpleLotteryTicket;

import java.util.List;

public interface SimpleLotteryService
{

    /**
     * Creates a SimpleLotteryTicketLine object with three randomly-generated factors
     * between 0 and 2.
     *
     * @return a SimpleLotteryTicketLine object with random factors
     */
    SimpleLotteryTicketLine createRandomNumbersForLineOfThree();

    /**
     * create a ticket of size n
     *
     * @param n
     * @return SimpleLotteryTicket
     */
    SimpleLotteryTicket createTicket(int n);

    /**
     * Get a list of all tickets from Main Memory
     *
     * @return List<SimpleLotteryTicket>
     */
    List<SimpleLotteryTicket> getListOfTickets() throws SimpleLotteryServiceException;

    /**
     * Get individual ticket based on a ticket number
     *
     * @param ticketNumber
     * @return SimpleLotteryTicket
     */
    SimpleLotteryTicket getTicket(int ticketNumber) throws SimpleLotteryServiceException;

    /**
     * Amend ticket lines
     * It should be possible for a ticket to be amended with n
     * additional lines.
     * Once the status of a ticket has been checked it should not be possible to
     * amend.
     *
     * @param ticketNumber
     * @param additionalLines
     * @return
     */
    SimpleLotteryTicket amendTicketLines(int ticketNumber, int additionalLines) throws SimpleLotteryServiceException;

    /**
     * Retrieve status of ticket
     * The lines will be sorted into outcomes before being returned
     *
     * Outcomes:
     * if the sum of the values on a line is 2, the result for that line is 10.
     * Otherwise if they are all the same, the result is 5.
     * Otherwise so long as both 2nd and 3rd numbers are different from the 1st, the result is 1.
     * Otherwise the result is 0
     *
     * @param ticketNumber
     * @return SimpleLotteryTicket
     */
    SimpleLotteryTicket getTicketStatus(int ticketNumber) throws SimpleLotteryServiceException;


}
