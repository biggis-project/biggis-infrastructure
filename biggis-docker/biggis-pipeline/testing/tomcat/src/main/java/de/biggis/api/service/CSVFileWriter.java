package de.biggis.api.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.opencsv.CSVWriter;

/**
 * An interims solution to persist the sensor data stream
 */
public class CSVFileWriter {

    public void writeToCSV (String outputFile, String message) {

        // before we open the file check to see if it already exists
        boolean alreadyExists = new File(outputFile).exists();

        try {
            // use FileWriter constructor that specifies open for appending
            CSVWriter csvOutput = new CSVWriter(new FileWriter(outputFile,true), ',', CSVWriter.NO_QUOTE_CHARACTER);

            if (!alreadyExists) {

                String [] header = "aid,sid,lat,lon,alt,epoch,temp,humid".split(";");
                csvOutput.writeNext(header);

                String [] entries = message.split("/");
                csvOutput.writeNext(entries);
                csvOutput.close();
            }
            else {

                String [] entries = message.split("/");
                csvOutput.writeNext(entries);
                csvOutput.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}