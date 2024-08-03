package com.example.ivoiretransportapi.features.auth.service;

import com.example.ivoiretransportapi.core.exception.EntityAllReadyExistException;
import com.example.ivoiretransportapi.features.auth.models.SigninRequest;
import com.example.ivoiretransportapi.features.auth.models.SigninResponse;
import com.example.ivoiretransportapi.features.auth.models.SignupRequest;
import com.example.ivoiretransportapi.features.email.EmailService;
import com.example.ivoiretransportapi.features.email.EmailTemplateName;
import com.example.ivoiretransportapi.features.role.RoleRepository;
import com.example.ivoiretransportapi.features.security.JwtService;
import com.example.ivoiretransportapi.features.token.Token;
import com.example.ivoiretransportapi.features.token.TokenRepository;
import com.example.ivoiretransportapi.features.user.User;
import com.example.ivoiretransportapi.features.user.UserRepository;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final EmailService emailService;
    private final TokenRepository tokenRepository;

    @Value("${application.mailing.frontend.activation-url}")
    private String activationUrl;

    @Override
    public void signup(SignupRequest request) throws MessagingException {
        var userRole = roleRepository.findByName("USER").orElseThrow(()->new IllegalStateException("Le RÔLE UTILISATEUR n'a pas été initié"));

        Optional<User> verifiedUser = userRepository.findByEmail(request.getEmail());

        if (verifiedUser.isPresent()) {
            log.warn("L'utilisateur avec l'email {} existe déjà dans BD", request.getEmail());
            throw new EntityAllReadyExistException("Cet e-mail est déjà pris ");
        }

        var userToSave = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .enabled(false)
                .roles(List.of(userRole))
                .build();
        userRepository.save(userToSave);
        sendValidationEmail(userToSave);
    }

    @Override
    public SigninResponse signin(SigninRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var claims = new HashMap<String, Object>();
        var user = ((User) auth.getPrincipal());
        claims.put("fullName", user.getFullName());

        var jwtToken = jwtService.generateToken(claims, (User) auth.getPrincipal());
        return SigninResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public void activateAccount(String token) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(token)
                // todo exception has to be defined
                .orElseThrow(() -> new RuntimeException("Token invalide"));
        if (LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
            sendValidationEmail(savedToken.getUser());
            throw new RuntimeException("Le token d'activation a expiré. Un nouveau token a été envoyé à la même adresse e-mail");
        }

        var user = userRepository.findById(savedToken.getUser().getId())
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));
        user.setEnabled(true);
        userRepository.save(user);

        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
    }

    private String generateAndSaveActivationToken(User user) {
        // Generate a token
        String generatedToken = generateActivationCode(6);
        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        tokenRepository.save(token);

        return generatedToken;
    }

    private void sendValidationEmail(User user) throws MessagingException {
        var newToken = generateAndSaveActivationToken(user);

        emailService.sendEmail(
                user.getEmail(),
                user.getFullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                "Account activation"
        );
    }

    private String generateActivationCode(int length) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();

        SecureRandom secureRandom = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }

        return codeBuilder.toString();
    }
}
