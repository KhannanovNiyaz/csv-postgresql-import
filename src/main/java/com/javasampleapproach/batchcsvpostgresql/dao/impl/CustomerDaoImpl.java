package com.javasampleapproach.batchcsvpostgresql.dao.impl;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.javasampleapproach.batchcsvpostgresql.dao.CustomerDao;
import com.javasampleapproach.batchcsvpostgresql.model.Customer;

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
        //		наименования как в базе данных
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

        long start = System.nanoTime();

        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);

        List<Customer> result = new ArrayList<Customer>();
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

        System.err.println("****************Time select****************");
        System.out.println(System.nanoTime() - start);

        return result;
    }

    @Override
    public List<Customer> loadAllCustomers() {
        String sql = "SELECT * FROM customer";
        return getCustomerDatabase(sql);


        /**по Хабру от Рамиля, вариант установления соединения сБД*/
//        List<Customer> result = new ArrayList<Customer>();
//
//        Connection connection = null;
//        String url = "jdbc:postgresql://localhost/test";
//        String name = "postgres";
//        String password = "12345";
//        try {
//            Class.forName("org.postgresql.Driver");
//            System.out.println("Драйвер подключен");
//            connection = DriverManager.getConnection(url, name, password);
//            System.out.println("Соединение установлено");
//            //2.PreparedStatement: предварительно компилирует запросы,
//            //которые могут содержать входные параметры
//            PreparedStatement preparedStatement = null;
//            // ? - место вставки нашего значеня
//            preparedStatement = connection.prepareStatement("SELECT * FROM customer");
//            //Устанавливаем в нужную позицию значения определённого типа
//            //выполняем запрос
//            ResultSet result2 = preparedStatement.executeQuery();
//
//            System.out.println("Выводим PreparedStatement");
//            while (result2.next()) {
//                System.out.println("Номер в выборке #" + result2.getRow()
//                        + "\t Номер в базе #" + result2.getInt("id")
//                        + "\t" + result2.getString("username"));
////                for (Map<String, Object> row : rows) {
////                    Customer customer = new Customer();
////                    customer.setSsoid((String) row.get("ssoid"));
////                    customer.setTs((String) row.get("ts"));
////                    customer.setGrp((String) row.get("grp"));
////                    customer.setType((String) row.get("type"));
////                    customer.setSubtype((String) row.get("subtype"));
////                    customer.setUrl((String) row.get("url"));
////                    customer.setOrgid((String) row.get("orgid"));
////                    customer.setFormid((String) row.get("formid"));
////                    customer.setCode((String) row.get("code"));
////                    customer.setLtpa((String) row.get("ltpa"));
////                    customer.setSudirresponse((String) row.get("sudirresponse"));
////                    customer.setYmdh((String) row.get("ymdh"));
////                    result.add(customer);
////                }
//
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;

    }

    @Override
    public List<Customer> loadFormUseLastHourse() {
        SimpleDateFormat sdf = new SimpleDateFormat("kk:mm:ss");
        long currentTime = Clock.systemDefaultZone().instant().getEpochSecond();
        String currentTimeString = sdf.format(new Date(currentTime * 1000));
        long oneHourAgo = currentTime - 60 * 60;
        String oneHourAgoString = sdf.format(new Date(oneHourAgo * 1000));
        System.out.println("currentTimeString = " + currentTimeString);
        System.out.println("oneHourAgoString = " + oneHourAgoString);

        long start = System.nanoTime();

        String sql = "SELECT ssoid, formid, ts FROM customer where to_char(to_timestamp(ts / 1000), 'HH24:MI:SS') between '" + oneHourAgoString + "' AND '" + currentTimeString + "'";
//        String sql = "SELECT ssoid, formid, ts  FROM customer WHERE ts >= '" + seconds + "'";
        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);
        List<Customer> result = new ArrayList<Customer>();
        for (Map<String, Object> row : rows) {
            Customer customer = new Customer();
            customer.setSsoid((String) row.get("ssoid"));
            customer.setFormid((String) row.get("formid"));
            customer.setTs((long) row.get("ts"));
            result.add(customer);
        }

        System.err.println("****************Time select****************");
        System.out.println(System.nanoTime() - start);

        return result;
    }

    @Override
    public List<Customer> userStatus() {
        long start = System.nanoTime();
        String sql = "SELECT * FROM customer WHERE grp LIKE 'dszn_%' AND subtype NOT IN ('send') ORDER BY grp DESC , subtype DESC";
//       ****************Time select**************** 47336526
        return getCustomerDatabase(sql);

        /** отсюда
         * http://qaru.site/questions/10984753/how-exactly-works-this-implementation-of-the-query-method-of-the-spring-jdbctemplate*/
//        ****************Time select**************** 33487335
//        List<Customer> dbCustomer = this.getJdbcTemplate().query(sql, new RowMapper<Customer>() {
//            @Override
//            public Customer mapRow(ResultSet resultSet, int i) throws SQLException {
//                Customer customer = new Customer();
//                customer.setSsoid(resultSet.getString("ssoid"));
//                customer.setTs(resultSet.getString("ts"));
//                customer.setGrp(resultSet.getString("grp"));
//                customer.setType(resultSet.getString("type"));
//                customer.setSubtype(resultSet.getString("subtype"));
//                customer.setUrl(resultSet.getString("url"));
//                customer.setOrgid(resultSet.getString("orgid"));
//                customer.setFormid(resultSet.getString("formid"));
//                customer.setCode(resultSet.getString("code"));
//                customer.setLtpa(resultSet.getString("ltpa"));
//                customer.setSudirresponse(resultSet.getString("sudirresponse"));
//                customer.setYmdh(resultSet.getString("ymdh"));
//                return customer;
//            }
//        });
//        System.err.println("****************Time select****************");
//        System.out.println( System.nanoTime() - start);
//        return dbCustomer;
    }


    @Override
    public List<Customer> topFiveUseForms() {
        String sql = "SELECT formid FROM (SELECT formid, COUNT(formid) as countNum FROM customer WHERE NOT (formid = '') GROUP BY formid ORDER BY countNum DESC LIMIT 5) AS result";
        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);

        List<Customer> result = new ArrayList<Customer>();
        for (Map<String, Object> row : rows) {
            Customer customer = new Customer();
            customer.setFormid((String) row.get("formid"));
            result.add(customer);
        }

        return result;
//        return getCustomerDatabase(sql);
    }
}
