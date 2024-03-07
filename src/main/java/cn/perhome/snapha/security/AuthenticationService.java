package cn.perhome.snapha.security;


import cn.perhome.snapha.dto.form.FormRegisterDto;
import cn.perhome.snapha.mapper.UserTokenMapper;
import cn.perhome.snapha.model.UserToken;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthUserHelper  authUserHelper;
    private final UserTokenMapper userTokenMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(FormRegisterDto request) {
        var user = AuthUser.builder()
                .passport(request.getPassport())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        var savedUser = authUserHelper.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getPassport(),
                        request.getPassword()
                )
        );
        var user = authUserHelper.findByPassport(request.getPassport())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public void saveUserToken(AuthUser authUser, String jwtToken) {
        var token = UserToken.builder()
                .token(jwtToken)
                .userId(authUser.getId())
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        userTokenMapper.insertSelective(token);
    }

    public void revokeAllUserTokens(AuthUser authUser) {
        var validUserTokens = userTokenMapper.findAllValidTokenByUser(authUser.getId());
        if (validUserTokens.isEmpty())
            return;

        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        userTokenMapper.saveAll(validUserTokens);
    }

    public AuthenticationResponse refreshToken(
            HttpServletRequest request
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String passport;
        AuthenticationResponse authResponse = new AuthenticationResponse();
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return authResponse;
        }
        refreshToken = authHeader.substring(7);
        passport = jwtService.extractUsername(refreshToken);
        if (passport != null) {
            var user = authUserHelper.findByPassport(passport)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
            }
        }
        return authResponse;
    }
}
