package nl.springserver.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDto {

    private Long id; // null 가능

    @NonNull
    private String title;

    @NonNull
    private String content;
}
