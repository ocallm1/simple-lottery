package microservices.simple.lottery.service.Error;

public class ServiceError
{
    private String errorMsg;

    public ServiceError(final String errorMsg)
    {
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg()
    {
        return this.errorMsg;
    }

    public void setErrorMsg(String errorMsg)
    {
        this.errorMsg = errorMsg;
    }
}
