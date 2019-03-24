package com.gcats.cats.repository;

import com.gcats.cats.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("commentRepository")
public interface CommentRepository  extends JpaRepository<Comment, Long> {

}
