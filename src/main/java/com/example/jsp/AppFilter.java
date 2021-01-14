package com.example.jsp;

import javax.servlet.*;
import java.io.IOException;
import javax.servlet.annotation.WebFilter;

@WebFilter("/")
public class AppFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException { }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() { }
}
