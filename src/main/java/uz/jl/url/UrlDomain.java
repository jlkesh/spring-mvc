package uz.jl.url;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UrlDomain {
    private Long id;
    private String originalUrl;
    private String shortenedUrl;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime validTill;

    @Builder.Default
    private Long createdBy = -1L;
}
