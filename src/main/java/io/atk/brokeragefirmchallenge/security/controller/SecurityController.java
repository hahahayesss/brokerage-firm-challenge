package io.atk.brokeragefirmchallenge.security.controller;

import io.atk.brokeragefirmchallenge.account.model.Account;
import io.atk.brokeragefirmchallenge.account.service.AccountService;
import io.atk.brokeragefirmchallenge.security.model.SignInRequest;
import io.atk.brokeragefirmchallenge.security.service.SecurityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class SecurityController {
    private final AuthenticationManager authenticationManager;
    private final SecurityService securityService;
    private final AccountService accountService;

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(
            @Validated @RequestBody SignInRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(), request.getPassword()));
            Account account = accountService.getByUsername(request.getUsername());
            String token = securityService.createToken(
                    account.getId(), account.getUsername(), account.getRoles());
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer" + " " + token);
            return new ResponseEntity<>(Map.of("access_token", token), httpHeaders, HttpStatus.OK);
        } catch (DisabledException e) {
            throw new RuntimeException("AuthService.getToken.DisabledException");
        } catch (LockedException e) {
            throw new RuntimeException("AuthService.getToken.LockedException");
        } catch (BadCredentialsException e) {
            throw new RuntimeException("AuthService.getToken.BadCredentialsException");
        } catch (Exception e) {
            throw new RuntimeException("AuthService.getToken.Exception");
        }
    }
}
