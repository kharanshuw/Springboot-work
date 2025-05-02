package com.example.demo.service;

import com.example.demo.repository.PostCommentRepo;
import com.example.demo.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.controller.TestController;
import com.example.demo.dto.Postform;
import com.example.demo.entity.Post;
import com.example.demo.entity.PostComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {


    public Logger logger = LoggerFactory.getLogger(TestController.class);

    public PostRepository postRepository;

    public PostCommentRepo postCommentRepo;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, PostCommentRepo postCommentRepo) {
        this.postRepository = postRepository;
        this.postCommentRepo = postCommentRepo;
    }

    @Override
    public Post savePost(Post post) {
        Post post1 = postRepository.save(post);
        return post1;
    }


    public List<Post> getallpost() {
        List<Post> optionalPost = postRepository.findAll();

        return optionalPost;
    }


    public Post getpostbyid(int id) {
        return postRepository.findById(id).orElseThrow(() -> {
            return new RuntimeException("error occured while finding post with given id " + id);
        });
    }


    public Post updatePost(Postform post) {
        Post oldPost = postRepository.findById(post.getId()).orElseThrow(() -> {
            return new RuntimeException("error occured in updatePost ");
        });


        if (post.getPostComment() == null || post.getPostComment().isEmpty()) {
            throw new IllegalArgumentException("Post comment cannot be null or empty.");
        }


        PostComment postComment = new PostComment();

        postComment.setReview(post.getPostComment());

        postComment.setPost(oldPost);

        postComment = postCommentRepo.save(postComment);

        oldPost.addComment(postComment);

        postRepository.save(oldPost);

        return oldPost;
    }


    public Post deletePost(int id) {
        logger.info("deletePost called");

       Post post = postRepository.findById(id).orElseThrow(() -> {
            return new RuntimeException("post not exist with this id");
        });

        logger.info("deleting post ");
        postRepository.delete(post);

        logger.info("deleted post successfully");

        return post;
    }
}
