package hobby_garden.hobby_garden_server.common.security;

import hobby_garden.hobby_garden_server.common.enums.LogLevel;
import hobby_garden.hobby_garden_server.common.mapper.LogMapper;
import hobby_garden.hobby_garden_server.log.model.LogModel;
import hobby_garden.hobby_garden_server.log.model.LogRequestModel;
import hobby_garden.hobby_garden_server.log.model.LogResponseModel;
import hobby_garden.hobby_garden_server.log.repository.LogRepository;
import hobby_garden.hobby_garden_server.user.service.JWTService;
import hobby_garden.hobby_garden_server.user.service.UserService;
import io.micrometer.common.lang.NonNullApi;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.bson.json.JsonObject;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;

//TODO add headers to log
//TODO add path variables to log (if any)
//TODO crypto password if user password is in log

@Component
@NonNullApi
@RequiredArgsConstructor
public class ApplicationFilter extends OncePerRequestFilter {

    private final LogRepository logRepository;

    @Override
<<<<<<< HEAD
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if(request.getRequestURI().equals("/swagger-ui/index.html")){
            filterChain.doFilter(request, response);
            return;
        }

        // * log filter and authentication filter
        logFilter(request, response, filterChain);
        authenticationFilter(request, response, filterChain);
        filterChain.doFilter(request, response);

    }

    private void authenticationFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String bearer = "Bearer ";
        final String authorizationHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        if (StringUtils.isEmpty(authorizationHeader) || !org.apache.commons.lang3.StringUtils.startsWith(authorizationHeader, bearer)) {
            return;
        }

        jwt = authorizationHeader.substring(bearer.length());
        username = jwtService.extractUserName(jwt);

        if (StringUtils.isNotEmpty(username) || SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService.userDetailsService().loadUserByUsername(username);

            if (jwtService.isTokenValid(jwt, userDetails)) {
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());

                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                securityContext.setAuthentication(token);
                SecurityContextHolder.setContext(securityContext);
            }
        }
    }

    private void logFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
=======
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // * Log mapper
        LogMapper logMapper = new LogMapper();

        // * start time
        long startTime = System.currentTimeMillis();
>>>>>>> add553c72abf9e54a352794a784fb2b79067e52f

        // * wrap request and response
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        // * Log mapper
        LogMapper logMapper = new LogMapper();

        // * start time
        long startTime = System.currentTimeMillis();

        // calculate response time
        long responseTime = System.currentTimeMillis() - startTime;
        String responseTimeStr = responseTime + "ms";

        // timestamp
        String timestampStr = new Timestamp(System.currentTimeMillis()).toString();

        // request payload
        Object requestPayload;
        String requestBody = new String(requestWrapper.getContentAsByteArray(), request.getCharacterEncoding());

        // if request body is empty, set it to empty object
        if (requestBody.isEmpty() || requestBody.isBlank() || requestBody.equals(" ")) {
            requestPayload = new JsonObject("{}");
        } else {
            requestPayload = logMapper.stringToObject(requestBody);
        }

        // response body
        Object responseBody;
        // convert response body to string
        String result;
        result = new String(responseWrapper.getContentAsByteArray(), StandardCharsets.UTF_8);

        // if response body is empty, set it to empty object
        if (result.isEmpty() || result.isBlank() || result.equals(" ")) {
            responseBody = new JsonObject("{}");
        } else {
            responseBody = logMapper.stringToObject(result);
        }

        // copy response body to response
        responseWrapper.copyBodyToResponse();

        // save log
        logRepository.save(new LogModel(
                null,
                new LogRequestModel<>(
                        requestWrapper.getMethod(),
                        timestampStr,
                        requestWrapper.getRequestURI(),
                        requestPayload),
                new LogResponseModel<>(
                        findLogLevel(responseWrapper.getStatus()),
                        responseWrapper.getStatus(),
                        responseTimeStr,
                        responseWrapper.getBufferSize(),
                        responseBody),
                null));
    }

    private LogLevel findLogLevel(int status) {
        return switch (status) {
            case 400 -> LogLevel.WARNING;
            case 500 -> LogLevel.ERROR;
            default -> LogLevel.INFO;
        };
    }
}
