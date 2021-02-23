package com.amaistra.service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amaistra.domain.Post;
import com.amaistra.repo.PostRepository;

import lombok.AllArgsConstructor;

@Service
public class PostService {
    
    @Value("${upload.path}")
    private String uploadPath;
    
    private final PostRepository postRepository;
    
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
    
    public void create(Post post, MultipartFile file) throws IllegalStateException, IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadDir + "/" + resultFileName));
            post.setImageName(resultFileName);
        }
        postRepository.save(post);
    }

    public List<Post> getAll() {
        List<Post> posts = postRepository.findAll();
        if(posts == null) {
            return Collections.emptyList();
        }
        posts.sort((Post one, Post two) -> two.getId().compareTo(one.getId()));
        return posts;
    }
    
    public Post getById(Long id) {
        return postRepository.findById(id).get();
    }
    
    public void edit(Post post, MultipartFile file) throws IllegalStateException, IOException {
        
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            if(post.getImageName() != null && !post.getImageName().isEmpty()) {
                File myFile = new File(uploadPath + "/" + post.getImageName());
                myFile.delete();
            }
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadDir + "/" + resultFileName));
            post.setImageName(resultFileName);
        }
        
        postRepository.save(post);
    }
    
    public void delete(Long id) {
        
        Post post = postRepository.findById(id).get();
        if(post.getImageName() != null && !post.getImageName().isEmpty()) {
            File myFile = new File(uploadPath + "/" + post.getImageName());
            myFile.delete();
        }
        postRepository.delete(post);
    }

  

}
