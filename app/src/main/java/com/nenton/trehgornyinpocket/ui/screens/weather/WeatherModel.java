package com.nenton.trehgornyinpocket.ui.screens.weather;

import com.nenton.trehgornyinpocket.data.storage.dto.WeatherDto;
import com.nenton.trehgornyinpocket.mvp.models.AbstractModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Observable;

public class WeatherModel extends AbstractModel {
    public Observable<List<WeatherDto>> getWeatherOnFourteenDays() {
        List<WeatherDto> weathers = new ArrayList<>();
        weathers.add(new WeatherDto("17", "5", new Date(new Date().getTime()), WeatherDto.WeatherType.SUMMER, "2-6"));
        weathers.add(new WeatherDto("21", "12", new Date(new Date().getTime() + 86400000), WeatherDto.WeatherType.SUMMER, "1-2"));
        weathers.add(new WeatherDto("22", "7", new Date(new Date().getTime() + 2 * 86400000), WeatherDto.WeatherType.CLOUDS, "1-2"));
        weathers.add(new WeatherDto("20", "5", new Date(new Date().getTime() + 3 * 86400000), WeatherDto.WeatherType.HEAVY_RAIN, "2-4"));
        weathers.add(new WeatherDto("7", "0", new Date(new Date().getTime() + 4 * 86400000), WeatherDto.WeatherType.PARTLY_CLOUDY, "2-6"));
        weathers.add(new WeatherDto("16", "6", new Date(new Date().getTime() + 5 * 86400000), WeatherDto.WeatherType.RAIN, "5-15"));
        weathers.add(new WeatherDto("15", "5", new Date(new Date().getTime() + 6 * 86400000), WeatherDto.WeatherType.SLEET, "1-3"));
        weathers.add(new WeatherDto("22", "10", new Date(new Date().getTime() + 7 * 86400000), WeatherDto.WeatherType.SNOW, "1-3"));
        weathers.add(new WeatherDto("26", "7", new Date(new Date().getTime() + 8 * 86400000), WeatherDto.WeatherType.STORM, "2-6"));
        weathers.add(new WeatherDto("28", "5", new Date(new Date().getTime() + 9 * 86400000), WeatherDto.WeatherType.WINDY, "20-25"));
        weathers.add(new WeatherDto("30", "15", new Date(new Date().getTime() + 10 * 86400000), WeatherDto.WeatherType.RAIN_CLOUD, "5-8"));
        weathers.add(new WeatherDto("32", "15", new Date(new Date().getTime() + 11 * 86400000), WeatherDto.WeatherType.SUMMER, "2-6"));

        for (int i = 0; i < weathers.size() - 1; i++) {
            for (int j = 0; j < weathers.size() - 1 - i; j++) {
                if (weathers.get(j).getDate().getTime() > weathers.get(j + 1).getDate().getTime()) {
                    WeatherDto weather = weathers.get(j);
                    weathers.set(j, weathers.get(j + 1));
                    weathers.set(j + 1, weather);
                }
            }
        }
        return Observable.just(weathers);
    }
}
