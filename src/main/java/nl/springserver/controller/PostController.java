package nl.springserver.controller;

import lombok.RequiredArgsConstructor;
import nl.springserver.dto.PostDto;
import nl.springserver.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
public class PostController {

    private final PostService postService;

    // 등록 (Create)
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody PostDto postDto) {
        Long id = postService.register(postDto);
        URI location = URI.create("/api/v1/post/" + id);
        return ResponseEntity.status(HttpStatus.CREATED).location(location).build(); // 201 Created
    }

    // 단건 조회 (Read)
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> get(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.get(id)); // 200 OK
    }

    // 다중 조회 (Read)
    @GetMapping
    public ResponseEntity<List<PostDto>> getList() {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getAll()); // 200 OK
    }

    // 수정 (Update)
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") Long id, @RequestBody PostDto postDto) {
        postDto.setId(id);
        postService.modify(postDto);
        return ResponseEntity.status(HttpStatus.OK).build(); // 200 OK
    }

    // 삭제 (Delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        postService.remove(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // 204 No Conten
    }


}
