package uz.jl.url;


import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ReactiveTypeDescriptor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import uz.jl.mappers.UrlDomainMapper;

import java.lang.reflect.Type;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UrlDao {
    private final JdbcTemplate jdbcTemplate;

    public void save(UrlDomain urlDomain) {
        String sql = """
                insert into url(original_url, shortened_url, description, valid_till)
                values (?,?,?,?);
                """;
        jdbcTemplate.update(sql, urlDomain.getOriginalUrl(), urlDomain.getShortenedUrl(), urlDomain.getDescription(), urlDomain.getValidTill());
    }

    public List<UrlDomain> getAll() {
        return jdbcTemplate.query("select * from url;", BeanPropertyRowMapper.newInstance(UrlDomain.class));
    }

    public UrlDomain findByShortenedUrl(String shortenedUrl) {
        return jdbcTemplate.queryForObject(
                "select * from url t where t.shortened_url = ?",
                BeanPropertyRowMapper.newInstance(UrlDomain.class),
                shortenedUrl);
    }
}
