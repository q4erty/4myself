package com.practice.project1.repository;

import com.practice.project1.model.entity.UserGmail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGmailRepository extends JpaRepository<UserGmail, String> {

}
