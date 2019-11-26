package microservices.simple.lottery.service;

import microservices.simple.lottery.domain.SimpleLotteryTicket;
import microservices.simple.lottery.domain.SimpleLotteryTicketLine;
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
    static final Logger logger = Logger.getLogger(SimpleLotteryServiceImplTest.class);

    @Mock
    private SimpleLotteryServiceImpl simpleLotteryService;

    @Mock
    private RandomGeneratorService randomGeneratorService;

    @Before
    public void setUp()
    {
        // With this call to initMocks we tell Mockito to process the annotations
        MockitoAnnotations.initMocks(this);
        this.simpleLotteryService = new SimpleLotteryServiceImpl(this.randomGeneratorService);
    }

    @Test
    public void createRandomRowTest()
    {
        // given (our mocked Random Generator service will return first 2, then 1)
        given(this.randomGeneratorService.generateRandomValue()).willReturn(2, 1);

        // when
        final SimpleLotteryTicketLine simpleLotteryTicketLine = this.simpleLotteryService.createRandomNumbersForLineOfThree();
    }

    ////////////////////////////////////
    // test with real data
    @Test
    public void createTicketsTest()
    {
        final RandomGeneratorServiceImpl randomGeneratorServiceImpl = new RandomGeneratorServiceImpl();
        final SimpleLotteryServiceImpl simpleLotteryService = new SimpleLotteryServiceImpl(randomGeneratorServiceImpl);

        // create a ticket with five lines
        final SimpleLotteryTicket simpleLotteryTicket = simpleLotteryService.createTicket(5);
        final List<SimpleLotteryTicketLine> simpleLotteryTicketLine = simpleLotteryTicket.getLines();

        printTicketLines(simpleLotteryTicketLine, 4);

        Assert.assertTrue(simpleLotteryTicket.getLines().size() > 4);
        Assert.assertTrue(simpleLotteryTicketLine.get(4).getNumberThree() >= 0);
        Assert.assertTrue(simpleLotteryTicketLine.get(4).getNumberThree() <= 2);

        // create a second ticket with three lines
        final SimpleLotteryTicket simpleLotteryTicketTwo = simpleLotteryService.createTicket(3);
        final List<SimpleLotteryTicketLine> simpleLotteryTicketTwoLines = simpleLotteryTicketTwo.getLines();

        printTicketLines(simpleLotteryTicketTwoLines, 2);

        Assert.assertTrue(simpleLotteryTicketTwo.getLines().size() > 2);
        Assert.assertTrue(simpleLotteryTicketTwoLines.get(2).getNumberThree() >= 0);
        Assert.assertTrue(simpleLotteryTicketTwoLines.get(2).getNumberThree() <= 2);
    }

    @Test
    public void getTicketStatus()
    {
        final RandomGeneratorServiceImpl randomGeneratorServiceImpl = new RandomGeneratorServiceImpl();
        final SimpleLotteryServiceImpl simpleLotteryService = new SimpleLotteryServiceImpl(randomGeneratorServiceImpl);

        // create a ticket with five lines
        simpleLotteryService.createTicket(10);

        SimpleLotteryTicket simpleLotteryTicketOne = null;
        try
        {
            simpleLotteryTicketOne = simpleLotteryService.getTicketStatus(0);
        }
        catch (final SimpleLotteryServiceException e)
        {
            logger.error("Proglem getting ticket status" + e);
        }
        for (final SimpleLotteryTicketLine simpleLotteryTicketCheck : simpleLotteryTicketOne.getLines())
        {
            SimpleLotteryServiceImplTest.logger.info("simpleLotteryTicketCheck {}" + simpleLotteryTicketCheck);
        }

        // create another ticket
        simpleLotteryService.createTicket(5);

        SimpleLotteryTicket simpleLotteryTicketTwo = null;
        try
        {
            simpleLotteryTicketTwo = simpleLotteryService.getTicketStatus(1);
        }
        catch (final SimpleLotteryServiceException e)
        {
            logger.error("Proglem getting ticket status" + e);
        }
        for (final SimpleLotteryTicketLine simpleLotteryTicketCheck : simpleLotteryTicketTwo.getLines())
        {
            SimpleLotteryServiceImplTest.logger.info("simpleLotteryTicketCheck {}" + simpleLotteryTicketCheck);
        }

        Assert.assertNotNull(simpleLotteryTicketTwo);
        Assert.assertTrue(simpleLotteryTicketTwo.getStatusChecked());
    }

    @Test
    public void amendTicketLines()
    {
        // create ticket
        final RandomGeneratorServiceImpl randomGeneratorServiceImpl = new RandomGeneratorServiceImpl();
        final SimpleLotteryServiceImpl simpleLotteryService = new SimpleLotteryServiceImpl(randomGeneratorServiceImpl);

        // create a ticket with 3 lines
        simpleLotteryService.createTicket(3);

        // amend ticket
        SimpleLotteryTicket simpleLotteryTicket = null;
        try
        {
            simpleLotteryTicket = simpleLotteryService.amendTicketLines(0, 3);
        }
        catch (final SimpleLotteryServiceException e)
        {
            logger.error("Proglem amending ticket" + e);
        }
        Assert.assertEquals(simpleLotteryTicket.getLines().size(), 6);

        // check status of ticket
        try
        {
            final SimpleLotteryTicket simpleLotteryTicketTwo = simpleLotteryService.getTicketStatus(0);
        }
        catch (final SimpleLotteryServiceException e)
        {
            logger.error("Proglem getting ticket status" + e);
        }

        // now try amending the ticket it should not be possible.
        SimpleLotteryTicket simpleLotteryTicketSame = null;
        try
        {
            simpleLotteryTicketSame = simpleLotteryService.amendTicketLines(0, 3);
        }
        catch (final SimpleLotteryServiceException e)
        {
            logger.error("Proglem amending ticket" + e);
        }
        Assert.assertEquals(simpleLotteryTicketSame.getLines().size(), 6);
    }

    private void printTicketLines(final List<SimpleLotteryTicketLine> simpleLotteryTicketLine, final int i)
    {
        SimpleLotteryServiceImplTest.logger
                .info("simpleLotteryTicketLine.get(0).getNumberOne() {}" + simpleLotteryTicketLine.get(0).getNumberOne());
        SimpleLotteryServiceImplTest.logger
                .info("simpleLotteryTicketLine.get(0).getNumberTwo() {}" + simpleLotteryTicketLine.get(0).getNumberTwo());
        SimpleLotteryServiceImplTest.logger
                .info("simpleLotteryTicketLine.get(0).getNumberThree() {}" + simpleLotteryTicketLine.get(0).getNumberThree());

        SimpleLotteryServiceImplTest.logger
                .info("simpleLotteryTicketLine.get(4).getNumberOne() {}" + simpleLotteryTicketLine.get(i).getNumberOne());
        SimpleLotteryServiceImplTest.logger
                .info("simpleLotteryTicketLine.get(4).getNumberTwo() {}" + simpleLotteryTicketLine.get(i).getNumberTwo());
        SimpleLotteryServiceImplTest.logger
                .info("simpleLotteryTicketLine.get(4).getNumberThree() {}" + simpleLotteryTicketLine.get(i).getNumberThree());
    }

}