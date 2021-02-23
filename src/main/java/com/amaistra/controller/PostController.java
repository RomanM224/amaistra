package com.amaistra.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.amaistra.domain.Post;
import com.amaistra.service.PostService;



@Controller
@RequestMapping("/posts")
public class PostController {

    
    @Value("${upload.path}")
    private String uploadPath;

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ModelAndView getPosts() {
        ModelAndView modelAndView = new ModelAndView("post/posts");
        List<Post> posts = postService.getAll();
        modelAndView.addObject("posts", posts);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView("post/createPost");

        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView create(@ModelAttribute Post post, @RequestParam("file") MultipartFile file)
            throws IllegalStateException, IOException {
        ModelAndView modelAndView = new ModelAndView("home");
        
        postService.create(post, file);

        return modelAndView;
    }

    @GetMapping("{id}")
    public ModelAndView getPost(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("post/post");
        Post post = postService.getById(id);
        modelAndView.addObject("post", post);

        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editPost(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("post/editPost");
        Post post = postService.getById(id);
        modelAndView.addObject("post", post);

        return modelAndView;
    }

    @PostMapping("/edit")
    public ModelAndView editPost(@ModelAttribute Post post, @RequestParam("file") MultipartFile file)
            throws IllegalStateException, IOException {
        ModelAndView modelAndView = new ModelAndView("home");
        postService.edit(post, file);

        return modelAndView;
    }
    
    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("home");
        postService.delete(id);
        List<Post> posts = postService.getAll();
        modelAndView.addObject("posts", posts);
        return modelAndView;
    }

}
