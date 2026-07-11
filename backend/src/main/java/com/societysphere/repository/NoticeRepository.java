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

    List<Notice> findByNoticeAudience(NoticeAudience noticeAudience);

    List<Notice> findByNoticePriority(NoticePriority noticePriority);

    List<Notice> findBySocietyAndNoticeAudience(
            Society society,
            NoticeAudience noticeAudience
    );

    List<Notice> findBySocietyAndNoticePriority(
            Society society,
            NoticePriority noticePriority
    );

    long countBySociety(Society society);

}