package microservices.simple.lottery.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import microservices.simple.lottery.domain.SimpleLotteryTicket;
import microservices.simple.lottery.domain.SimpleLotteryTicketLine;
import microservices.simple.lottery.service.SimpleLotteryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.boot.test.json.JacksonTester.initFields;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@RunWith(SpringRunner.class)
@WebMvcTest(SimpleLotteryController.class)
public class SimpleLotteryControllerTest
{
    @MockBean
    private SimpleLotteryService simpleLotteryService;

    @Autowired
    private MockMvc mvc;

    private JacksonTester<SimpleLotteryTicketLine> json;

    // This object will be magically initialized by the initFields method below.
    private JacksonTester<SimpleLotteryTicket> jsonResult;
    private JacksonTester<SimpleLotteryTicket> jsonResponse;

    @Before
    public void setup() {
        initFields(this, new ObjectMapper());
    }

    @Test
    public void getRandomLotteryTest() throws Exception {
        //given
        given(simpleLotteryService.createRandomNumbersForLineOfThree()).
                willReturn(new SimpleLotteryTicketLine(1,0, 2));
    }

    @Test
    public void postResultReturnCorrect() throws Exception {
        SimpleLotteryTicket simpleLotteryTicket = new SimpleLotteryTicket();
        genericParameterizedTest(simpleLotteryTicket);
    }

    void genericParameterizedTest(final SimpleLotteryTicket simpleLotteryTicket) throws Exception {
        given(simpleLotteryService
                .createTicket(1))
                .willReturn(simpleLotteryTicket);

        SimpleLotteryTicket simpleLotteryTicketAttempt = new SimpleLotteryTicket();
        simpleLotteryTicketAttempt.setLines(null);
        simpleLotteryTicketAttempt.setStatusChecked(true);

        // when
        MockHttpServletResponse response =
                mvc.perform(post("/lottery/ticket")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(jsonResult.write(simpleLotteryTicketAttempt).getJson()))
                .andReturn().getResponse();
        //                .content(jsonResult.write(simpleLotteryTicket).getJson())

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    public void putResultReturnInCorrect() throws Exception {
        SimpleLotteryTicket simpleLotteryTicket = new SimpleLotteryTicket();

        given(simpleLotteryService
                .amendTicketLines(1,2))
                .willReturn(simpleLotteryTicket);

        SimpleLotteryTicket simpleLotteryTicketAttempt = new SimpleLotteryTicket();
        simpleLotteryTicketAttempt.setLines(null);
        simpleLotteryTicketAttempt.setStatusChecked(true);

        // Won't be found as no Ticket with Index 1 Created
        int id = 1;
        MockHttpServletRequestBuilder builder =
                put("/articles/" + id)
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(jsonResult.write(simpleLotteryTicketAttempt).getJson());

        this.mvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }



}
