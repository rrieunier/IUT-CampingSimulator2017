package fr.iut.persistence;

import fr.iut.persistence.domain.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.tool.hbm2ddl.SchemaExport;

/**
 * Created by Sydpy on 2/16/17.
 */
public class CreateDBSchema {

    public static void main(String[] args){
        Configuration configuration = new Configuration();

        configuration
                .addAnnotatedClass(Authorization.class)
                .addAnnotatedClass(Client.class)
                .addAnnotatedClass(Employee.class)
                .addAnnotatedClass(Location.class)
                .addAnnotatedClass(Log.class)
                .addAnnotatedClass(Map.class)
                .addAnnotatedClass(Notification.class)
                .addAnnotatedClass(Problem.class)
                .addAnnotatedClass(Product.class)
                .addAnnotatedClass(Purchase.class)
                .addAnnotatedClass(Reservation.class)
                .addAnnotatedClass(Restocking.class)
                .addAnnotatedClass(Spot.class)
                .addAnnotatedClass(Supplier.class)
                .addAnnotatedClass(Task.class)
                .setProperty(Environment.USER, "root")
                .setProperty(Environment.PASS, "root")
                .setProperty(Environment.URL, "jdbc:mysql://localhost/Camping")
                .setProperty(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect")
                .setProperty(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");

        SchemaExport schema = new SchemaExport(configuration);

        schema.setOutputFile("camping-db-creation.sql");

        schema.drop(true, true);
        schema.create(true, true);

    }
}
