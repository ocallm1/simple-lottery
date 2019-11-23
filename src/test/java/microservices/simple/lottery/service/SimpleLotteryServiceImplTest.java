package microservices.simple.lottery.service;

import microservices.simple.lottery.domain.SimpleLotteryTicketLine;
import microservices.simple.lottery.domain.SimpleLotteryTicket;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.BDDMockito.given;

public class SimpleLotteryServiceImplTest
{
    final static Logger logger = Logger.getLogger(SimpleLotteryServiceImplTest.class);

    @Mock
    private SimpleLotteryServiceImpl simpleLotteryService;

    @Mock
    private RandomGeneratorService randomGeneratorService;

    @Before
    public void setUp()
    {
        // With this call to initMocks we tell Mockito to process the annotations
        MockitoAnnotations.initMocks(this);
        simpleLotteryService = new SimpleLotteryServiceImpl(randomGeneratorService);
    }

    @Test
    public void createRandomRowTest()
    {
        // given (our mocked Random Generator service will return first 2, then 1)
        given(randomGeneratorService.generateRandomValue()).willReturn(2, 1);

        // when
        SimpleLotteryTicketLine simpleLotteryTicketLine = simpleLotteryService.createRandomNumbersForLineOfThree();
    }

    ////////////////////////////////////
    // test with real data
    @Test
    public void createTicketsTest()
    {
        RandomGeneratorServiceImpl randomGeneratorServiceImpl = new RandomGeneratorServiceImpl();
        SimpleLotteryServiceImpl simpleLotteryService = new SimpleLotteryServiceImpl(randomGeneratorServiceImpl);

        // create a ticket with five lines
        SimpleLotteryTicket simpleLotteryTicket = simpleLotteryService.createTicket(5);
        List<SimpleLotteryTicketLine> simpleLotteryTicketLine = simpleLotteryTicket.getLines();

        logger.info("simpleLotteryTicketLine.get(0).getNumberOne() {}" + simpleLotteryTicketLine.get(0).getNumberOne());
        logger.info("simpleLotteryTicketLine.get(0).getNumberTwo() {}" + simpleLotteryTicketLine.get(0).getNumberTwo());
        logger.info("simpleLotteryTicketLine.get(0).getNumberThree() {}" + simpleLotteryTicketLine.get(0).getNumberThree());

        logger.info("simpleLotteryTicketLine.get(4).getNumberOne() {}" + simpleLotteryTicketLine.get(4).getNumberOne());
        logger.info("simpleLotteryTicketLine.get(4).getNumberTwo() {}" + simpleLotteryTicketLine.get(4).getNumberTwo());
        logger.info("simpleLotteryTicketLine.get(4).getNumberThree() {}" + simpleLotteryTicketLine.get(4).getNumberThree());

        Assert.assertTrue(simpleLotteryTicket.getLines().size() > 4);
        Assert.assertTrue(simpleLotteryTicketLine.get(4).getNumberThree() >= 0);
        Assert.assertTrue(simpleLotteryTicketLine.get(4).getNumberThree() <= 2);

        // create a second ticket with three lines
        SimpleLotteryTicket simpleLotteryTicketTwo = simpleLotteryService.createTicket(3);
        List<SimpleLotteryTicketLine> simpleLotteryTicketTwoLines = simpleLotteryTicketTwo.getLines();

        logger.info("simpleLotteryTicketLine.get(0).getNumberOne() {}" + simpleLotteryTicketTwoLines.get(0).getNumberOne());
        logger.info("simpleLotteryTicketLine.get(0).getNumberTwo() {}" + simpleLotteryTicketTwoLines.get(0).getNumberTwo());
        logger.info("simpleLotteryTicketLine.get(0).getNumberThree() {}" + simpleLotteryTicketTwoLines.get(0).getNumberThree());

        logger.info("simpleLotteryTicketLine.get(4).getNumberOne() {}" + simpleLotteryTicketTwoLines.get(2).getNumberOne());
        logger.info("simpleLotteryTicketLine.get(4).getNumberTwo() {}" + simpleLotteryTicketTwoLines.get(2).getNumberTwo());
        logger.info("simpleLotteryTicketLine.get(4).getNumberThree() {}" + simpleLotteryTicketTwoLines.get(2).getNumberThree());

        Assert.assertTrue(simpleLotteryTicketTwo.getLines().size() > 2);
        Assert.assertTrue(simpleLotteryTicketTwoLines.get(2).getNumberThree() >= 0);
        Assert.assertTrue(simpleLotteryTicketTwoLines.get(2).getNumberThree() <= 2);
    }

    @Test
    public void getTicketStatus()
    {
        RandomGeneratorServiceImpl randomGeneratorServiceImpl = new RandomGeneratorServiceImpl();
        SimpleLotteryServiceImpl simpleLotteryService = new SimpleLotteryServiceImpl(randomGeneratorServiceImpl);

        // create a ticket with five lines
        simpleLotteryService.createTicket(10);

        SimpleLotteryTicket simpleLotteryTicketOne = null;
        try
        {
            simpleLotteryTicketOne = simpleLotteryService.getTicketStatus(0);
        }
        catch (SimpleLotteryServiceException e)
        {
            e.printStackTrace();
        }
        for (SimpleLotteryTicketLine simpleLotteryTicketCheck : simpleLotteryTicketOne.getLines())
        {
            logger.info("simpleLotteryTicketCheck {}" + simpleLotteryTicketCheck.toString());
        }

        // create another ticket
        simpleLotteryService.createTicket(5);

        SimpleLotteryTicket simpleLotteryTicketTwo = null;
        try
        {
            simpleLotteryTicketTwo = simpleLotteryService.getTicketStatus(1);
        }
        catch (SimpleLotteryServiceException e)
        {
            e.printStackTrace();
        }
        for (SimpleLotteryTicketLine simpleLotteryTicketCheck : simpleLotteryTicketTwo.getLines())
        {
            logger.info("simpleLotteryTicketCheck {}" + simpleLotteryTicketCheck.toString());
        }

        Assert.assertNotNull(simpleLotteryTicketTwo);
        Assert.assertTrue(simpleLotteryTicketTwo.getStatusChecked());
    }

    @Test
    public void amendTicketLines()
    {
        // create ticket
        RandomGeneratorServiceImpl randomGeneratorServiceImpl = new RandomGeneratorServiceImpl();
        SimpleLotteryServiceImpl simpleLotteryService = new SimpleLotteryServiceImpl(randomGeneratorServiceImpl);

        // create a ticket with 3 lines
        simpleLotteryService.createTicket(3);

        // amend ticket
        SimpleLotteryTicket simpleLotteryTicket = null;
        try
        {
            simpleLotteryTicket = simpleLotteryService.amendTicketLines(0,  3);
        }
        catch (SimpleLotteryServiceException e)
        {
            e.printStackTrace();
        }
        Assert.assertEquals(simpleLotteryTicket.getLines().size(), 6);

        // check status of ticket
        try
        {
            SimpleLotteryTicket simpleLotteryTicketTwo = simpleLotteryService.getTicketStatus(0);
        }
        catch (SimpleLotteryServiceException e)
        {
            e.printStackTrace();
        }

        // now try amending the ticket it should not be possible.
        SimpleLotteryTicket simpleLotteryTicketSame = null;
        try
        {
            simpleLotteryTicketSame = simpleLotteryService.amendTicketLines(0,  3);
        }
        catch (SimpleLotteryServiceException e)
        {
            e.printStackTrace();
        }
        Assert.assertEquals(simpleLotteryTicketSame.getLines().size(), 6);
    }

}