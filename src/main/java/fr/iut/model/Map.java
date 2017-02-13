package fr.iut.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Arrays;

/**
 * Created by Sydpy on 2/13/17.
 */
@Entity
public class Map {
    private byte[] image;

    @Basic
    @Column(name = "image", nullable = false)
    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Map map = (Map) o;

        if (!Arrays.equals(image, map.image)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(image);
    }
}
