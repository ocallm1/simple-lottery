package microservices.simple.lottery.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RandomGeneratorServiceTest
{

    @Autowired
    private RandomGeneratorService randomGeneratorService;

    @Test
    public void generateRandomNumbersAreBetweenExpectedLimits() throws Exception
    {
        // when a good sample of randomly generated factors is generated
        final List<Integer> randomNumbers = IntStream.range(0, 3).map(i -> this.randomGeneratorService.generateRandomValue()).boxed()
                .collect(Collectors.toList());

        assertThat(randomNumbers).size().isEqualTo(3);
    }

}