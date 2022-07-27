package uz.jl.mappers;

import org.springframework.jdbc.core.RowMapper;
import uz.jl.url.UrlDomain;

import javax.swing.tree.TreePath;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UrlDomainMapper implements RowMapper<UrlDomain> {
    @Override
    public UrlDomain mapRow(ResultSet rs, int rowNum) throws SQLException {
        return UrlDomain.builder()
                .id(rs.getLong("id"))
                .originalUrl(rs.getString("original_url"))
                .shortenedUrl(rs.getString("shortened_url"))
                .description(rs.getString("description"))
                .validTill(rs.getTimestamp("valid_till").toLocalDateTime())
                .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                .build();
    }
}
