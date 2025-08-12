package com.project.questapp.services;

import com.project.questapp.dto.PostCreateRequest;
import com.project.questapp.dto.PostUpdateRequest;
import com.project.questapp.entities.Post;
import com.project.questapp.entities.User;
import com.project.questapp.repos.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    PostRepository postRepository;
    private UserService userService;

    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    public List<Post> getAllPosts(Optional<Long> userId) {
        if (userId.isPresent()) {
            return postRepository.findByUserId(userId.get());
        }
        return postRepository.findAll();
    }

    public Post getOnePostById(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }

    public Post createOnePost(PostCreateRequest postCreateRequest) {
        User user = userService.getOneUser(postCreateRequest.getUserId());
        if (user == null) {
            return null;
        }else {
            Post post = new Post();
            post.setId(postCreateRequest.getId());
            post.setText(postCreateRequest.getText());
            post.setTitle(postCreateRequest.getTitle());
            post.setUser(user);
            return postRepository.save(post);
        }
    }

    public Post updateOnePostById(Long postId, PostUpdateRequest postUpdateRequest) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            post.setText(postUpdateRequest.getText());
            post.setTitle(postUpdateRequest.getTitle());
            return postRepository.save(post);
        }
        return null;
    }

    public void deleteOnePostById(Long postId) {
        postRepository.deleteById(postId);
    }
}
