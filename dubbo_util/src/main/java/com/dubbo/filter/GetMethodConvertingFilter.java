package com.dubbo.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;

public class GetMethodConvertingFilter implements Filter {//这个过滤器将forward跳转指定为get请求

    @Override
    public void init(FilterConfig config) throws ServletException {
        // do nothing
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        chain.doFilter(wrapRequest((HttpServletRequest) request), response);
    }

    @Override
    public void destroy() {
        // do nothing
    }

    private static HttpServletRequestWrapper wrapRequest(HttpServletRequest request) {
        return new HttpServletRequestWrapper(request) {
            @Override
            public String getMethod() {
                return "GET";
            }
        };
    }
}