package de.biggis.api.model;

import java.math.BigDecimal;
/**
 * POJO class for GPS weather station data
 */
public class GEOSensor {

    private String aid;
    private String sid;
    private BigDecimal lat;
    private BigDecimal lon;
    private BigDecimal alt;
    private long epoch;
    private double temp;
    private double humid;

    /**
     * GET-Methods
     */
    public String getAid() {
        return aid;
    }

    public String getSid() {return sid; }

    public BigDecimal getLat() {
        return lat;
    }

    public BigDecimal getLon() {
        return lon;
    }

    public BigDecimal getAlt() {
        return alt;
    }

    public long getEpoch() {
        return epoch;
    }

    public double getTemp() {
        return temp;
    }

    public double getHumid() {
        return humid;
    }

    /**
     * SET-Methods
     */
    public void setAid(String aid) {
        this.aid = aid;
    }

    public void setSid(String sid) { this.sid = sid; }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public void setLon(BigDecimal lon) {
        this.lon = lon;
    }

    public void setAlt(BigDecimal alt) {
        this.alt = alt;
    }

    public void setEpoch(long epoch) {
        this.epoch = epoch;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public void setHumid(double humid) {
        this.humid = humid;
    }

    @Override
    public String toString() {
        return aid + "," + sid + "," + lat + "," + lon + "," + alt + "," + epoch + "," + temp + "," + humid;
    }

}

