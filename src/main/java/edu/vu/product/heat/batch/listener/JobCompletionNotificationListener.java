package edu.vu.product.heat.batch.listener;

import edu.vu.product.heat.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results");

            List<Product> results = jdbcTemplate.query("SELECT product_name, product_price, product_rank FROM products", new RowMapper<Product>() {
                @Override
                public Product mapRow(ResultSet rs, int row) throws SQLException {
                    return new Product(rs.getString(1), rs.getLong(2), rs.getLong(3));
                }
            });

            for (Product product : results) {
                log.info("Found <" + product + "> in the database.");
            }

        }
    }
}
