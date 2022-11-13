package com.webapp.vascomm.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import com.google.gson.Gson;
import com.webapp.vascomm.repository.log_response_rep;
import com.webapp.vascomm.table.log_response;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggableDispatcherServlet extends DispatcherServlet {
    @Autowired
    private log_response_rep rep;

    @Override
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!(request instanceof ContentCachingRequestWrapper)) {
            request = new ContentCachingRequestWrapper(request);
        }
        if (!(response instanceof ContentCachingResponseWrapper)) {
            response = new ContentCachingResponseWrapper(response);
        }
        HandlerExecutionChain handler = getHandler(request);

        try {
            super.doDispatch(request, response);
        } finally {
            log(request, response, handler);
            updateResponse(response);
        }
    }

    private void log(HttpServletRequest requestToCache, HttpServletResponse responseToCache, HandlerExecutionChain handler) {
        LogMessage logMsg = new LogMessage();
        logMsg.setHttpStatus(responseToCache.getStatus());
        logMsg.setHttpMethod(requestToCache.getMethod());
        logMsg.setPath(requestToCache.getRequestURI());
        logMsg.setClientIp(requestToCache.getRemoteAddr());
        logMsg.setJavaMethod(handler.toString());
        logMsg.setResponse(getResponsePayload(responseToCache));


        rep.save(new log_response(new Gson().toJson(logMsg).toString()));
        
        log.info(new Gson().toJson(logMsg).toString());
    }

    private String getResponsePayload(HttpServletResponse response) {
        ContentCachingResponseWrapper wrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        if (wrapper != null) {

            byte[] buf = wrapper.getContentAsByteArray();
            if (buf.length > 0) {
                int length = Math.min(buf.length, 5120);
                try {
                    return new String(buf, 0, length, wrapper.getCharacterEncoding());
                }
                catch (UnsupportedEncodingException ex) {
                    // NOOP
                }
            }
        }
        return "[unknown]";
    }

    private void updateResponse(HttpServletResponse response) throws IOException {
        ContentCachingResponseWrapper responseWrapper =
            WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        responseWrapper.copyBodyToResponse();
    }

}