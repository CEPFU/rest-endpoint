package de.fu_berlin.agdb.crepe.data;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vividsolutions.jts.geom.Point;
import de.fu_berlin.agdb.crepe.json.serialize.PointDeserializer;
import de.fu_berlin.agdb.crepe.json.serialize.PointSerializer;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;

/**
 * Data class for weather station meta data.
 * @author Simon Kalt
 */
@Entity
@Table(name = "dwd_station_meta_data", schema = "public")
public class StationMetaData implements Serializable {
    private long stationId;

    private Point stationPosition;
    private Date fromDate;
    private Date untilDate;
    private Integer stationHeight;
    private String stationName;
    private String federalState;

    @Id
    @NotNull
    @Column(name = "station_id")
    public long getStationId() {
        return stationId;
    }

    public void setStationId(long stationId) {
        this.stationId = stationId;
    }

    @NotNull
    @Column(name = "station_position")
    @Type(type = "org.hibernate.spatial.GeometryType")
    @JsonSerialize(using = PointSerializer.class)
    public Point getStationPosition() {
        return stationPosition;
    }

    @JsonDeserialize(using = PointDeserializer.class)
    public void setStationPosition(Point stationPosition) {
        this.stationPosition = stationPosition;
    }

    @Column(name = "from_date")
    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    @Column(name = "until_date")
    public Date getUntilDate() {
        return untilDate;
    }

    public void setUntilDate(Date untilDate) {
        this.untilDate = untilDate;
    }

    @Column(name = "station_height")
    public Integer getStationHeight() {
        return stationHeight;
    }

    public void setStationHeight(Integer stationHeight) {
        this.stationHeight = stationHeight;
    }

    @Column(name = "station_name")
    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    @Column(name = "federal_state")
    public String getFederalState() {
        return federalState;
    }

    public void setFederalState(String federalState) {
        this.federalState = federalState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StationMetaData that = (StationMetaData) o;

        if (stationId != that.stationId) return false;
        if (stationPosition != null ? !stationPosition.equals(that.stationPosition) : that.stationPosition != null)
            return false;
        if (fromDate != null ? !fromDate.equals(that.fromDate) : that.fromDate != null) return false;
        if (untilDate != null ? !untilDate.equals(that.untilDate) : that.untilDate != null) return false;
        if (stationHeight != null ? !stationHeight.equals(that.stationHeight) : that.stationHeight != null)
            return false;
        if (stationName != null ? !stationName.equals(that.stationName) : that.stationName != null) return false;
        if (federalState != null ? !federalState.equals(that.federalState) : that.federalState != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (stationId ^ (stationId >>> 32));
        result = 31 * result + (stationPosition != null ? stationPosition.hashCode() : 0);
        result = 31 * result + (fromDate != null ? fromDate.hashCode() : 0);
        result = 31 * result + (untilDate != null ? untilDate.hashCode() : 0);
        result = 31 * result + (stationHeight != null ? stationHeight.hashCode() : 0);
        result = 31 * result + (stationName != null ? stationName.hashCode() : 0);
        result = 31 * result + (federalState != null ? federalState.hashCode() : 0);
        return result;
    }
}
