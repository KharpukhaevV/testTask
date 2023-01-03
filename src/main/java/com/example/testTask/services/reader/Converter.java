package com.example.testTask.services.reader;

import org.apache.commons.math3.util.Precision;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class Converter {
    public static ArrayList<Double[]> convert(ArrayList<String> lines) {
        ArrayList<Double[]> coordinates = new ArrayList<>();
        for (String line : lines) {
            Double[] coordinate = new Double[3];
            if (line.contains("GGA")) {
                String lat = line.split(",")[2];
                String lon = line.split(",")[4];
                if (lat.length() != 0) {
                    coordinate[0] = Converter.calc(Double.parseDouble(lat.substring(0, 2)), Double.parseDouble(lat.substring(2, lat.length() - 1)));
                    coordinate[1] = Converter.calc(Double.parseDouble(lon.substring(1, 3)), Double.parseDouble(lon.substring(3, lon.length() - 2)));
                    coordinate[2] = Double.parseDouble(line.split(",")[9]);

                    coordinates.add(coordinate);
                }
            }
            if (line.contains("VTG") && line.split(",").length > 1) {
                if (line.split(",")[5].isEmpty()) {
                    coordinates.remove(coordinates.size() - 1);
                }
            }
        }
        return coordinates;
    }

    public static double calc(double a, double b) {
        return Precision.round(a + b / 60, 8);
    }
}
