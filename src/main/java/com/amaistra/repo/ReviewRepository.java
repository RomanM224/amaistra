package com.amaistra.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amaistra.domain.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>{

    
}
