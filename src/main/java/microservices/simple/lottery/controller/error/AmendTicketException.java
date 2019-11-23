package microservices.simple.lottery.controller.error;

public class AmendTicketException extends RuntimeException {

    public AmendTicketException(Integer id) {
        super("Cannot Amend Ticket id: " + id);
    }

}
