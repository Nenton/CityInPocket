package com.nenton.trehgornyinpocket.data.network.res;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseWeather {
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
    @SerializedName("city")
    @Expose
    private City city;

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

    public City getCity() {
        return city;
    }

    public class List {

        @SerializedName("dt")
        @Expose
        private int dt;
        @SerializedName("main")
        @Expose
        private Main main;
        @SerializedName("weather")
        @Expose
        private java.util.List<Weather> weather = null;
        @SerializedName("clouds")
        @Expose
        private Clouds clouds;
        @SerializedName("wind")
        @Expose
        private Wind wind;
        @SerializedName("rain")
        @Expose
        private Rain rain;
        @SerializedName("sys")
        @Expose
        private Sys sys;
        @SerializedName("dt_txt")
        @Expose
        private String dtTxt;

        public int getDt() {
            return dt;
        }

        public Main getMain() {
            return main;
        }

        public java.util.List<Weather> getWeather() {
            return weather;
        }

        public Clouds getClouds() {
            return clouds;
        }

        public Wind getWind() {
            return wind;
        }

        public Rain getRain() {
            return rain;
        }

        public Sys getSys() {
            return sys;
        }

        public String getDtTxt() {
            return dtTxt;
        }
    }

    public class Coord {

        @SerializedName("lat")
        @Expose
        private float lat;
        @SerializedName("lon")
        @Expose
        private float lon;

        public float getLat() {
            return lat;
        }

        public float getLon() {
            return lon;
        }
    }

    public class Clouds {

        @SerializedName("all")
        @Expose
        private int all;

        public int getAll() {
            return all;
        }
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
    }

    public class Main {

        @SerializedName("temp")
        @Expose
        private float temp;
        @SerializedName("temp_min")
        @Expose
        private float tempMin;
        @SerializedName("temp_max")
        @Expose
        private float tempMax;
        @SerializedName("pressure")
        @Expose
        private float pressure;
        @SerializedName("sea_level")
        @Expose
        private float seaLevel;
        @SerializedName("grnd_level")
        @Expose
        private float grndLevel;
        @SerializedName("humidity")
        @Expose
        private int humidity;
        @SerializedName("temp_kf")
        @Expose
        private int tempKf;

        public float getTemp() {
            return temp;
        }

        public float getTempMin() {
            return tempMin;
        }

        public float getTempMax() {
            return tempMax;
        }

        public float getPressure() {
            return pressure;
        }

        public float getSeaLevel() {
            return seaLevel;
        }

        public float getGrndLevel() {
            return grndLevel;
        }

        public int getHumidity() {
            return humidity;
        }

        public int getTempKf() {
            return tempKf;
        }
    }

    public class Rain {

        @SerializedName("3h")
        @Expose
        private float _3h;

        public float get_3h() {
            return _3h;
        }
    }

    public class Sys {

        @SerializedName("pod")
        @Expose
        private String pod;

        public String getPod() {
            return pod;
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

    public class Wind {

        @SerializedName("speed")
        @Expose
        private float speed;
        @SerializedName("deg")
        @Expose
        private float deg;

        public float getSpeed() {
            return speed;
        }

        public float getDeg() {
            return deg;
        }
    }
}
