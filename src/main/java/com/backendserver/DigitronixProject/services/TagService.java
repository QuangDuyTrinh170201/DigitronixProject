package com.backendserver.DigitronixProject.services;

import com.backendserver.DigitronixProject.dtos.TagDTO;
import com.backendserver.DigitronixProject.exceptions.DataNotFoundException;
import com.backendserver.DigitronixProject.models.Tag;
import com.backendserver.DigitronixProject.repositories.TagRepository;
import com.backendserver.DigitronixProject.responses.TagResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<TagResponse> getAllTags() {
        List<Tag> tags = tagRepository.findAll();
        return tags.stream()
                .map(TagResponse::fromTag)
                .collect(Collectors.toList());
    }


//    @Override
//    public List<TagResponse> getTagsByProductId(Long productId) {
//        // Assume you have a method in the repository to fetch tags by product ID
//        List<Tag> findTag = tagRepository.findTagsByProductId(productId);
//        return findTag.stream()
//                .map(TagResponse::fromTag)
//                .collect(Collectors.toList());
//    }

    @Override
    public List<TagResponse> getTagsByName(String name){
        List<Tag> findTag = tagRepository.findByTagNameList(name);
        return findTag.stream()
                .map(TagResponse::fromTag)
                .collect(Collectors.toList());
    }

    @Override
    public String deleteTag(Long tagId) throws DataNotFoundException {
        Optional<Tag> findTag = tagRepository.findById(tagId);
        if(findTag.isEmpty()){
            throw new DataNotFoundException("Cannot find this tag!");
        }
        tagRepository.deleteById(tagId);
        return "Delete Successfully";
    }

    @Override
    public Tag getById(long id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found!"));
    }

    @Override
    @Transactional
    public Tag update(long tagId, TagDTO tagDTO) throws Exception{
        Tag existingTag = getById(tagId);

        // Check the new category has existed before
        Optional<Tag> existingTagWithName = tagRepository.findByTagName(tagDTO.getTagName());
        if (existingTagWithName.isPresent() && existingTagWithName.get().getId() != tagId) {
            throw new RuntimeException("A category with the same name already exists.");
        }

        existingTag.setTagName(tagDTO.getTagName());
        return tagRepository.save(existingTag);
    }
}
