package com.xlkj.beautifulpicturehouse.module.home.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @date 2017/11/16
 * @description：@JsonIgnoreProperties(ignoreUnknown = true)将这个注解写在类上之后，就会忽略类中不存在的字段，可以满足当前的需要
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherBean {

    /**
     * weatherinfo : {"city":"北京","cityid":"101010100","temp1":"-2℃","temp2":"16℃","weather":"晴","img1":"n0.gif","img2":"d0.gif","ptime":"18:00"}
     */

    public WeatherinfoBean weatherinfo;

    public WeatherinfoBean getWeatherinfo() {
        return weatherinfo;
    }

    public void setWeatherinfo(WeatherinfoBean weatherinfo) {
        this.weatherinfo = weatherinfo;
    }
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WeatherinfoBean {
        /**
         * city : 北京
         * cityid : 101010100
         * temp1 : -2℃
         * temp2 : 16℃
         * weather : 晴
         * img1 : n0.gif
         * img2 : d0.gif
         * ptime : 18:00
         */

        public String city;
        public String cityid;
        public String temp1;
        public String temp2;
        public String weather;
        public String img1;
        public String img2;
        public String ptime;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCityid() {
            return cityid;
        }

        public void setCityid(String cityid) {
            this.cityid = cityid;
        }

        public String getTemp1() {
            return temp1;
        }

        public void setTemp1(String temp1) {
            this.temp1 = temp1;
        }

        public String getTemp2() {
            return temp2;
        }

        public void setTemp2(String temp2) {
            this.temp2 = temp2;
        }

        public String getWeather() {
            return weather;
        }

        public void setWeather(String weather) {
            this.weather = weather;
        }

        public String getImg1() {
            return img1;
        }

        public void setImg1(String img1) {
            this.img1 = img1;
        }

        public String getImg2() {
            return img2;
        }

        public void setImg2(String img2) {
            this.img2 = img2;
        }

        public String getPtime() {
            return ptime;
        }

        public void setPtime(String ptime) {
            this.ptime = ptime;
        }

        @Override
        public String toString() {
            return "WeatherinfoBean{" +
                    "city='" + city + '\'' +
                    ", cityid='" + cityid + '\'' +
                    ", temp1='" + temp1 + '\'' +
                    ", temp2='" + temp2 + '\'' +
                    ", weather='" + weather + '\'' +
                    ", img1='" + img1 + '\'' +
                    ", img2='" + img2 + '\'' +
                    ", ptime='" + ptime + '\'' +
                    '}';
        }
    }
}
