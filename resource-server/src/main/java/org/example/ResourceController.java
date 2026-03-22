package org.example;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ResourceController {

    // endpoint to access with token
    @GetMapping("/api/user/me")
    public Map<String, Object> getUserInfo(@AuthenticationPrincipal Jwt jwt) {
        // Engineering Lesson: The 'sub' claim is the username (e.g., "admin")
        return Map.of(
                "username", jwt.getSubject(),
                "scopes", jwt.getClaim("scope"),
                "message", "Hello from the Resource Server!"
        );
    }

    // added for jwt customization
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/api/user/hello")
    public String hello() {
        return "secured";
    }

    // for client credential flow
    @GetMapping("/api/service/hello")
    public String helloService() {
        return " here service";
    }
}