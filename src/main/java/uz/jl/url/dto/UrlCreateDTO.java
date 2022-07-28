package uz.jl.url.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import uz.jl.annotations.ValidLocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UrlCreateDTO {

    @NotBlank(message = "Original name can not be null")
    @Pattern(regexp = "^(https://|http://).*", message = "Must be valid link exl(olx.uz)")
    private String originalURL;

    @NotBlank
    private String description;

    @ValidLocalDateTime(message = "value for expiration field invalid")
    private String validTill;

    @JsonIgnore
    private LocalDateTime expiration;

}
