package org.example.service;

import org.example.model.Apartment;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Запись отсортированных данных в файл
 */
@Service
public class SortedDataRecordingService {

    public void writeSortedApartmentsToFile(Map<String, List<Apartment>> sortedApartments, String outputFile) throws IOException {
        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write("Отсортированный список квартир по домам: \n\n");

            for (Map.Entry<String, List<Apartment>> entry : sortedApartments.entrySet()) {
                writer.write("Дом: " + entry.getKey() + "\n");
                writer.write("Количество квартир: " + entry.getValue().size() + "\n");

                for (Apartment apartment : entry.getValue()) {
                    writer.write(" -" + apartment.toString() + "\n");
                }
                writer.write("\n");
            }

            // Добавляем статистику
            writer.write("=== СТАТИСТИКА ===\n");
            int totalApartments = sortedApartments
                    .values()
                    .stream()
                    .mapToInt(List::size)
                    .sum();
            writer.write("Всего домов: " + sortedApartments.size() + "\n");
            writer.write("Всего квартир: " + totalApartments + "\n");

            // Статистика по домам
            for (Map.Entry<String, List<Apartment>> entry : sortedApartments.entrySet()) {
                writer.write(String.format("Дом '%s': %d квартир\n", entry.getKey(), entry.getValue().size()));
            }
        }
    }

    /**
     * Получить статистику по квартирам
     */
    public Map<String, Object> getStatistics(List<Apartment> apartments) {
        Map<String, Object> stats = new HashMap<>();

        stats.put("totalApartments", apartments.size());

        // Количество уникальных домов
        long uniqueHouses = apartments.stream()
                .map(Apartment::getAddress)
                .distinct()
                .count();
        stats.put("uniqueHouses", uniqueHouses);

        // Распределение по количеству комнат
        Map<String, Long> roomsDistribution = apartments.stream()
                .collect(Collectors.groupingBy(Apartment::getRooms, Collectors.counting()));
        stats.put("roomsDistribution", roomsDistribution);
        return stats;
    }
}
