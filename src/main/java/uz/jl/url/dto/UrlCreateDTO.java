package uz.jl.url.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UrlCreateDTO {
    private String originalURL;
    private String description;
    private String validTill;
}
