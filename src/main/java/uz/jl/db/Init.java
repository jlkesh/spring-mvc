package uz.jl.db;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationStartupAware;
import org.springframework.core.metrics.ApplicationStartup;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

//@Component
public class Init implements ApplicationContextAware {
    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        JdbcTemplate jdbcTemplate = context.getBean(JdbcTemplate.class);
        String dropUrlTableIfExists = "drop table if exists url;";
        jdbcTemplate.execute(dropUrlTableIfExists);
        String createTable = """
                create table url(
                    id bigserial primary key ,
                    original_url varchar not null ,
                    shortened_url varchar not null ,
                    description varchar,
                    valid_till timestamp not null  default current_timestamp + interval '10 minutes',
                    created_at timestamp not null  default current_timestamp
                );
                """;
        jdbcTemplate.execute(createTable);
    }
}
