package com.societysphere.repository;

import com.societysphere.entity.Complaint;
import com.societysphere.entity.ComplaintFeedback;
import com.societysphere.entity.Resident;
import com.societysphere.entity.Society;
import com.societysphere.enums.FeedbackRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComplaintFeedbackRepository extends JpaRepository<ComplaintFeedback, Long> {

    Optional<ComplaintFeedback> findByComplaint(Complaint complaint);

    List<ComplaintFeedback> findByResident(Resident resident);

    List<ComplaintFeedback> findBySociety(Society society);

    List<ComplaintFeedback> findByFeedbackRating(FeedbackRating feedbackRating);

    List<ComplaintFeedback> findBySocietyAndFeedbackRating(
            Society society,
            FeedbackRating feedbackRating
    );

    long countBySociety(Society society);

}