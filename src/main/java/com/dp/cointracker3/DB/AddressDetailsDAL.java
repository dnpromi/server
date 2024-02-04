package com.dp.cointracker3.DB;

import com.dp.cointracker3.model.AddressDetail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class AddressDetailsDAL {

    public AddressDetailsDAL() {
        // TODO fix this path before running in a new env.
        String URL = "jdbc:sqlite:C:\\Users\\dpanda\\IdeaProjects\\cointracker3\\identifier.sqlite";
        Configuration configuration = new Configuration()
                .addPackage("com.dp.cointracker3.model.*")
                .addAnnotatedClass(AddressDetail.class)
                .setProperty("hibernate.connection.driver_class", "org.sqlite.JDBC")
                .setProperty("hibernate.connection.url", URL)
                .setProperty("hibernate.dialect", "org.hibernate.community.dialect.SQLiteDialect")
                .setProperty("hibernate.show_sql", "true")
                .setProperty("hibernate.hdm2ddl.auto", "create-drop");

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    private SessionFactory sessionFactory;

    public void upsertAddressDetail(String address, String detail) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            AddressDetail addressDetail = session.get(AddressDetail.class, address);
            if (addressDetail != null) {
                // Address detail exists, update it
                addressDetail.setDetail(detail);
                session.update(addressDetail);
                System.out.println("Address detail updated successfully.");
            } else {
                // Address detail doesn't exist, insert a new one
                addressDetail = new AddressDetail();
                addressDetail.setAddress(address);
                addressDetail.setDetail(detail);
                session.save(addressDetail);
                System.out.println("Address detail inserted successfully.");
            }

            transaction.commit();
        }
    }

    /**
     * Returns null if not present otherwise will return the detail of the address.
     * @param address
     * @return
     */
    public String getAddressDetail(String address) {
        try (Session session = sessionFactory.openSession()) {
            AddressDetail addressDetail = session.get(AddressDetail.class, address);
            if (addressDetail != null) {
                return addressDetail.getDetail();
            } else {
                System.out.println("No records found for the given address.");
            }
        }
        return null;
    }

    public void deleteAddressDetail(String address) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            AddressDetail addressDetail = session.get(AddressDetail.class, address);
            if (addressDetail != null) {
                session.delete(addressDetail);
                transaction.commit();
                System.out.println("Address detail deleted successfully.");
            } else {
                System.out.println("No records found for the given address.");
            }
        }
    }
}
