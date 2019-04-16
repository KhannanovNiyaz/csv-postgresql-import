package com.javasampleapproach.batchcsvpostgresql.step;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.javasampleapproach.batchcsvpostgresql.model.Customer;

public class Processor implements ItemProcessor<Customer, Customer> {

    private static final Logger log = LoggerFactory.getLogger(Processor.class);

    @Override
    public Customer process(Customer customer) throws Exception {
        final String ssoid = customer.getSsoid().toUpperCase();
        final String ts = customer.getTs().toUpperCase();
        final String grp = customer.getGrp().toUpperCase();
        final String type = customer.getType().toUpperCase();
        final String subtype = customer.getSubtype().toUpperCase();
        final String url = customer.getUrl().toUpperCase();
        final String orgid = customer.getOrgid().toUpperCase();
        final String formid = customer.getFormid().toUpperCase();
        final String code = customer.getCode().toUpperCase();
        final String ltpa = customer.getLtpa().toUpperCase();
        final String sudirresponse = customer.getSudirresponse().toUpperCase();
        final String ymdh = customer.getYmdh().toUpperCase();

        final Customer fixedCustomer = new Customer( ssoid, ts, grp, type, subtype, url, orgid, formid, code, ltpa, sudirresponse, ymdh);

        log.info("Converting (" + customer + ") into (" + fixedCustomer + ")");

        return fixedCustomer;
    }
}
