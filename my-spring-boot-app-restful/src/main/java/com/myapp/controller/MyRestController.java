package com.myapp.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class MyRestController {

  private static final Logger logger = LoggerFactory.getLogger(MyRestController.class);

  private void logRequestDetails(HttpServletRequest request) {
    String clientIp = request.getRemoteAddr();
    String userAgent = request.getHeader("User-Agent");
    String requestUri = request.getRequestURI();
    String requestUrl = request.getRequestURL().toString();
    String method = request.getMethod();
    String contentType = request.getContentType();
    int contentLength = request.getContentLength();
    String query = request.getQueryString();
    String host = request.getHeader("Host");
    String referer = request.getHeader("Referer");
    String remoteHost = request.getRemoteHost();
    int remotePort = request.getRemotePort();

    logger.info("Request Details: IP: {},\n User-Agent: {},\n Method: {},\n URI: {}, URL: {},\n " +
                    "Content-Type: {},\n Content-Length: {},\n Query: {},\n Host: {},\n Referer: {},\n " +
                    "Remote Host: {},\n Remote Port: {}",
            clientIp, userAgent, method, requestUri, requestUrl, contentType,
            contentLength, query, host, referer, remoteHost, remotePort);
    // Logging cookies
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        logger.info("Cookie: Name={}, Value={}", cookie.getName(), cookie.getValue());
      }
    }

    // Logging locale
    Locale locale = request.getLocale();
    logger.info("Locale: {}", locale.toString());

    // Logging character encoding
    String characterEncoding = request.getCharacterEncoding();
    logger.info("Character Encoding: {}", characterEncoding);

    // Logging session attributes (if session exists)
    HttpSession session = request.getSession(false);
    if (session != null) {
      Enumeration<String> sessionAttributeNames = session.getAttributeNames();
      while (sessionAttributeNames.hasMoreElements()) {
        String attributeName = sessionAttributeNames.nextElement();
        Object attributeValue = session.getAttribute(attributeName);
        logger.info("Session Attribute: Name={}, Value={}", attributeName, attributeValue);
      }
    }

    // Servlet and Context Path
    String servletPath = request.getServletPath();
    String contextPath = request.getContextPath();
    logger.info("Servlet Path: {}, Context Path: {}", servletPath, contextPath);
  }


  @GetMapping(value = "/")
  public RedirectView redirectToAdminDashboard(HttpServletRequest request) {
    logRequestDetails(request);
    return new RedirectView("/enter");
  }

  @GetMapping(value = "/enter", produces = MediaType.TEXT_HTML_VALUE)
  public String enter(HttpServletRequest request) {
    logRequestDetails(request);

    Path htmlPath = Paths.get("src/main/resources/static/index.html");
    try {
      return Files.readString(htmlPath);
    } catch (IOException e) {
      logger.error("Error reading file: {}", e.getMessage());
      return e.getMessage();
    }
  }

  @GetMapping (value = "/home", produces = MediaType.TEXT_HTML_VALUE)
  public String home(HttpServletRequest request){
    logRequestDetails(request);
    Path htmlPath = Paths.get("src/main/resources/static/home.html");
    try {
      return Files.readString(htmlPath);
    } catch (IOException e) {
      logger.error("Error reading file: {}", e.getMessage());
      return e.getMessage();
    }
  }

}
