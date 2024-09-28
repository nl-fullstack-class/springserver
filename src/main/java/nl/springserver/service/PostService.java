package nl.springserver.service;

import lombok.RequiredArgsConstructor;
import nl.springserver.dto.PostDto;
import nl.springserver.entity.Post;
import nl.springserver.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    // 등록
    @Transactional
    public Long register(PostDto postDto) {
        Post post = Post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .build();
        return postRepository.save(post).getId();
    }

    // 단건 조회
    @Transactional(readOnly = true)
    public PostDto get(Long id) {
        return postRepository.findById(id)
                .map(post -> PostDto.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .build()
                )
                .orElseThrow(() -> new NoSuchElementException("Post not found: " + id));
    }

    // 다중 조회
    @Transactional(readOnly = true)
    public List<PostDto> getAll() {
        return postRepository.findAll().stream()
                .map(post -> PostDto.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .build()
                )
                .collect(Collectors.toList());
    }

    // 수정
    @Transactional
    public void modify(PostDto postDto) {
        // 조회
        Post post = postRepository.findById(postDto.getId())
                .orElseThrow(() -> new NoSuchElementException("Post not found: " + postDto.getId()));
        // 변경 내용 반영 (dirty checking)
        post.changeTitle(postDto.getTitle());
        post.changeContent(postDto.getContent());
    }

    // 삭제
    @Transactional
    public void remove(Long id) {
        postRepository.deleteById(id);
    }


}
