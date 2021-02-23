package com.amaistra.controller;

import java.io.IOException;
import java.util.List;

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
import com.amaistra.domain.Review;
import com.amaistra.service.ReviewService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
@RequestMapping("/reviews")
public class ReviewController {
    
    private final ReviewService reviewService;
    
    @GetMapping
    public ModelAndView getReviews() {
       ModelAndView modelAndView = new ModelAndView("review/reviews");
       List<Review> reviews = reviewService.getAll();
       modelAndView.addObject("reviews", reviews);
       return modelAndView;
    }
    
    @GetMapping("/create")
    public ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView("review/createReview");
        
        return modelAndView;
    }
    
    @PostMapping("/create")
    public ModelAndView create(@ModelAttribute Review review, @RequestParam("file") MultipartFile file) throws IllegalStateException, IOException {
        ModelAndView modelAndView = new ModelAndView("home");
        reviewService.create(review, file);
        return modelAndView;
    }
    
    @GetMapping("/{id}")
    public ModelAndView getById(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("review/review");
        Review review = reviewService.getById(id);
        modelAndView.addObject("review", review);
        return modelAndView;
    }
    
    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("review/editReview");
        Review review = reviewService.getById(id);
        modelAndView.addObject("review", review);
        return modelAndView;
    }
    
    @PostMapping("/edit")
    public ModelAndView edit(@ModelAttribute Review review, @RequestParam("file") MultipartFile file) throws IllegalStateException, IOException {
        ModelAndView modelAndView = new ModelAndView("home");
        reviewService.edit(review, file);
        return modelAndView;
    }
    
    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("home");
        reviewService.delete(id);
        return modelAndView;
    }
    

}
