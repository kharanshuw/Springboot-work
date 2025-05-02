package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.PostComment;

public interface PostCommentRepo extends JpaRepository<PostComment,Integer>{

    

}
