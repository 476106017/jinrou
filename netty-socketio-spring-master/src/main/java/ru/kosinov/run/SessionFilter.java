package ru.kosinov.run;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mazhenhua on 2016/12/27.
 *
 * 过滤器
 */
@Component
public class SessionFilter implements Filter {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(SessionFilter.class);


    /**
     * 封装，不需要过滤的list列表
     */
    protected static List<Pattern> patterns = new ArrayList<Pattern>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        String url = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
        if (url.startsWith("/") && url.length() > 1) {
            url = url.substring(1);
        }

        allowCrossAccess(httpRequest,httpResponse);

        if (isInclude(url)){
            chain.doFilter(httpRequest, httpResponse);
        } else {
            HttpSession session = httpRequest.getSession();
            if (session.getAttribute("") != null){
                // session存在
                chain.doFilter(httpRequest, httpResponse);
            } else {
                // session不存在 准备跳转失败
                /* RequestDispatcher dispatcher = request.getRequestDispatcher(path);
                    dispatcher.forward(request, response);*/
                chain.doFilter(httpRequest, httpResponse);
            }
        }


    }

    @Override
    public void destroy() {

    }


    /**
     * 是否需要过滤
     * @param url
     * @return
     */
    private boolean isInclude(String url) {
        for (Pattern pattern : patterns) {
            Matcher matcher = pattern.matcher(url);
            if (matcher.matches()) {
                return true;
            }
        }
        return false;
    }

    protected void allowCrossAccess(HttpServletRequest request,HttpServletResponse response) {

//       String allowOrigin = "*";

        String allowOrigin = request.getHeader("Origin");

        String allowMethods = "GET,PUT, POST, DELETE";

        String allowHeaders = "Origin,No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified,Cache-Control, Expires, Content-Type, X-E4M-With";

        response.addHeader("Access-Control-Allow-Credentials", "true");

        response.addHeader("Access-Control-Allow-Headers", allowHeaders);

        response.addHeader("Access-Control-Allow-Methods", allowMethods);

        response.addHeader("Access-Control-Allow-Origin", allowOrigin);

    }
}