package com.backendserver.DigitronixProject.services;

import com.backendserver.DigitronixProject.dtos.TagDTO;
import com.backendserver.DigitronixProject.models.Tag;
import com.backendserver.DigitronixProject.models.User;
import com.backendserver.DigitronixProject.repositories.TagRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TagService implements ITagService {

    private final TagRepository tagRepository;

    @Override
    public Tag createTag(TagDTO tagDto) {
        String tagName = tagDto.getTagName();
        if(tagRepository.existsByTagName(tagName)){
            throw new DataIntegrityViolationException("Tag already exists");
        }
        Tag newTag = Tag.builder()
                .tagName(tagDto.getTagName())
                .build();
        return tagRepository.save(newTag);
    }

    @Override
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> getTagsByProductId(Long productId) {
        // Assume you have a method in the repository to fetch tags by product ID
        return tagRepository.findTagsByProductId(productId);
    }

    @Override
    public void deleteTag(Long tagId) {
        tagRepository.deleteById(tagId);
    }

    @Override
    public Tag updateTag(Long tagId, Tag tag) {
        Tag existingTag = tagRepository.findById(tagId)
                .orElseThrow(() -> new RuntimeException("Tag not found"));

        existingTag.setTagName(tag.getTagName());
        // Set other properties if needed

        return tagRepository.save(existingTag);
    }
}
