package com.instagram.instagram.service;

import com.instagram.instagram.exception.ResourceNotFoundException;
import com.instagram.instagram.models.Post;
import com.instagram.instagram.repo.PostRepository;
import com.instagram.instagram.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Post createPost(Post post){
        if(userRepository.existsById(post.getUser().getId())) {
            return postRepository.save(post);
        } else {
            throw new ResourceNotFoundException(String.format("User with id %s not found! Cannot add the post", post.getUser().getId()));
        }
    }

    public Post findPostById(int id){
        return postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Post with id %s not found!", id)));
    }

    public void deletePostById(int id){
        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException(String.format("Post with id %s not found!", id));
        }
    }

    public List<Post> findAllPosts(){
        return postRepository.findAll();
    }


}
