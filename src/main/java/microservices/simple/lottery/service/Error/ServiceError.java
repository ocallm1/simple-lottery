package microservices.simple.lottery.service.Error;

public class ServiceError
{
    private String errorMsg;

    public ServiceError(String errorMsg)
    {
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg()
    {
        return errorMsg;
    }

    public void setErrorMsg(final String errorMsg)
    {
        this.errorMsg = errorMsg;
    }
}
