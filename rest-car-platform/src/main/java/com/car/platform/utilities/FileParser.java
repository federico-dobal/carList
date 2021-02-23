package com.car.platform.utilities;

import com.car.platform.entity.Listing;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class FileParserException extends RuntimeException {
    FileParserException(String message) {
        super(message);
    }
}

public class FileParser {

    public static List<Listing> parseFile(MultipartFile file, Long dealerId) throws IllegalArgumentException {
        if (file == null) {
            throw new IllegalArgumentException("Cannot parse file since it is null");
        }

        if (dealerId == null) {
            throw new IllegalArgumentException("Cannot parse file since dealerId is null");
        }

        List<Listing> listings = new ArrayList<>();

        // parse CSV file to create a list of `User` objects
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            // Ignore headers
            reader.readLine();
            String line = reader.readLine();
            while (line != null) {

                if (line == null) {
                    break;
                }

                List<String> tokens = Arrays.stream(line.split(","))
                        .map(tok -> tok.trim())
                        .collect(Collectors.toList());
                if (tokens.size() != 6) {
                    // TODO: add proper logging
                    // Ignore line since it is corrupted
                    line = reader.readLine();
                    continue;
                }
                String makeAndModel[] = tokens.get(1).split("/");

                if (makeAndModel.length != 2) {
                    line = reader.readLine();
                    // TODO: add proper logging
                    // Ignore line since it is corrupted due to malformed pair (make, model)
                    continue;
                }

                listings.add(new Listing(dealerId, tokens.get(0), makeAndModel[0].trim(), makeAndModel[1].trim(),
                        Integer.parseInt(tokens.get(2)), Integer.parseInt(tokens.get(3)), tokens.get(4),
                        Long.parseLong(tokens.get(5))));
                line = reader.readLine();

            }
        } catch (Exception ex) {
            // TODO: all logger
            System.out.println("Error reading file" + ex.getMessage());
        }

        return listings;

    }
}
