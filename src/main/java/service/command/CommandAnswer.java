package service.command;

/**
 * Container for data, that will describe result of executing request(forward or redirect)
 */
public class CommandAnswer {
    private String forwardPage;
    private String redirectUrl;
    private boolean isRedirect;
    public CommandAnswer(String forwardPage, String redirectUrl, boolean isRedirect) {
        this.forwardPage = forwardPage;
        this.redirectUrl = redirectUrl;
        this.isRedirect = isRedirect;
    }
    public CommandAnswer() {}

    public String getForwardPage() {
        return forwardPage;
    }

    public void setForwardPage(String forwardPage) {
        this.forwardPage = forwardPage;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public boolean isRedirect() {
        return isRedirect;
    }

    public void setRedirect(boolean redirect) {
        isRedirect = redirect;
    }
}
