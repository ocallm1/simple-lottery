package microservices.simple.lottery.service;

import org.springframework.stereotype.Service;
import java.util.Random;

@Service
class RandomGeneratorServiceImpl implements RandomGeneratorService {

    // Upper bound exclusive
    static final int MAXIMUM_NUMBER = 3;

    public int generateRandomValue() {
        return new Random().nextInt(RandomGeneratorServiceImpl.MAXIMUM_NUMBER);
    }
}
