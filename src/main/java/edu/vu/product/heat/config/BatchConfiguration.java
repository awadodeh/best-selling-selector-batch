package edu.vu.product.heat.config;

import edu.vu.product.heat.batch.listener.JobCompletionNotificationListener;
import edu.vu.product.heat.batch.processor.ProductItemProcessor;
import edu.vu.product.heat.model.Product;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;

    // tag::readerwriterprocessor[]

    @Bean
    public FlatFileItemReader<Product> reader() {

        FlatFileItemReader<Product> reader = new FlatFileItemReader<Product>();
        reader.setResource(new ClassPathResource("/edu.vu.product.heat/sample-data.csv"));
        reader.setLineMapper(new DefaultLineMapper<Product>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[]{"name", "price", "rank"});
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Product>() {{
                setTargetType(Product.class);
            }});
        }});
        return reader;
    }

    @Bean
    public ProductItemProcessor processor() {
        return new ProductItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Product> writer() {
        JdbcBatchItemWriter<Product> writer = new JdbcBatchItemWriter<Product>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("INSERT INTO products (product_id, product_name, product_price, product_rank) VALUES (:id, :name, :price, :rank)");
        writer.setDataSource(dataSource);
        return writer;
    }
    // end::readerwriterprocessor[]

    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1())
                .end()
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
//        chunk() is prefixed <Person,Person> because it’s a generic method.
// This represents the input and output types of each "chunk" of processing,
// and lines up with ItemReader<Product> and ItemWriter<Product>.
                .<Product, Product>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }
    // end::jobstep[]
}
