package com.backendserver.DigitronixProject.repositories;

import com.backendserver.DigitronixProject.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    // Tìm tag theo tên
    Tag findByTagName(String tagName);
    boolean existsByTagName(String tagName);

    // Xóa tag theo tên
    void deleteByTagName(String tagName);

    // Lấy danh sách các tag theo productId
    @Query("SELECT t FROM Tag t INNER JOIN t.products p WHERE p.id = :productId")
    List<Tag> findTagsByProductId(@Param("productId") Long productId);
}
