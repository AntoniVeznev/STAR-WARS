package com.example.star_wars_project.repository;

import com.example.star_wars_project.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findCommentsByMovie_IdOrderByCreatedDesc(Long movieID);

    List<Comment> findCommentsBySeries_IdOrderByCreatedDesc(Long serialId);

    List<Comment> findCommentsByGame_IdOrderByCreatedDesc(Long gameId);

    Comment findCommentById(Long id);

    List<Comment> findCommentsByAuthor_Id(Long id);
}