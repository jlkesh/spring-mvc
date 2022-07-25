package uz.jl.url;


import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UrlDao {
    private static final List<UrlDomain> URL_DOMAIN_LIST = new ArrayList<>();

    public void save(UrlDomain urlDomain) {
        URL_DOMAIN_LIST.add(urlDomain);
    }

    public List<UrlDomain> getAll() {
        return URL_DOMAIN_LIST;
    }

    public UrlDomain findByShortenedUrl(String shortenedUrl) {
        // TODO: 7/25/2022 customize exception
        return URL_DOMAIN_LIST.stream().filter(urlDomain -> urlDomain.getShortenedUrl().equals(shortenedUrl))
                .findFirst().orElseThrow(() -> new RuntimeException("URL not found"));
    }
}
