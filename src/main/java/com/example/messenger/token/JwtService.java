package com.example.messenger.token;

import com.example.messenger.user.User;
import com.example.messenger.user.UserDetail;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.security.SignatureException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class JwtService {
    private static final String SECRET_KEY = "LW4gJ3siYWxnIjoiRVMyNTYiLCJ0eXAiOiJKV1QifScgDQo";
    private final JwtRepository jwtRepository;
    // Subject claims: Subject claim quy định chủ thể của token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject ); // (Claims) -> Claims.getSubject()
    }
    private static final Logger logger = LoggerFactory.getLogger(JwtService.class.getName());

    // Tạo token mà không thêm extraClaims bằng cách tái sử dụng hàm generateToken
    public String generateToken(User userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    // Tạo token và thêm claim bất ký
    public String generateToken(
            Map<String, Object> extraClaims, // Để thêm một thông tin bất kì
            User userDetails
    ) {
        return Jwts.builder().setClaims(extraClaims)
                .setSubject(userDetails.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 3))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256 )
                .compact();
    }

    // Check xem token có thuộc về user này hay không
    public boolean checkToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token -> Message: {}", e);
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token -> Message: {}", e);
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token -> Message: {}", e);
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty -> Message: {}", e);
        }

        return false;
//        return (username.equals(userDetails.getEmail()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    //
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
