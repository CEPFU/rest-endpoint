package de.fu_berlin.agdb.crepe.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;

/**
 * Data class for weather data.
 * @author Simon Kalt
 */
@Entity
@Table(name = "dwd_station_weather_data", schema = "public")
public class StationWeatherData implements Serializable {
    private long stationId;
    private Date date;
    private Integer qualityLevel;
    private Double averageAirTemperature;
    private Double steampressure;
    private Double cloudage;
    private Double airPressure;
    private Double relativeHumidityOfTheAir;
    private Double windSpeed;
    private Double maximumAirTemperature;
    private Double minimumAirTemperature;
    private Double minimumAirTemperatureGround;
    private Double maximumWindSpeed;
    private Double precipitationdepth;
    private Double sunshineDuration;
    private Double snowHeight;

    @Id
    @NotNull
    @Column(name = "station_id")
    public long getStationId() {
        return stationId;
    }

    public void setStationId(long stationId) {
        this.stationId = stationId;
    }

    @Id
    @NotNull
    @Column(name = "date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Column(name = "quality_level")
    public Integer getQualityLevel() {
        return qualityLevel;
    }

    public void setQualityLevel(Integer qualityLevel) {
        this.qualityLevel = qualityLevel;
    }

    @Column(name = "average_air_temperature")
    public Double getAverageAirTemperature() {
        return averageAirTemperature;
    }

    public void setAverageAirTemperature(Double averageAirTemperature) {
        this.averageAirTemperature = averageAirTemperature;
    }

    @Column(name = "steampressure")
    public Double getSteampressure() {
        return steampressure;
    }

    public void setSteampressure(Double steampressure) {
        this.steampressure = steampressure;
    }

    @Column(name = "cloudage")
    public Double getCloudage() {
        return cloudage;
    }

    public void setCloudage(Double cloudage) {
        this.cloudage = cloudage;
    }

    @Column(name = "air_pressure")
    public Double getAirPressure() {
        return airPressure;
    }

    public void setAirPressure(Double airPressure) {
        this.airPressure = airPressure;
    }

    @Column(name = "relative_humidity_of_the_air")
    public Double getRelativeHumidityOfTheAir() {
        return relativeHumidityOfTheAir;
    }

    public void setRelativeHumidityOfTheAir(Double relativeHumidityOfTheAir) {
        this.relativeHumidityOfTheAir = relativeHumidityOfTheAir;
    }

    @Column(name = "wind_speed")
    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    @Column(name = "maximum_air_temperature")
    public Double getMaximumAirTemperature() {
        return maximumAirTemperature;
    }

    public void setMaximumAirTemperature(Double maximumAirTemperature) {
        this.maximumAirTemperature = maximumAirTemperature;
    }

    @Column(name = "minimum_air_temperature")
    public Double getMinimumAirTemperature() {
        return minimumAirTemperature;
    }

    public void setMinimumAirTemperature(Double minimumAirTemperature) {
        this.minimumAirTemperature = minimumAirTemperature;
    }

    @Column(name = "minimum_air_temperature_ground")
    public Double getMinimumAirTemperatureGround() {
        return minimumAirTemperatureGround;
    }

    public void setMinimumAirTemperatureGround(Double minimumAirTemperatureGround) {
        this.minimumAirTemperatureGround = minimumAirTemperatureGround;
    }

    @Column(name = "maximum_wind_speed")
    public Double getMaximumWindSpeed() {
        return maximumWindSpeed;
    }

    public void setMaximumWindSpeed(Double maximumWindSpeed) {
        this.maximumWindSpeed = maximumWindSpeed;
    }

    @Column(name = "precipitationdepth")
    public Double getPrecipitationdepth() {
        return precipitationdepth;
    }

    public void setPrecipitationdepth(Double precipitationdepth) {
        this.precipitationdepth = precipitationdepth;
    }

    @Column(name = "sunshine_duration")
    public Double getSunshineDuration() {
        return sunshineDuration;
    }

    public void setSunshineDuration(Double sunshineDuration) {
        this.sunshineDuration = sunshineDuration;
    }

    @Column(name = "snow_height")
    public Double getSnowHeight() {
        return snowHeight;
    }

    public void setSnowHeight(Double snowHeight) {
        this.snowHeight = snowHeight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StationWeatherData that = (StationWeatherData) o;

        if (stationId != that.stationId) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (qualityLevel != null ? !qualityLevel.equals(that.qualityLevel) : that.qualityLevel != null) return false;
        if (averageAirTemperature != null ? !averageAirTemperature.equals(that.averageAirTemperature) : that.averageAirTemperature != null)
            return false;
        if (steampressure != null ? !steampressure.equals(that.steampressure) : that.steampressure != null)
            return false;
        if (cloudage != null ? !cloudage.equals(that.cloudage) : that.cloudage != null) return false;
        if (airPressure != null ? !airPressure.equals(that.airPressure) : that.airPressure != null) return false;
        if (relativeHumidityOfTheAir != null ? !relativeHumidityOfTheAir.equals(that.relativeHumidityOfTheAir) : that.relativeHumidityOfTheAir != null)
            return false;
        if (windSpeed != null ? !windSpeed.equals(that.windSpeed) : that.windSpeed != null) return false;
        if (maximumAirTemperature != null ? !maximumAirTemperature.equals(that.maximumAirTemperature) : that.maximumAirTemperature != null)
            return false;
        if (minimumAirTemperature != null ? !minimumAirTemperature.equals(that.minimumAirTemperature) : that.minimumAirTemperature != null)
            return false;
        if (minimumAirTemperatureGround != null ? !minimumAirTemperatureGround.equals(that.minimumAirTemperatureGround) : that.minimumAirTemperatureGround != null)
            return false;
        if (maximumWindSpeed != null ? !maximumWindSpeed.equals(that.maximumWindSpeed) : that.maximumWindSpeed != null)
            return false;
        if (precipitationdepth != null ? !precipitationdepth.equals(that.precipitationdepth) : that.precipitationdepth != null)
            return false;
        if (sunshineDuration != null ? !sunshineDuration.equals(that.sunshineDuration) : that.sunshineDuration != null)
            return false;
        if (snowHeight != null ? !snowHeight.equals(that.snowHeight) : that.snowHeight != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (stationId ^ (stationId >>> 32));
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (qualityLevel != null ? qualityLevel.hashCode() : 0);
        result = 31 * result + (averageAirTemperature != null ? averageAirTemperature.hashCode() : 0);
        result = 31 * result + (steampressure != null ? steampressure.hashCode() : 0);
        result = 31 * result + (cloudage != null ? cloudage.hashCode() : 0);
        result = 31 * result + (airPressure != null ? airPressure.hashCode() : 0);
        result = 31 * result + (relativeHumidityOfTheAir != null ? relativeHumidityOfTheAir.hashCode() : 0);
        result = 31 * result + (windSpeed != null ? windSpeed.hashCode() : 0);
        result = 31 * result + (maximumAirTemperature != null ? maximumAirTemperature.hashCode() : 0);
        result = 31 * result + (minimumAirTemperature != null ? minimumAirTemperature.hashCode() : 0);
        result = 31 * result + (minimumAirTemperatureGround != null ? minimumAirTemperatureGround.hashCode() : 0);
        result = 31 * result + (maximumWindSpeed != null ? maximumWindSpeed.hashCode() : 0);
        result = 31 * result + (precipitationdepth != null ? precipitationdepth.hashCode() : 0);
        result = 31 * result + (sunshineDuration != null ? sunshineDuration.hashCode() : 0);
        result = 31 * result + (snowHeight != null ? snowHeight.hashCode() : 0);
        return result;
    }
}
