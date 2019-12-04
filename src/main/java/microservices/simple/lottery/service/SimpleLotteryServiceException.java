package microservices.simple.lottery.service;

public class SimpleLotteryServiceException extends Exception
{

    public SimpleLotteryServiceException(String errorMessage)
    {
        super(errorMessage);
    }
}