package controller;

import service.command.CommandAnswer;
import service.command.CommandHelper;
import service.command.ICommand;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class for managing requests, parse commands and call execute method of ICommand
 */
public class Controller extends HttpServlet {
    /**
     * Handling GET request
     * @param req Request
     * @param resp Response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        processCommands(req, resp);
    }

    /**
     * Parsing complex command to identifying requested jsp page
     * @param command complex URL from request
     * @return Simple url, just last word from url
     */
    private String parseComplexCommand(String command) {
        if (command.endsWith("/review")) {
            return "review";
        } else if (command.endsWith("/delete") && command.contains("review")) {
            return "delete-review";
        } else if (command.endsWith("/delete") && command.contains("film")) {
            return "delete-film";
        } else {
            return command.substring(0, command.indexOf("/"));
        }
    }

    /**
     * Calling corresponding ICommand implementation
     * @param req Request
     * @param resp Response
     * @throws ServletException
     * @throws IOException
     */
    private void processCommands(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String commandName = req.getRequestURI().substring(1);
        if (commandName.contains("/")) {
            commandName = parseComplexCommand(commandName);
        }
        ICommand command = CommandHelper.getInstance().getCommand(commandName);
        CommandAnswer answer;
        try{
            answer = command.execute(req);
        } catch (Exception e) {
             answer = new CommandAnswer(JspPageName.ERROR_PAGE, "", false);
        }
        if (!answer.isRedirect()) {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher(answer.getForwardPage());
            if (requestDispatcher != null) {
                requestDispatcher.forward(req, resp);
            } else {
                errorMessageDirectlyFromResponse(resp);
            }
        } else {
            resp.sendRedirect(answer.getRedirectUrl());
        }
    }

    /**
     * Handling POST request
     * @param req Request
     * @param resp Response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processCommands(req, resp);
    }

    private void errorMessageDirectlyFromResponse(HttpServletResponse response) throws
            IOException{
        response.setContentType("text/html");
        response.getWriter().println("E R R O R");
    }

}
