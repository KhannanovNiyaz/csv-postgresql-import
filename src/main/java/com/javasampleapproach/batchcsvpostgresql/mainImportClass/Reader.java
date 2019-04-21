package com.javasampleapproach.batchcsvpostgresql.mainImportClass;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;

import com.javasampleapproach.batchcsvpostgresql.model.Customer;

public class Reader {
    public static FlatFileItemReader<Customer> reader(String path) {
        FlatFileItemReader<Customer> reader = new FlatFileItemReader<Customer>();

        reader.setResource(new ClassPathResource(path));
        reader.setLinesToSkip(1);
        reader.setLineMapper(new DefaultLineMapper<Customer>() {
            {
                setLineTokenizer(new DelimitedLineTokenizer() {
                    {
                        setDelimiter(";");
                        setNames(new String[]{"ssoid", "ts", "grp", "type", "subtype", "url", "orgid", "formid", "code", "ltpa", "sudirresponse", "ymdh"});
                    }
                });
                setFieldSetMapper(new BeanWrapperFieldSetMapper<Customer>() {
                    {
                        setTargetType(Customer.class);
                    }
                });
            }
        });
        return reader;
    }
}
