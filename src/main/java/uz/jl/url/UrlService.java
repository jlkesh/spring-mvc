package uz.jl.url;

import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import uz.jl.url.dto.UrlCreateDTO;
import uz.jl.util.Utils;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UrlService {

    private final UrlDao dao;

    public UrlService(UrlDao dao) {
        this.dao = dao;
    }

    public void generate(UrlCreateDTO dto, BindingResult result) {

        if (!Utils.isParsable(dto.getValidTill()) && StringUtils.hasText(dto.getValidTill())) {
            result.addError(new FieldError(
                    "dto",
                    "validTill",
                    "Data for url expiration invalid"));
            return;
        } else if (!StringUtils.hasText(dto.getValidTill())) {
            dto.setExpiration(LocalDateTime.now(Clock.systemDefaultZone()).plusDays(10));
        } else dto.setExpiration(Utils.toLocalDateTime(dto.getValidTill()));

        if (dto.getExpiration().isBefore(LocalDateTime.now(Clock.systemDefaultZone()))) {
            result.addError(new FieldError(
                    "dto",
                    "validTill",
                    "Time is not valid is must be future time"));
            return;
        }
        String shortenedURL = DigestUtils.md5DigestAsHex(dto.getOriginalURL().getBytes());
        UrlDomain urlDomain = UrlDomain.builder()
                .originalUrl(dto.getOriginalURL())
                .createdAt(LocalDateTime.now())
                .description(dto.getDescription())
                .validTill(dto.getExpiration())
                .shortenedUrl(shortenedURL)
                .build();
        dao.save(urlDomain);
    }

    public List<UrlDomain> findAll() {
        return dao.getAll();
    }

    public UrlDomain findByShortenedUrl(String shortenedUrl) {
        return dao.findByShortenedUrl(shortenedUrl);
    }
}
