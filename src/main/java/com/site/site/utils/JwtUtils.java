package com.site.site.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import javax.crypto.SecretKey;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.site.site.constant.Constant.redisAddress;

public class JwtUtils {
    public static final String privateKey = "askldjsdklfjasldkjlkasdjklasjdlkasjdlkjasdlkjaskld";

    public void writeToRedis(String userId, String privateKey) throws InterruptedException, JsonProcessingException {
        Config config = new Config();
        config.useSingleServer().setAddress(redisAddress);
        RedissonClient redisson = Redisson.create(config);
        RBucket<String> bucket = redisson.getBucket(userId);
        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("privateKey", privateKey);
        bucket.set(new ObjectMapper().writeValueAsString(userInfo));
        redisson.shutdown();
        Thread.sleep(1000 * 5);
    }

    public String createToken(Map<String, Object> payload) throws InterruptedException, JsonProcessingException {
        System.out.println("open redis get key*******************************");
        String privateKeyStr = privateKey + String.valueOf(new Random().nextFloat()).split("\\.")[1];
        System.out.println(privateKeyStr);
        String userId = payload.get("id").toString();
        this.writeToRedis(userId, privateKeyStr);

        JwtBuilder jwtBuilder = Jwts.builder();
        // 添加payload
        payload.forEach((k, value) -> {
            jwtBuilder.claim(k, value);
        });
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 3600);
        // 添加过期时间
        jwtBuilder.setExpiration(calendar.getTime());
        // 生成jwt string
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(privateKeyStr));
        return jwtBuilder.signWith(key).compact();
    }

    public boolean verifyToken(String privateKeyStr, String token) {
        Jws<Claims> jws = null;
        if (privateKeyStr == null) {
            return false;
        }
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(privateKeyStr));
        jws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        System.out.println(jws);

        return jws != null;
    }

    public JsonNode getUserInfoFromRedis(String userId) throws JsonProcessingException {
        Config config = new Config();
        config.useSingleServer().setAddress(redisAddress);
        RedissonClient redisson = Redisson.create(config);
        RBucket<String> bucket = redisson.getBucket(userId);
        String userInfoStr = bucket.get();
        redisson.shutdown();
        JsonNode userInfoNode = new ObjectMapper().readTree(userInfoStr);
        return userInfoNode;
    }
}
