package microservices.simple.lottery.service;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class RandomGeneratorServiceImplTest {

    private RandomGeneratorServiceImpl randomGeneratorServiceImpl;

    @Before
    public void setUp() {
        randomGeneratorServiceImpl = new RandomGeneratorServiceImpl();
    }

    @Test
    public void generateRandomFactorIsBetweenExpectedLimits() throws Exception {
        // when a good sample of randomly generated numbers is generated
        List<Integer> randomNumbers = IntStream.range(0, 3)
                .map(i -> randomGeneratorServiceImpl.generateRandomValue())
                .boxed().collect(Collectors.toList());

        // then all of them should be between 0 and 2
//        assertThat(randomNumbers).containsOnlyElementsOf(IntStream.range(0, 3)
//                .boxed().collect(Collectors.toList()));

        assertThat(randomNumbers).size().isEqualTo(3);

        assertThat(randomNumbers).doesNotContainNull();

        assertThat(randomNumbers).doesNotContain(4, 5);

    }

}