package com.example.demo.controller;

import com.example.demo.dto.Postform;
import com.example.demo.entity.Post;
import com.example.demo.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class TestController {

    public Logger logger = LoggerFactory.getLogger(TestController.class);
    
    private PostService postService;

    @Autowired
    public TestController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/addpost")
    public String addpost(Model model) {
        Postform postform = new Postform();

        model.addAttribute("post", postform);

        return "addpost.html";
    }


    @PostMapping("/addpost")
    public String processaddpost(@ModelAttribute Postform postform) {
        
        logger.info("processaddpost called");

        Post post = new Post();

        post.setTitle(postform.getTitle());
        
        Post savedPost = postService.savePost(post);
        
        logger.info("saved post "+post.toString());

        return "redirect:/addpost";
    }


    @GetMapping("/")
    public String test() {
        return "test";
    }
    
    
}
