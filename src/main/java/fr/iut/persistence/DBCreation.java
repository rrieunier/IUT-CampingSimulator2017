package fr.iut.persistence;

import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;

import java.io.File;
import java.util.EnumSet;

/**
 * Created by Sydpy on 2/27/17.
 */
public class DBCreation {

    public static void main(String[] args){

        String file = "camping-db-creation.sql";
        new File(file).delete();

        MetadataSources metadata = new MetadataSources(
                new StandardServiceRegistryBuilder().configure().build());

        SchemaExport export = new SchemaExport();
        export.setOutputFile(file);
        export.setDelimiter(";");
        export.setFormat(true);
        export.execute(EnumSet.of(TargetType.SCRIPT), SchemaExport.Action.CREATE, metadata.buildMetadata());
    }

}
