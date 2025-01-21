package com.practice.project1.service.serviceImpl;

import com.practice.project1.model.entity.UserGmail;
import com.practice.project1.repository.UserGmailRepository;
import com.practice.project1.service.UserGmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserGmailServiceImpl implements UserGmailService {

    private final UserGmailRepository userGmailRepository;

    @Override
    public String addUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof OAuth2AuthenticationToken oauthToken) {
            OAuth2User oauthUser = oauthToken.getPrincipal();
            if (oauthUser != null) {
                Map<String, Object> attributes = oauthUser.getAttributes();
                Optional<UserGmail> userOptional = userGmailRepository.findById((String) attributes.get("sub"));
                if (userOptional.isEmpty()) {
                    UserGmail userGmail = new UserGmail();
                    userGmail.setId((String) attributes.get("sub"));
                    userGmail.setGiven_name((String) attributes.get("given_name"));
                    userGmail.setFamily_name((String) attributes.get("family_name"));
                    userGmail.setName((String) attributes.get("name"));
                    userGmail.setEmail((String) attributes.get("email"));
                    userGmail.setPicture((String) attributes.get("picture"));
                    userGmailRepository.save(userGmail);
                }
                return "Hello" + attributes.get("given_name");
            }
        }
        return "No OAuth2AuthenticationToken found";
    }
}
