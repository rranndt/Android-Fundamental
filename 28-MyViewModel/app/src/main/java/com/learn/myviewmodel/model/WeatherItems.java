package com.learn.myviewmodel.model;

import lombok.Data;

/*
@Setter, untuk setter
@Getter, untuk getter
@AllArgsConstructor, constructor untuk semua
@NoArgsConstructor, Contructor kosong
@ToString, untuk anotasi toString

@Data, untuk semua, tp constuctor kosong
@Builder, untuk membuat builder
*/

@Data
public class WeatherItems {

    private int id;
    private String name;
    private String currentWeather;
    private String description;
    private String temperature;
}
