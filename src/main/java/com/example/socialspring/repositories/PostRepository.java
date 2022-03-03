package com.example.socialspring.repositories;

import com.example.socialspring.entities.Post;
import com.example.socialspring.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    Object findByUser(User user);
}
