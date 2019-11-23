package microservices.simple.lottery.service;

public class SimpleLotteryServiceException extends Exception
{

    public SimpleLotteryServiceException(final String errorMessage)
    {
        super(errorMessage);
    }
}