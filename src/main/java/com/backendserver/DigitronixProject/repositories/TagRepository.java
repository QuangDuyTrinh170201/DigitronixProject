package com.backendserver.DigitronixProject.repositories;

import com.backendserver.DigitronixProject.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    // Tìm tag theo tên
    Optional<Tag> findByTagName(String tagName);
//    List<Tag> findByTagNameList(String tagName);
    boolean existsByTagName(String tagName);

    // Xóa tag theo tên
    void deleteByTagName(String tagName);

    // Lấy danh sách các tag theo productId
    @Query("SELECT t FROM Tag t INNER JOIN t.products p WHERE p.id = :productId")
    List<Tag> findTagsByProductId(@Param("productId") Long productId);

    @Query("SELECT t FROM Tag t WHERE t.tagName LIKE %:tagName%")
    List<Tag> findByTagNameList(@Param("tagName") String tagName);

}
