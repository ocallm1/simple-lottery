package microservices.simple.lottery.controller.error;

public class CheckTicketException extends RuntimeException
{
    public CheckTicketException(int id)
    {
        super("Cannot Check Ticket status id: " + id);
    }
}
