package com.example.demo.service;

import com.example.demo.dto.Postform;
import com.example.demo.entity.Post;

import java.util.List;

public interface PostService {
    public Post savePost(Post post);

    public List<Post> getallpost();

    public Post getpostbyid(int id);

    public Post updatePost(Postform post);

    public Post deletePost(int id);
}
