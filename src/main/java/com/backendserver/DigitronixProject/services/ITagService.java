package com.backendserver.DigitronixProject.services;

import com.backendserver.DigitronixProject.dtos.TagDTO;
import com.backendserver.DigitronixProject.exceptions.DataNotFoundException;
import com.backendserver.DigitronixProject.models.Tag;
import com.backendserver.DigitronixProject.responses.TagResponse;

import java.util.List;

public interface ITagService {
    Tag createTag(TagDTO tag);

    public List<TagResponse> getAllTags();

//    List<TagResponse> getTagsByProductId(Long productId);
    List<TagResponse> getTagsByName(String name);

    String deleteTag(Long tagId) throws DataNotFoundException;

    public Tag update(long tagId, TagDTO tagDTO) throws Exception;

    public Tag getById(long id);
}
