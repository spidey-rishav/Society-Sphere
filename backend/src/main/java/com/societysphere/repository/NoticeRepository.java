package com.societysphere.repository;

import com.societysphere.entity.Notice;
import com.societysphere.entity.Society;
import com.societysphere.enums.NoticeAudience;
import com.societysphere.enums.NoticePriority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {

    List<Notice> findBySociety(Society society);

    // Notice entity has 'audience' field, not 'noticeAudience'
    List<Notice> findByAudience(NoticeAudience audience);

    // Notice entity has 'priority' field, not 'noticePriority'
    List<Notice> findByPriority(NoticePriority priority);

    List<Notice> findByActive(boolean active);

    List<Notice> findBySocietyAndAudience(Society society, NoticeAudience audience);

    List<Notice> findBySocietyAndPriority(Society society, NoticePriority priority);

    List<Notice> findBySocietyAndActive(Society society, boolean active);

    long countBySociety(Society society);
}