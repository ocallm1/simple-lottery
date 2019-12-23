package microservices.simple.lottery.service;

/**
 * Created by ocallm1 on 18/12/19.
 */
public interface RandomGeneratorService
{

    /**
     * @return a randomly-generated factor. It's always a number between 11 and 99.
     */
    int generateRandomValue();

}
