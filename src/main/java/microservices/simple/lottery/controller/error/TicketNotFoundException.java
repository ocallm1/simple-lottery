package microservices.simple.lottery.controller.error;

public class TicketNotFoundException extends RuntimeException
{

    public TicketNotFoundException(final Integer id)
    {
        super("Ticket id not found : " + id);
    }

    public TicketNotFoundException()
    {
        super("Ticket(s) not found : ");
    }

}
