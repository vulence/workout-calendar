package com.vule.workoutcalendar.interceptor;

import com.vule.workoutcalendar.annotation.RequiresJwtToken;
import io.micrometer.common.lang.NonNullApi;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@NonNullApi
public class JwtInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod handlerMethod) {
            if (handlerMethod.getMethodAnnotation(RequiresJwtToken.class) != null) {
                String jwtToken = request.getHeader("Authorization");

                if (jwtToken == null || !jwtToken.startsWith("Bearer ")) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return false;
                }

                jwtToken = jwtToken.substring(7);
                request.setAttribute("jwtToken", jwtToken);
            }
        }

        return true;
    }
}
