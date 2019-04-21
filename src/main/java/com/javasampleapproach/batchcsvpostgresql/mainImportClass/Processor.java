package com.javasampleapproach.batchcsvpostgresql.mainImportClass;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.javasampleapproach.batchcsvpostgresql.model.Customer;

public class Processor implements ItemProcessor<Customer, Customer> {

    private static final Logger log = LoggerFactory.getLogger(Processor.class);

    @Override
    public Customer process(Customer customer) throws Exception {
        final String ssoid = customer.getSsoid();
        final long ts = customer.getTs();
        final String grp = customer.getGrp();
        final String type = customer.getType();
        final String subtype = customer.getSubtype();
        final String url = customer.getUrl();
        final String orgid = customer.getOrgid();
        final String formid = customer.getFormid();
        final String code = customer.getCode();
        final String ltpa = customer.getLtpa();
        final String sudirresponse = customer.getSudirresponse();
        final String ymdh = customer.getYmdh();

        final Customer fixedCustomer = new Customer( ssoid, ts, grp, type, subtype, url, orgid, formid, code, ltpa, sudirresponse, ymdh);

        log.info("Converting (" + customer + ") into (" + fixedCustomer + ")");

        return fixedCustomer;
    }
}
