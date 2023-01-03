package com.example.testTask.services.calculator;

import com.example.testTask.services.reader.Converter;
import com.example.testTask.services.reader.Reader;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class Calculator {
    private final Reader reader;

    public Calculator(Reader reader) {
        this.reader = reader;
    }

    public double calc(double lat1, double lat2, double lon1,
                                     double lon2, double el1, double el2) {

        final int R = 6371;

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000;

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }

    public double getDistance(String fileName) {
        ArrayList<String> lines = reader.read(fileName);
        ArrayList<Double[]> coordinates = Converter.convert(lines);

        double dist = 0;
        for (int i = 1; i < coordinates.size(); i++) {
            dist += calc(coordinates.get(i -1)[0], coordinates.get(i)[0], coordinates.get(i -1)[1],
                    coordinates.get(i)[1], coordinates.get(i -1)[2], coordinates.get(i)[2]);
        }
        return dist;
    }
}
