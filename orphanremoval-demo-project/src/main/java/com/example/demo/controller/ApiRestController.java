package com.example.demo.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.dto.Postform;
import com.example.demo.entity.Post;
import com.example.demo.service.PostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
public class ApiRestController {

    public Logger logger = LoggerFactory.getLogger(ApiRestController.class);

    private PostService postService;


    @Autowired
    public ApiRestController(PostService postService) {
        this.postService = postService;
    }



    @GetMapping("/allpost")
    public List<Post> getMethodName() {

        logger.info("executing getallpost");

        List<Post> postList = postService.getallpost();


        return postList;
    }


    @PostMapping("/addcomment")
    public Post addcomment(@RequestBody Postform postform) {

        Post post = null;

        logger.info(postform.getId() + "");

        logger.info(postform.getTitle());

        logger.info(postform.getPostComment());

        logger.info("updating post adding Comment");

        post = postService.updatePost(postform);


        logger.info(" Comment added successfully");
        return post;
    }


    @DeleteMapping("/post")
    public Post deletePost(@RequestParam int id) {

        logger.info("id :" + id);

        Post post = postService.deletePost(id);

        return post;
    }



}
