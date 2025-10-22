package org.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Apartment {
    @JsonProperty("address")
    private String address;

    @JsonProperty("apartment")
    private String apartmentNumber;

    @JsonProperty("price")
    private String price;

    @JsonProperty("living_area")
    private String livingArea;

    @JsonProperty("rooms")
    private String rooms;

    @JsonProperty("floor")
    private String floor;

    @JsonProperty("entrance")
    private String entrance;

    @Override
    public String toString() {
        return String.format("Квартира№ %s: %s, %s, %s комнат, %s этаж, подъезд %s", apartmentNumber, price, livingArea, rooms, floor, entrance);
    }
}
