package de.fu_berlin.agdb.crepe.data;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "dwd_meta_data", schema = "public", catalog = "ems")
@IdClass(DwdMetaDataPK.class)
public class DwdMetaData {
    private long locationId;
    private long stationId;
    private Date fromDate;
    private Date untilDate;
    private int stationHeight;
    private String federalState;
    private LocationMetaData locationMetaDataByLocationId;

    @Id
    @Column(name = "location_id")
    public long getLocationId() {
        return locationId;
    }

    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }

    @Id
    @Column(name = "station_id")
    public long getStationId() {
        return stationId;
    }

    public void setStationId(long stationId) {
        this.stationId = stationId;
    }

    @Basic
    @Column(name = "from_date")
    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    @Basic
    @Column(name = "until_date")
    public Date getUntilDate() {
        return untilDate;
    }

    public void setUntilDate(Date untilDate) {
        this.untilDate = untilDate;
    }

    @Basic
    @Column(name = "station_height")
    public int getStationHeight() {
        return stationHeight;
    }

    public void setStationHeight(int stationHeight) {
        this.stationHeight = stationHeight;
    }

    @Basic
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

        DwdMetaData that = (DwdMetaData) o;

        if (locationId != that.locationId) return false;
        if (stationId != that.stationId) return false;
        if (stationHeight != that.stationHeight) return false;
        if (fromDate != null ? !fromDate.equals(that.fromDate) : that.fromDate != null) return false;
        if (untilDate != null ? !untilDate.equals(that.untilDate) : that.untilDate != null) return false;
        if (federalState != null ? !federalState.equals(that.federalState) : that.federalState != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (locationId ^ (locationId >>> 32));
        result = 31 * result + (int) (stationId ^ (stationId >>> 32));
        result = 31 * result + (fromDate != null ? fromDate.hashCode() : 0);
        result = 31 * result + (untilDate != null ? untilDate.hashCode() : 0);
        result = 31 * result + stationHeight;
        result = 31 * result + (federalState != null ? federalState.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "location_id", nullable = false)
    public LocationMetaData getLocationMetaDataByLocationId() {
        return locationMetaDataByLocationId;
    }

    public void setLocationMetaDataByLocationId(LocationMetaData locationMetaDataByLocationId) {
        this.locationMetaDataByLocationId = locationMetaDataByLocationId;
    }
}
