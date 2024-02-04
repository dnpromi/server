package com.dp.cointracker3.DB;

import com.dp.cointracker3.model.UserAddress;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

import java.util.List;
import java.util.UUID;

public class UserAddressDAL {

    public UserAddressDAL() {
        // TODO fix this path before running in a new env.
        String URL = "jdbc:sqlite:C:\\Users\\dpanda\\IdeaProjects\\cointracker3\\identifier.sqlite";
        Configuration configuration = new Configuration()
                .addPackage("com.dp.cointracker3.model.*")
                .addAnnotatedClass(UserAddress.class)
                .setProperty("hibernate.connection.driver_class", "org.sqlite.JDBC")
                .setProperty("hibernate.connection.url", URL)
                .setProperty("hibernate.dialect", "org.hibernate.community.dialect.SQLiteDialect")
                .setProperty("hibernate.show_sql", "true")
                .setProperty("hibernate.hdm2ddl.auto", "create-drop");

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    SessionFactory sessionFactory;

    public String addAddress(String user, String nickName, String address) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            // Ideally should validate if this already exists
            String newId = UUID.randomUUID().toString();

            // Create a new UserAddress object
            UserAddress userAddress = new UserAddress();
            userAddress.setAddressId(newId);
            userAddress.setUser(user);
            userAddress.setNickName(nickName);
            userAddress.setAddress(address);

            // Save the new UserAddress to the database
            session.save(userAddress);

            session.getTransaction().commit();

            return newId;
        }
    }

    public void deleteAddress(String user, String addressId) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            // Retrieve the UserAddress to delete
            UserAddress userAddress = session.get(UserAddress.class, addressId);

            if (userAddress != null && user.equals(userAddress.getUser())) {
                // Check if the address belongs to the specified user
                session.delete(userAddress);
                session.getTransaction().commit();
            } else {
                // Address not found or does not belong to the user
                session.getTransaction().rollback();
            }
        }
    }

    public List<UserAddress> getAllAddresses(String user) {
        List<UserAddress> res;

        try (Session session = sessionFactory.openSession()){
            Query<UserAddress> query = session.createQuery("from UserAddress ua WHERE ua.user=:user", UserAddress.class);
            query.setParameter("user", user);
            res = query.list();
        }
        return res;
    }

    public UserAddress getAddress(String addressId) {

        List<UserAddress> userAddresses;
        try (Session session = sessionFactory.openSession()) {
            Query<UserAddress> query = session.createQuery("from UserAddress ua WHERE ua.addressId=:addressId", UserAddress.class);
            query.setParameter("addressId", addressId);
            userAddresses = query.list();
        }
        if (userAddresses.isEmpty()) {
            return null;
        }
        return userAddresses.stream().findFirst().get();
    }
}
