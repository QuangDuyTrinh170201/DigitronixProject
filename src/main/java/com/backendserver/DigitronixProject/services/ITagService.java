package com.backendserver.DigitronixProject.services;

import com.backendserver.DigitronixProject.dtos.TagDTO;
import com.backendserver.DigitronixProject.models.Tag;

import java.util.List;

public interface ITagService {
    Tag createTag(TagDTO tag);

    List<Tag> getAllTags();

    List<Tag> getTagsByProductId(Long productId);

    void deleteTag(Long tagId);

    Tag updateTag(Long tagId, Tag tag);
}
