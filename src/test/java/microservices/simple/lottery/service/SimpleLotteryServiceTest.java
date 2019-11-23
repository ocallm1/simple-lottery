package microservices.simple.lottery.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SimpleLotteryServiceTest {

    @MockBean
    private RandomGeneratorService randomGeneratorService;

    @Autowired
    private SimpleLotteryService simpleLotteryService;

    @Test
    public void createRandomValuesTest() {
        // given (our mocked Random Generator service will return first 2, then 1)
        given(randomGeneratorService.generateRandomValue()).willReturn(2, 1);

        // when

//        simpleLotteryService.createRandomNumbersForLineOfThree();

    }
}