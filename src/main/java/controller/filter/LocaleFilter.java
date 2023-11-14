package controller.filter;

import beans.Language;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LocaleFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    private Language getLocale(HttpServletRequest httpServletRequest) {
        HttpSession httpSession = httpServletRequest.getSession();
        Language language = (Language) httpSession.getAttribute("lang");
        if (language == null) {
            language = Language.ENGLISH;
            httpSession.setAttribute("lang", Language.ENGLISH);
        }
        return language;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String command = httpServletRequest.getRequestURI().substring(1);
        if (command.endsWith("review") && httpServletRequest.getSession().getAttribute("loggedIn") == null) {
            httpServletResponse.sendRedirect("/register");
            return;
        }
        Language language = getLocale(httpServletRequest);
        httpServletRequest.setAttribute("lang", language.getName());
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
