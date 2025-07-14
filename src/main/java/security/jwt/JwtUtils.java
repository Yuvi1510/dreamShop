package security.jwt;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import security.user.ShopUserDetails;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.JwtException;


@Component
public class JwtUtils {
	private String jwtSecret;
	
	@Value("${auth.token.expirationMillis}")
	private int expirationTime;
	
	
	public JwtUtils() {
		try {
			KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
			SecretKey sk = keyGen.generateKey();
			
			this.jwtSecret = Base64.getEncoder().encodeToString(sk.getEncoded());
			
		}catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	public String generateTokenForuser(Authentication authentication) {
		ShopUserDetails userPrincipal = (ShopUserDetails) authentication.getPrincipal(); 
		
		List<String> roles = userPrincipal.getAuthorities().stream().map((role)-> role.getAuthority()).toList();
		
		return Jwts.builder()
				.setSubject(userPrincipal.getEmail())
				.claim("id", userPrincipal.getId())
				.claim("roles", roles)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
				.signWith(key(), SignatureAlgorithm.HS256)
				.compact();
				
	}
	
	
	private Key key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}
	
	public String getUsernameFromToken(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(key())
				.build()
				.parseClaimsJws(token)  
				.getBody()
				.getSubject();
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
			.setSigningKey(key())
			.build()
			.parseClaimsJws(token);
			
			return true;
		}catch(ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
			throw new JwtException(e.getMessage());
		}
	}
	
}















