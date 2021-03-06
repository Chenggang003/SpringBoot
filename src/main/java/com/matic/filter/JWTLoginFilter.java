package com.matic.filter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.matic.vo.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter{
	@Autowired
	private AuthenticationManager authenticationManager ;
    
	public JWTLoginFilter(AuthenticationManager authenticationManager) {  
        this.authenticationManager = authenticationManager;  
    }  
    @Override  
    public Authentication attemptAuthentication(HttpServletRequest req,HttpServletResponse res) throws AuthenticationException {  
        try {
        	 
            User user = new ObjectMapper().readValue(req.getInputStream(), User.class);  
  
            return authenticationManager.authenticate(  
                    new UsernamePasswordAuthenticationToken(  
                            user.getUsername(),  
                            user.getPassword(),  
                            new ArrayList<>())  
            );  
            
        } catch (IOException e) {  
            throw new RuntimeException(e);  
        }  
    }
 // 用户成功登录后，这个方法会被调用，我们在这个方法里生成token  
    @Override  
    protected void successfulAuthentication(HttpServletRequest req,  
                                            HttpServletResponse res,  
                                            FilterChain chain,  
                                            Authentication auth) throws IOException, ServletException {  
  
        String token = Jwts.builder()  
                .setSubject(((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername())  
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 24 * 1000))  
                .signWith(SignatureAlgorithm.HS512, "MyJwtSecret")  
                .compact();  
        res.addHeader("Authorization", "Bearer " + token);  
    }  

}
