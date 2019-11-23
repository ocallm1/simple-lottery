package microservices.simple.lottery.controller.error;

public class AmendTicketException extends RuntimeException {

    private static final long serialVersionUID = -875975093137686962L;

    public AmendTicketException(final Integer id) {
        super("Cannot Amend Ticket id: " + id);
    }

}
