package com.nenton.trehgornyinpocket.data.network.res;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseWeather {
    @SerializedName("city")
    @Expose
    private City city;
    @SerializedName("cod")
    @Expose
    private String cod;
    @SerializedName("message")
    @Expose
    private float message;
    @SerializedName("cnt")
    @Expose
    private int cnt;
    @SerializedName("list")
    @Expose
    private java.util.List<List> list = null;

    public City getCity() {
        return city;
    }

    public String getCod() {
        return cod;
    }

    public float getMessage() {
        return message;
    }

    public int getCnt() {
        return cnt;
    }

    public java.util.List<List> getList() {
        return list;
    }

    public class City {

        @SerializedName("id")
        @Expose
        private int id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("coord")
        @Expose
        private Coord coord;
        @SerializedName("country")
        @Expose
        private String country;
        @SerializedName("population")
        @Expose
        private int population;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public Coord getCoord() {
            return coord;
        }

        public String getCountry() {
            return country;
        }

        public int getPopulation() {
            return population;
        }
    }

    public class Coord {
        @SerializedName("lon")
        @Expose
        private float lon;
        @SerializedName("lat")
        @Expose
        private float lat;

        public float getLon() {
            return lon;
        }

        public float getLat() {
            return lat;
        }
    }

    public class List {

        @SerializedName("dt")
        @Expose
        private int dt;
        @SerializedName("temp")
        @Expose
        private Temp temp;
        @SerializedName("pressure")
        @Expose
        private float pressure;
        @SerializedName("humidity")
        @Expose
        private int humidity;
        @SerializedName("weather")
        @Expose
        private java.util.List<Weather> weather = null;
        @SerializedName("speed")
        @Expose
        private float speed;
        @SerializedName("deg")
        @Expose
        private int deg;
        @SerializedName("clouds")
        @Expose
        private int clouds;
        @SerializedName("rain")
        @Expose
        private float rain;

        public float getRain() {
            return rain;
        }

        public int getDt() {
            return dt;
        }

        public Temp getTemp() {
            return temp;
        }

        public float getPressure() {
            return pressure;
        }

        public int getHumidity() {
            return humidity;
        }

        public java.util.List<Weather> getWeather() {
            return weather;
        }

        public float getSpeed() {
            return speed;
        }

        public int getDeg() {
            return deg;
        }

        public int getClouds() {
            return clouds;
        }
    }

    public class Temp {

        @SerializedName("day")
        @Expose
        private float day;
        @SerializedName("min")
        @Expose
        private float min;
        @SerializedName("max")
        @Expose
        private float max;
        @SerializedName("night")
        @Expose
        private float night;
        @SerializedName("eve")
        @Expose
        private float eve;
        @SerializedName("morn")
        @Expose
        private float morn;

        public float getDay() {
            return day;
        }

        public float getMin() {
            return min;
        }

        public float getMax() {
            return max;
        }

        public float getNight() {
            return night;
        }

        public float getEve() {
            return eve;
        }

        public float getMorn() {
            return morn;
        }
    }

    public class Weather {

        @SerializedName("id")
        @Expose
        private int id;
        @SerializedName("main")
        @Expose
        private String main;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("icon")
        @Expose
        private String icon;

        public int getId() {
            return id;
        }

        public String getMain() {
            return main;
        }

        public String getDescription() {
            return description;
        }

        public String getIcon() {
            return icon;
        }
    }
}
