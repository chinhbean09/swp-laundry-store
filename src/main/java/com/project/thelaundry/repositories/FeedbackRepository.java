package com.project.thelaundry.repositories;

import com.project.SWP391.entities.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback,Long> {

    List<Feedback> findAllByUserId(Long id);
}
