package com.javasampleapproach.batchcsvpostgresql.dao.impl;

import com.javasampleapproach.batchcsvpostgresql.dao.CustomerDao;
import com.javasampleapproach.batchcsvpostgresql.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class CustomerDaoImpl extends JdbcDaoSupport implements CustomerDao {

    @Autowired
    DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    @Override
    public void insert(List<? extends Customer> Customers) {
        String sql = "INSERT INTO customer " + "(ssoid, ts, grp, type, subtype, url, orgid, formid, code, ltpa, sudirresponse, ymdh) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Customer customer = Customers.get(i);
                ps.setString(1, customer.getSsoid());
                ps.setLong(2, customer.getTs());
                ps.setString(3, customer.getGrp());
                ps.setString(4, customer.getType());
                ps.setString(5, customer.getSubtype());
                ps.setString(6, customer.getUrl());
                ps.setString(7, customer.getOrgid());
                ps.setString(8, customer.getFormid());
                ps.setString(9, customer.getCode());
                ps.setString(10, customer.getLtpa());
                ps.setString(11, customer.getSudirresponse());
                ps.setString(12, customer.getYmdh());
            }

            public int getBatchSize() {
                return Customers.size();
            }
        });
    }

    private List<Customer> getCustomerDatabase(String sql) {
        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);
        List<Customer> result = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            Customer customer = new Customer();
            customer.setSsoid((String) row.get("ssoid"));
            customer.setTs((long) row.get("ts"));
            customer.setGrp((String) row.get("grp"));
            customer.setType((String) row.get("type"));
            customer.setSubtype((String) row.get("subtype"));
            customer.setUrl((String) row.get("url"));
            customer.setOrgid((String) row.get("orgid"));
            customer.setFormid((String) row.get("formid"));
            customer.setCode((String) row.get("code"));
            customer.setLtpa((String) row.get("ltpa"));
            customer.setSudirresponse((String) row.get("sudirresponse"));
            customer.setYmdh((String) row.get("ymdh"));
            result.add(customer);
        }
        return result;
    }

    @Override
    public List<Customer> loadAllCustomers() {
        String sql = "SELECT * FROM customer";
        return getCustomerDatabase(sql);
    }

    @Override
    public List<Customer> loadFormUseLastHourse() {
        SimpleDateFormat sdf = new SimpleDateFormat("kk:mm:ss");
        long currentTime = Clock.systemDefaultZone().instant().getEpochSecond();
        String currentTimeString = sdf.format(new Date(currentTime * 1000));
        long oneHourAgo = currentTime - 60 * 60;
        String oneHourAgoString = sdf.format(new Date(oneHourAgo * 1000));

        String sql = "SELECT ssoid, formid, ts FROM customer where to_char(to_timestamp(ts / 1000), 'HH24:MI:SS') between '" + oneHourAgoString + "' AND '" + currentTimeString + "'";

        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);
        List<Customer> result = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            Customer customer = new Customer();
            customer.setSsoid((String) row.get("ssoid"));
            customer.setFormid((String) row.get("formid"));
            customer.setTs((long) row.get("ts"));
            result.add(customer);
        }
        return result;
    }

    @Override
    public List<Customer> userStatus() {
        String sql = "SELECT * FROM customer WHERE grp LIKE 'dszn_%' AND subtype NOT IN ('send') ORDER BY grp DESC , subtype DESC";
        return getCustomerDatabase(sql);
    }


    @Override
    public List<Customer> topFiveUseForms() {
        String sql = "SELECT formid FROM (SELECT formid, COUNT(formid) as countNum FROM customer WHERE NOT (formid = '') GROUP BY formid ORDER BY countNum DESC LIMIT 5) AS result";
        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);

        List<Customer> result = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            Customer customer = new Customer();
            customer.setFormid((String) row.get("formid"));
            result.add(customer);
        }

        return result;
    }
}
