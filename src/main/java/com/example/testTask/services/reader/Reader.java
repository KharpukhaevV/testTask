package com.example.testTask.services.reader;

import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;

@Component
public class Reader {

    public ArrayList<String> read(String fileName) {
        ArrayList<String> coordinates = new ArrayList<>();
        try {
            File file = new File("C:/Users/user/Desktop/testTask/upload-dir/" + fileName);
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            while (line != null) {
                String[] s = line.split("\\$");
                if (s.length > 2) {
                    for (String str : s) {
                        if (!str.isEmpty()) {
                            coordinates.add(str);
                        }
                    }
                } else {
                    if (s.length > 1) {
                        coordinates.add(s[1]);
                    }
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return coordinates;
    }
}