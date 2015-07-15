package de.fu_berlin.agdb.crepe.data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class DwdMetaDataPK implements Serializable {
    private long locationId;
    private long stationId;

    @Column(name = "location_id")
    @Id
    public long getLocationId() {
        return locationId;
    }

    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }

    @Column(name = "station_id")
    @Id
    public long getStationId() {
        return stationId;
    }

    public void setStationId(long stationId) {
        this.stationId = stationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DwdMetaDataPK that = (DwdMetaDataPK) o;

        if (locationId != that.locationId) return false;
        if (stationId != that.stationId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (locationId ^ (locationId >>> 32));
        result = 31 * result + (int) (stationId ^ (stationId >>> 32));
        return result;
    }
}
