package com.site.site.interceptor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.site.site.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.Charset;
import java.security.Key;
import java.util.Base64;
import java.util.HashMap;

@Configuration
public class Interceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        System.out.println("in interceptor 进入了拦截器！！！！！!!!!!!!!!");
        HashMap<String, Object> resMap = new HashMap<>();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        if (token == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resMap.put("state", "你未登录！！！");
            String json = new ObjectMapper().writeValueAsString(resMap);
            response.getWriter().println(json);
            return false;
        }
        JwtUtils jwtUtils = new JwtUtils();
        String[] splits = token.split("\\.");
        String userInfoBase64 = splits[1];
        //eyJhZGRyZXNzIjoiYXNkIiwibmFtZSI6Inp4IiwiaWQiOiIxMjMxMiIsImFnZSI6MTksImV4cCI6MTY0OTYxODU4Mn0
        byte[] decodedBytes = Base64.getDecoder().decode(userInfoBase64);
        String userInfoStr = new String(decodedBytes);
        //{"address":"asd","name":"zx","id":"12312","age":19,"exp":1649618582}

        JsonNode userInfoNode = new ObjectMapper().readTree(userInfoStr);
        String userId = userInfoNode.get("id").asText();
        // 从redis中读取 privateKeyStr
        JsonNode userInfo = jwtUtils.getUserInfoFromRedis(userId);
        String privateKeyStr = userInfo.get("privateKey").toString();
        if (privateKeyStr == null) {
            // 说明当前用户没登录
            response.setStatus(401);
            resMap.put("state", "你未登录！！！");
            String json = new ObjectMapper().writeValueAsString(resMap);
            response.getWriter().println(json);
            return false;
        }

        boolean flag = false;
        try {
            flag = jwtUtils.verifyToken(privateKeyStr, token);
        } catch (SignatureException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resMap.put("state", "登录态已失效！！！");
        } catch (ExpiredJwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resMap.put("state", "token已过期！！！");
            e.printStackTrace();
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resMap.put("state", "登录态已失效！！！");
            e.printStackTrace();
        }

        if (flag) {
            return true;
        } else {
            String json = new ObjectMapper().writeValueAsString(resMap);
            response.getWriter().println(json);
            return false;
        }
    }


}
