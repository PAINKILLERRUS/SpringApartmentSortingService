package org.example.service;

import org.example.model.Apartment;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Сортировка квартир по домам и номерам квартир
 */

@Service
public class ApartmentSortingService {
    public Map<String, List<Apartment>> sortApartments(List<Apartment> apartments) {
        // Группировка по адресу (дому)
        Map<String, List<Apartment>> apartmentsByAddres = apartments
                .stream()
                .collect(Collectors.groupingBy(Apartment::getAddress));

        // Сортировка квартир внутри каждого дома по номеру квартиры
        apartmentsByAddres.forEach((address, apartmentList) -> {
            apartmentList.sort(Comparator.comparing(apt -> {
                try {
                    return Integer.parseInt(apt.getApartmentNumber());
                } catch (NumberFormatException e) {
                    return Integer.MAX_VALUE;// Если номер не числовой, помещаем в конец
                }
            }));
        });

        return apartmentsByAddres;
    }
}
