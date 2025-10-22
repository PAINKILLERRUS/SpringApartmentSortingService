package org.example.controller;

import org.example.model.Apartment;
import org.example.service.ApartmentSortingService;
import org.example.service.FileReadingService;
import org.example.service.SortedDataRecordingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/apartments")
public class ApartmentController {

    @Autowired
    private ApartmentSortingService apartmentSortingService;
    @Autowired
    private FileReadingService fileReadingService;
    @Autowired
    private SortedDataRecordingService sortedDataRecordingService;

    private static final String INPUT_FILE = "src/main/resources/apartments_list.json";
    private static final String OUTPUT_FILE = "sorted_apartment_list.txt";


    @GetMapping("/process")
    public ResponseEntity<String> processApartments() {
        try {
            // Чтение данных из файла
            List<Apartment> apartments = fileReadingService.readApartmentsFromFile(INPUT_FILE);

            // Сортировка квартир
            Map<String, List<Apartment>> sortedApartments = apartmentSortingService.sortApartments(apartments);

            // Запись в файл
            sortedDataRecordingService.writeSortedApartmentsToFile(sortedApartments, OUTPUT_FILE);
            return ResponseEntity.ok("Данные успешно обработаны и сохранены в файл: " + OUTPUT_FILE);

        } catch (IOException e) {
            return ResponseEntity.internalServerError()
                    .body("Ошибка при обработке данных: " + OUTPUT_FILE);
        }
    }

    @GetMapping("/sorted")
    public ResponseEntity<Map<String, List<Apartment>>> getSortedApartments(){
        try {
            List<Apartment> apartments = fileReadingService.readApartmentsFromFile(INPUT_FILE);
            Map<String, List<Apartment>> sortedApartments = apartmentSortingService.sortApartments(apartments);
            return  ResponseEntity.ok(sortedApartments);
        } catch (IOException e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getStatistics(){
        try {
            List<Apartment> apartments = fileReadingService.readApartmentsFromFile(INPUT_FILE);
            Map<String, Object> statistic = sortedDataRecordingService.getStatistics(apartments);
            return ResponseEntity.ok(statistic);
        } catch (IOException e){
            return ResponseEntity.internalServerError().build();
        }
    }
}
