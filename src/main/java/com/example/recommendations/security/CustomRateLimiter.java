package com.example.recommendations.security;

import com.example.recommendations.model.RequestDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.springframework.http.HttpStatus.TOO_MANY_REQUESTS;

@Configuration
public class CustomRateLimiter implements HandlerInterceptor {

    @Value("${rate.limit.max}")
    private Integer RATE_LIMIT_MAX;
    private ConcurrentHashMap<String, RequestDetails> requestCounts = new ConcurrentHashMap<>();
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        RequestDetails initialRequestDetails = RequestDetails
                .builder()
                .count(new AtomicInteger(0))
                .lastUpdate(LocalDateTime.now())
                .build();
        String ip = request.getRemoteAddr();

        requestCounts.putIfAbsent(ip,initialRequestDetails);
        RequestDetails updatedRequestDetails = requestCounts.get(ip);

        updatedRequestDetails.setCount(new AtomicInteger(updatedRequestDetails.getCount().incrementAndGet()));
        updatedRequestDetails.setLastUpdate(LocalDateTime.now());
        requestCounts.put(ip, updatedRequestDetails);


        if (updatedRequestDetails.getCount().get() > RATE_LIMIT_MAX) {
            // If over rate limit but certain time has passed we reset so it works next time.
            if(updatedRequestDetails.getLastUpdate().isBefore(LocalDateTime.now().minusMinutes(30))) {
                requestCounts.put(ip, initialRequestDetails);
            }
            response.setStatus(TOO_MANY_REQUESTS.value());
            return false;
        }

        return true;
    }
}
