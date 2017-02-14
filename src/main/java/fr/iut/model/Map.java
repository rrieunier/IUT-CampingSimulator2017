package fr.iut.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Arrays;

/**
 * Created by Sydpy on 2/14/17.
 */
@Entity
public class Map {
    private byte[] image;
    private int id;

    @Basic
    @Column(name = "image", nullable = false)
    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Map map = (Map) o;

        if (id != map.id) return false;
        if (!Arrays.equals(image, map.image)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(image);
        result = 31 * result + id;
        return result;
    }
}
