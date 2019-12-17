package microservices.simple.lottery.controller;

import microservices.simple.SimpleLotteryApplication;
import microservices.simple.lottery.domain.SimpleLotteryTicket;
import microservices.simple.lottery.domain.SimpleLotteryTicketLine;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

// TODO Complete Integration Tests
@SpringBootTest(classes = SimpleLotteryApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SimpleLotteryIntegrationTest
{
    @LocalServerPort
    private final int port = 8080;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    @Ignore("Test is ignored as a demonstration")
    @Test
    public void testCreateTicket()
    {
        List<SimpleLotteryTicketLine> lines = new ArrayList<>();
        SimpleLotteryTicketLine simpleLotteryTicketLine = new SimpleLotteryTicketLine(1, 2, 0);
        lines.add(simpleLotteryTicketLine);

        SimpleLotteryTicket simpleLotteryTicket = new SimpleLotteryTicket(lines);
        simpleLotteryTicket.setStatusChecked(false);

        ResponseEntity<SimpleLotteryTicket> responseEntity = this.restTemplate
                .postForEntity("http://localhost:" + port + "/lottery/ticket", null, SimpleLotteryTicket.class);
        assertEquals(201, responseEntity.getStatusCodeValue());
    }

    @Ignore("Test is ignored as a demonstration")
    @Test
    public void testRetrieveTicket()
    {

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<SimpleLotteryTicket> response = restTemplate
                .getForEntity("http://localhost:8080/lottery/ticket/1", SimpleLotteryTicket.class);
        assertEquals(201, response.getStatusCodeValue());
    }

    private String createURLWithPort(String uri)
    {
        return "http://localhost:" + port + uri;
    }

    @Ignore("Test is ignored as a demonstration")
    @Test
    public void testGetAllTicketss()
    {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<SimpleLotteryTicket> entity = new HttpEntity<SimpleLotteryTicket>(null, headers);
        //        ResponseEntity<SimpleLotteryTicket> response = restTemplate.exchange("http://localhost:8080/lottery/ticket/",
        //                HttpMethod.GET, entity, SimpleLotteryTicket.class);

        SimpleLotteryTicket simpleLotteryTicket = restTemplate
                .getForObject("http://localhost:8080/lottery/ticket/0", SimpleLotteryTicket.class);
        System.out.println(simpleLotteryTicket.getStatusChecked());

        //   assertNotNull(response.getBody());
    }
}