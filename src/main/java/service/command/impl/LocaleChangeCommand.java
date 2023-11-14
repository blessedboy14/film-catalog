package service.command.impl;

import beans.Language;
import service.command.CommandAnswer;
import service.command.ICommand;
import service.exception.CommandException;

import javax.servlet.http.HttpServletRequest;

/**
 * Class for executing requests for /locale
 */
public class LocaleChangeCommand implements ICommand {
    /**
     * Changing language in session
     * @param req HttpServletRequest object
     * @return CommandAnswer
     * @throws CommandException
     */
    @Override
    public CommandAnswer execute(HttpServletRequest req) throws CommandException {
        Language lang = (Language)req.getSession().getAttribute("lang");
        if (lang==null) {
            throw new CommandException("This is rly unpredictable!");
        }
        Language newLang = Language.get(req.getParameter("language"));
        if (newLang==null) {
            throw new CommandException("Empty field, impossible");
        }
        req.getSession().setAttribute("lang", newLang);
        String referrer = req.getHeader("Referer");
        return new CommandAnswer("", referrer, true);
    }
}
