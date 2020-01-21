package microservices.simple.lottery.controller;

import microservices.simple.lottery.controller.error.AmendTicketException;
import microservices.simple.lottery.controller.error.CheckTicketException;
import microservices.simple.lottery.controller.error.TicketNotFoundException;
import microservices.simple.lottery.domain.SimpleLotteryTicket;
import microservices.simple.lottery.domain.SimpleLotteryTicketLine;
import microservices.simple.lottery.service.SimpleLotteryService;
import microservices.simple.lottery.service.SimpleLotteryServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by ocallm1 on 18/12/19.
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/lottery")
public class SimpleLotteryController
{
    private static final Logger               logger = Logger.getLogger(SimpleLotteryController.class);
    private final        SimpleLotteryService simpleLotteryService;

    @Autowired
    public SimpleLotteryController(SimpleLotteryService simpleLotteryService)
    {
        this.simpleLotteryService = simpleLotteryService;
    }

    /**
     * Create an in memory ticket with 10 lines of data
     * <p>
     * Each line is made up of three rows
     * NOTE: A param could be used to pass in a variab
     * le number of rows but rows can be added via
     * amendTicket.
     * <p>
     * Note also no body of HTTP Post is required
     *
     * @return ResponseEntity containing new ticket, not sorted yet and status of 201 if created ok.
     */
    @PostMapping("/ticket")
    @ResponseBody
    public ResponseEntity<?> createTicket()
    {
        // lets just always create a ticket with 10 lines
        final SimpleLotteryTicket simpleLotteryServiceTicket = simpleLotteryService.createTicket(10);

        return new ResponseEntity<SimpleLotteryTicket>(simpleLotteryServiceTicket, HttpStatus.CREATED);
    }

    /**
     * @return ResponseEntity - A list of the created tickets with their rows and a status of 200 if the request succeeds.
     */
    @RequestMapping(value = "/ticket", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getTickets()
    {
        List<SimpleLotteryTicket> simpleLotteryServiceTickets = null;
        try
        {
            simpleLotteryServiceTickets = simpleLotteryService.getListOfTickets();
            logger.info("Getting all tickets");
        }
        catch (SimpleLotteryServiceException e)
        {
            logger.error("could not retrieve ticket(s)");
            throw new TicketNotFoundException();
        }

        return new ResponseEntity<List<SimpleLotteryTicket>>(simpleLotteryServiceTickets, HttpStatus.OK);
    }

    /**
     * Accepts an id as a basic integer for the number of the ticket and returns the requested ticket.
     *
     * @return ResponseEntity - A single ticket with its rows and a status of 200 if the request succeeds.
     */
   // @GetMapping("/ticket/{id}")
    @RequestMapping(value = "/ticket/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getTicket(@PathVariable int id)
    {
        SimpleLotteryTicket simpleLotteryServiceTicket = null;
        try
        {
            simpleLotteryServiceTicket = simpleLotteryService.getTicket(id);
            logger.info("Getting a single ticket for id: "+id );
        }
        catch (SimpleLotteryServiceException e)
        {
            logger.error("could not retrieve ticket");
            throw new TicketNotFoundException(id);
        }

        return new ResponseEntity<SimpleLotteryTicket>(simpleLotteryServiceTicket, HttpStatus.OK);
    }

    /**
     * @return ResponseEntity - A list of lines from selected ticket
     */
    @RequestMapping(value = "/ticket/{id}/lines", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getTicketLines(@PathVariable int id)
    {
        List<SimpleLotteryTicketLine> simpleLotteryServiceTicketLines = null;

        SimpleLotteryTicket simpleLotteryServiceTicket = null;
        try
        {
            simpleLotteryServiceTicket = simpleLotteryService.getTicket(id);

            logger.info("Getting ticket lines for ticket id: "+id );
        }
        catch (SimpleLotteryServiceException e)
        {
            logger.error("could not retrieve ticket");
            throw new TicketNotFoundException(id);
        }

        return new ResponseEntity<List<SimpleLotteryTicketLine>>(simpleLotteryServiceTicket.getLines(), HttpStatus.OK);
    }

    /**
     * Note we pick a ticket to update, we could also perhaps add a method to choose both ticket and
     * number of additional extra lines but for now we will impose 6 new lines.
     * <p>
     * Once the status of a ticket has been checked it should not be possible to amend, so in this case we will
     * just return the original ticket and its field: "statusChecked": true.
     *
     * @param id of ticket chosen as an integer (0..n-1)
     * @return ResponseEntity - Ticket with 10+6 new lines and 201 CREATED
     * OR
     * original 10 lines if ticket was checked and status of 200 OK.
     */
    @PutMapping("/ticket/{id}")
    @ResponseBody
    public ResponseEntity<?> amendTicket(@PathVariable int id)
    {
        // lets just always update a chosen ticket with 6 lines
        SimpleLotteryTicket simpleLotteryServiceTicket = null;
        try
        {
            simpleLotteryServiceTicket = simpleLotteryService.amendTicketLines(id, 6);
        }
        catch (SimpleLotteryServiceException e)
        {
            logger.error("could not amend ticket: " + id);
            throw new AmendTicketException(id);
        }

        if (simpleLotteryServiceTicket.getLines().size() > 10)
        {
            return new ResponseEntity<SimpleLotteryTicket>(simpleLotteryServiceTicket, HttpStatus.CREATED);
        }
        else
        {
            return new ResponseEntity<SimpleLotteryTicket>(simpleLotteryServiceTicket, HttpStatus.OK);
        }
    }

    /**
     * Checks the status of a ticket based on an integer id passed in
     * and sorts the lines of the ticket into outcomes from lowest to highest
     * and marks the ticket as read
     * <p>
     * Note we could parameter check with a ? to perhaps sort in descending order also
     *
     * @param id
     * @return ResponseEntity - Ordered into outcomes in ascending order.
     */
    @PutMapping("/status/{id}")
    @ResponseBody
    public ResponseEntity<?> status(@PathVariable int id)
    {
        SimpleLotteryTicket simpleLotteryServiceTicket = null;
        try
        {
            simpleLotteryServiceTicket = simpleLotteryService.getTicketStatus(id);
        }
        catch (SimpleLotteryServiceException sLSE)
        {
            throw new CheckTicketException(id);
        }

        return new ResponseEntity<SimpleLotteryTicket>(simpleLotteryServiceTicket, HttpStatus.CREATED);
    }

}
