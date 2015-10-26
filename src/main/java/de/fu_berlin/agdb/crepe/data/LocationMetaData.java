package de.fu_berlin.agdb.crepe.data;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vividsolutions.jts.geom.Point;
import de.fu_berlin.agdb.crepe.json.serialize.PointDeserializer;
import de.fu_berlin.agdb.crepe.json.serialize.PointSerializer;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.Type;

import javax.persistence.*;

/**
 * JPA mapping class for meta data about locations.
 */
@Entity
@Table(name = "location_meta_data", schema = "public", catalog = "ems")
@NamedQueries(value = {
        /**
         * Query to retrieve a list of locations by their description.
         */
        @NamedQuery(
                name = "LocationMetaData.findByDescription",
                query = "from LocationMetaData where locationDescription like :locationDescription"
        ),
        /**
         * Query to retrieve all available locations.
         */
        @NamedQuery(
                name = "LocationMetaData.findAll",
                query = "from LocationMetaData"
        ),
        @NamedQuery(
                name = "LocationMetaData.nearby",
                query = "from LocationMetaData order by Distance(locationPosition, :Point)"
        )
})
public class LocationMetaData {
    private int locationId;
    private Point locationPosition;
    private String locationDescription;

    @Id
    @Generated(GenerationTime.INSERT)
    @Column(name = "location_id", columnDefinition = "serial", insertable = false, updatable = false)
    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    @Basic
    @Column(name = "location_position")
    @Type(type = "org.hibernate.spatial.GeometryType")
    @JsonSerialize(using = PointSerializer.class)
    public Point getLocationPosition() {
        return locationPosition;
    }

    @JsonDeserialize(using = PointDeserializer.class)
    public void setLocationPosition(Point locationPosition) {
        this.locationPosition = locationPosition;
    }

    @Basic
    @Column(name = "location_description")
    public String getLocationDescription() {
        return locationDescription;
    }

    public void setLocationDescription(String locationDescription) {
        this.locationDescription = locationDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LocationMetaData that = (LocationMetaData) o;

        if (locationId != that.locationId) return false;
        if (locationPosition != null ? !locationPosition.equals(that.locationPosition) : that.locationPosition != null)
            return false;
        if (locationDescription != null ? !locationDescription.equals(that.locationDescription) : that.locationDescription != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = locationId;
        result = 31 * result + (locationPosition != null ? locationPosition.hashCode() : 0);
        result = 31 * result + (locationDescription != null ? locationDescription.hashCode() : 0);
        return result;
    }
}
