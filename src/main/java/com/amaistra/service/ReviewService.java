package com.amaistra.service;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amaistra.domain.Post;
import com.amaistra.domain.Review;
import com.amaistra.repo.ReviewRepository;

import lombok.AllArgsConstructor;

@Service
public class ReviewService {
    
    @Value("${upload.path}")
    private String uploadPath;
    
    private final ReviewRepository reviewRepository;    

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<Review> getAll() {
        List<Review> reviews = reviewRepository.findAll();
        if(reviews == null) {
            return Collections.emptyList();
        }
        reviews.sort((Review one, Review two) -> two.getId().compareTo(one.getId()));
        return reviews;
    }

    public void create(Review review, MultipartFile file) throws IllegalStateException, IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadDir + "/" + resultFileName));
            review.setImageName(resultFileName);
        }
        reviewRepository.save(review);
    }

    public Review getById(Long id) {
        return reviewRepository.findById(id).get();
    }

    public void edit(Review review, MultipartFile file) throws IllegalStateException, IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            if(review.getImageName() != null && !review.getImageName().isEmpty()) {
                File myFile = new File(uploadPath + "/" + review.getImageName());
                myFile.delete();
            }
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadDir + "/" + resultFileName));
            review.setImageName(resultFileName);
        }
        reviewRepository.save(review);
    }

    public void delete(Long id) {
        Review review = reviewRepository.findById(id).get();
        if(review.getImageName() != null && !review.getImageName().isEmpty()) {
            File myFile = new File(uploadPath + "/" + review.getImageName());
            myFile.delete();
        }
        reviewRepository.delete(review);
    }
    
    

}
