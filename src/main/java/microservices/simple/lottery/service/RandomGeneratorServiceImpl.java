package microservices.simple.lottery.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by ocallm1 on 18/12/19.
 */
@Service
class RandomGeneratorServiceImpl implements RandomGeneratorService
{

    // Upper bound exclusive
    static final int MAXIMUM_NUMBER = 3;

    public int generateRandomValue()
    {
        final int boundedRandomValue = ThreadLocalRandom.current().nextInt(0, 3);
        return boundedRandomValue;
    }
}
