package de.biggis.api.controller;

import de.biggis.api.model.GEOSensor;
import de.biggis.api.service.CSVFileWriter;
import de.biggis.api.service.KafkaProducer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.LoggerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;

/**
 * Provide a RESTful interface for receiving log data from weather stations
 *
 * This is a really basic setup and does not .
 *
 */
@Path("v1")
public class RESTfulService {

    // KafkaProducer kafkaProducer = new KafkaProducer();
    // CSVFileWriter csvFileWriter = new CSVFileWriter();

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(RESTfulService.class);
    private static final GsonBuilder gsonBuilder = new GsonBuilder();
    private static final String GEO_SENSOR_TOPIC_NAME = "geosensors";
    private static final String OUTPUT_FILE_PATH = "/opt/geosensors.csv";

    /**
     * @GET              Retrieves geo sensor data from database
     * @POST             Creates new geo sensor data entry
     * @PUT              Updates specific geo sensor data entry
     * @PATCH            Partially updates specific geo sensor data entry
     * @DELETE           Deletes specific geo sensor data entry
     */

    /**
     * GET Method
     *
     * This is only a placeholder. Has to be implemented
     * Database query needs to be implemented
     *
     * @param aid       Aggregator ID
     * @param sid       Sensor ID
     *
     * @return          GEOSensor object as JSON
     */
    @GET
    @Path("/geosensors")
    @Produces(MediaType.APPLICATION_JSON)
    public String getSensordata(
            @QueryParam("aid") String aid,
            @QueryParam("sid") String sid) {

        /**
         * Database request to be placed here according
         * to params.
         *
         * Simulated Response
         *
         */
        BigDecimal lat = new BigDecimal(49.1123124);
        BigDecimal lon = new BigDecimal(7.1123124);
        BigDecimal alt = new BigDecimal(2);
        long epoch = 140121230;
        double temp = 22.3;
        double humid = 0.4;

        GEOSensor geosensor = new GEOSensor();
        geosensor.setAid(aid);
        geosensor.setSid(sid);
        geosensor.setLat(lat);
        geosensor.setLon(lon);
        geosensor.setAlt(alt);
        geosensor.setEpoch(epoch);
        geosensor.setTemp(temp);
        geosensor.setHumid(humid);

        Gson gson = gsonBuilder.setPrettyPrinting().create();

        String geosensorToString = gson.toJson(geosensor);
        LOG.info(geosensor.toString());

        return geosensorToString;

    }

    /**
     * POST Method
     *
     * @param aid       Aggregator ID
     * @param sid       Sensor ID
     * @param lat       Latitude of geo sensor
     * @param lon       Longitude of geo sensor
     * @param alt       Altitude of geo sensor
     * @param epoch     Unix epoch
     * @param temp      Temperature measured in degree celcius
     * @param humid     Humidity measured in percent
     *
     * @return          GEOSensor object as JSON
     */
    @POST
    @Path("/geosensors")
    @Produces(MediaType.APPLICATION_JSON)
    public String postSensordata(
            @QueryParam("aid") String aid,
            @QueryParam("sid") String sid,
            @QueryParam("lat") BigDecimal lat,
            @QueryParam("lon") BigDecimal lon,
            @QueryParam("alt") BigDecimal alt,
            @QueryParam("epoch") long epoch,
            @QueryParam("temp") double temp,
            @QueryParam("humid") double humid ) {

        GEOSensor geosensor = new GEOSensor();
        geosensor.setAid(aid);
        geosensor.setSid(sid);
        geosensor.setLat(lat);
        geosensor.setLon(lon);
        geosensor.setAlt(alt);
        geosensor.setEpoch(epoch);
        geosensor.setTemp(temp);
        geosensor.setHumid(humid);

        // csvFileWriter.writeToCSV(OUTPUT_FILE_PATH, geosensor.toString());
        //LOG.info(geosensor.toString());
        // kafkaProducer.sendMessage(GEO_SENSOR_TOPIC_NAME, geosensor.toString());

        Gson gson = gsonBuilder.serializeNulls().setPrettyPrinting().create();

        String geosensorToString = gson.toJson(geosensor);
        LOG.info(geosensor.toString());

        return geosensorToString;

    }

}
