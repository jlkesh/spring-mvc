package uz.jl.url;

import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import uz.jl.url.dto.UrlCreateDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class UrlService {

    private final UrlDao dao;

    public UrlService(UrlDao dao) {
        this.dao = dao;
    }

    public void generate(UrlCreateDTO dto) {

        LocalDateTime validTill = Objects.nonNull(dto.getValidTill())
                ? LocalDateTime.parse(dto.getValidTill())
                : LocalDateTime.now().plusMinutes(10);
        String shortenedURL = DigestUtils.md5DigestAsHex(dto.getOriginalURL().getBytes());
        UrlDomain urlDomain = UrlDomain.builder()
                .originalUrl(dto.getOriginalURL())
                .createdAt(LocalDateTime.now())
                .description(dto.getDescription())
                .validTill(validTill)
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
