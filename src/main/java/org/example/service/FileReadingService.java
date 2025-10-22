package org.example.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Apartment;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class FileReadingService {
    public List<Apartment> readApartmentsFromFile(String filePath) throws IOException {
        return new ObjectMapper().readValue(new File(filePath), new TypeReference<List<Apartment>>() {
        });
    }
}
