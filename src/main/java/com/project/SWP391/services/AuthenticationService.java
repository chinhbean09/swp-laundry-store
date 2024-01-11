package com.project.SWP391.services;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.project.SWP391.entities.User;
import com.project.SWP391.entities.Token;
import com.project.SWP391.entities.TokenType;
import com.project.SWP391.repositories.FeedbackRepository;
import com.project.SWP391.repositories.TokenRepository;
import com.project.SWP391.repositories.UserRepository;
import com.project.SWP391.requests.AuthenticationRequest;
import com.project.SWP391.requests.RegisterRequest;
import com.project.SWP391.responses.AuthenticationResponse;

import com.project.SWP391.responses.dto.UserInfoDTO;
import com.project.SWP391.security.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final FeedbackRepository feedbackRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final ModelMapper mapper;

    public AuthenticationResponse register(RegisterRequest request) throws Exception {

        if(!repository.existsByEmail(request.getEmail())){
            var newUser = User.builder()
                    .fullName(request.getFullName())
                    .email(request.getEmail())
                    .phone(request.getPhone())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(request.getRole())
                    .status(1)
                    .build();
            var savedUser = repository.save(newUser);
            var jwtToken = jwtService.generateToken(newUser);
            var refreshToken = jwtService.generateRefreshToken(newUser);
            saveUserToken(savedUser, jwtToken);
            return AuthenticationResponse.builder()
                    .userInfoDTO(mapper.map(savedUser, UserInfoDTO.class))
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .build();


        }

      throw new Exception("Email is existed");
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var feedbacks = feedbackRepository.findAllByUserId(user.getId());
        var jwtToken = jwtService.generateToken((UserDetails) user);
        var refreshToken = jwtService.generateRefreshToken((UserDetails) user);


        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .userInfoDTO(mapper.map(user, UserInfoDTO.class))
                .build();


    }

    private void saveUserToken(User user, String jwtToken) {
       Token existedToken = tokenRepository.findByUserId(user.getId());
        if (existedToken != null){
            tokenRepository.delete(existedToken);
        }
            var token = Token.builder()
                    .user(user)
                    .token(jwtToken)
                    .tokenType(TokenType.BEARER)
                    .expired(false)
                    .revoked(false)
                    .build();
            tokenRepository.save(token);
    }



    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(Math.toIntExact(user.getId()));
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.repository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

}
