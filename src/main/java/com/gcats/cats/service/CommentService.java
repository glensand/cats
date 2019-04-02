package com.gcats.cats.service;

import com.gcats.cats.model.Comment;
import com.gcats.cats.model.Lesson;
import com.gcats.cats.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("commentService")
public class CommentService {

    private CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Iterable<Comment> listAllComments() {
        return commentRepository.findAll();
    }

    public Comment saveComment(Comment comment){return commentRepository.save(comment);}

    public Comment findCommentById(int id) {
        return commentRepository.findById(id);
    }

    public void deleteById(int id){ commentRepository.deleteById(id);}

}