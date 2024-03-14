package com.backendserver.DigitronixProject.constrollers;

import com.backendserver.DigitronixProject.dtos.TagDTO;
import com.backendserver.DigitronixProject.models.Tag;
import com.backendserver.DigitronixProject.services.ITagService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("")
    @PreAuthorize("hasRole('ROLE_DIRECTOR')")
    public ResponseEntity<List<Tag>> getAllTags() {
        List<Tag> tags = tagService.getAllTags();
        return ResponseEntity.ok(tags);
    }

    @GetMapping("/{productId}")
    @PreAuthorize("hasRole('ROLE_DIRECTOR')")
    public ResponseEntity<List<Tag>> getTagsByProductId(@PathVariable Long productId) {
        List<Tag> tags = tagService.getTagsByProductId(productId);
        return ResponseEntity.ok(tags);
    }

    @DeleteMapping("/{tagId}")
    @PreAuthorize("hasRole('ROLE_DIRECTOR')")
    public ResponseEntity<Void> deleteTag(@PathVariable Long tagId) {
        tagService.deleteTag(tagId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{tagId}")
    @PreAuthorize("hasRole('ROLE_DIRECTOR')")
    public ResponseEntity<Tag> updateTag(@PathVariable Long tagId, @RequestBody Tag tag) {
        Tag updatedTag = tagService.updateTag(tagId, tag);
        return ResponseEntity.ok(updatedTag);
    }
}
