package com.backendserver.DigitronixProject.controllers;

import com.backendserver.DigitronixProject.dtos.TagDTO;
import com.backendserver.DigitronixProject.exceptions.DataNotFoundException;
import com.backendserver.DigitronixProject.models.Tag;
import com.backendserver.DigitronixProject.responses.TagResponse;
import com.backendserver.DigitronixProject.services.ITagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/tags")
@RequiredArgsConstructor
public class TagController {

    private final ITagService tagService;

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_DIRECTOR')")
    public ResponseEntity<Tag> createTag(@RequestBody TagDTO tag) {
        Tag newTag = tagService.createTag(tag);
        return ResponseEntity.ok(newTag);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TagResponse>> getAllTags() {
        List<TagResponse> tagResponses = tagService.getAllTags();
        return ResponseEntity.ok(tagResponses);
    }

    @GetMapping("/withName")
    @PreAuthorize("hasRole('ROLE_DIRECTOR')")
    public ResponseEntity<?> getTagsByProductId(@RequestParam(defaultValue = "") String name) {
        List<TagResponse> tags = tagService.getTagsByName(name);
        return ResponseEntity.ok(tags);
    }

    @DeleteMapping("/{tagId}")
    @PreAuthorize("hasRole('ROLE_DIRECTOR')")
    public ResponseEntity<String> deleteTag(@PathVariable Long tagId) {
        try{
            String message = tagService.deleteTag(tagId);
            return ResponseEntity.ok(message);
        }catch (DataNotFoundException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_DIRECTOR')")
    public ResponseEntity<?> updateTag(@PathVariable long id, @RequestBody TagDTO tagDTO) {
        try {
            TagResponse updateTag = TagResponse.fromTag(tagService.update(id, tagDTO));
            return ResponseEntity.ok(updateTag);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
