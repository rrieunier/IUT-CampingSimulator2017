package fr.iut.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by roman on 10/02/17.
 */
@Entity
@Table(name = "SpotType", schema = "CampingSimulator", catalog = "")
public class SpotTypeEntity {
    private int idSpotType;
    private String label;
    private Collection<SpotEntity> spotsByIdSpotType;

    @Id
    @Column(name = "idSpotType", nullable = false)
    public int getIdSpotType() {
        return idSpotType;
    }

    public void setIdSpotType(int idSpotType) {
        this.idSpotType = idSpotType;
    }

    @Basic
    @Column(name = "label", nullable = false, length = 45)
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SpotTypeEntity that = (SpotTypeEntity) o;

        if (idSpotType != that.idSpotType) return false;
        if (label != null ? !label.equals(that.label) : that.label != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idSpotType;
        result = 31 * result + (label != null ? label.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "spotTypeByTable1SpotType")
    public Collection<SpotEntity> getSpotsByIdSpotType() {
        return spotsByIdSpotType;
    }

    public void setSpotsByIdSpotType(Collection<SpotEntity> spotsByIdSpotType) {
        this.spotsByIdSpotType = spotsByIdSpotType;
    }
}
