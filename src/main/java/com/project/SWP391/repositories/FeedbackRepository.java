package com.project.SWP391.repositories;

import com.project.SWP391.entities.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback,Long> {

    List<Feedback> findAllByUserId(Long id);

    List<Feedback> findAllByLaundryServiceId(Long id);
}
