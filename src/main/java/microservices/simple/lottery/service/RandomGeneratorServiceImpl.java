package microservices.simple.lottery.service;

import org.springframework.stereotype.Service;
import java.util.Random;

@Service
class RandomGeneratorServiceImpl implements RandomGeneratorService {

    // Upper bound exclusive
    final static int MAXIMUM_NUMBER = 3;

    @Override
    public int generateRandomValue() {
        return new Random().nextInt(MAXIMUM_NUMBER);
    }
}
