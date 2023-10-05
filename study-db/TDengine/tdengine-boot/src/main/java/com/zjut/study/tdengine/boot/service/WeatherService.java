package com.zjut.study.tdengine.boot.service;

import com.zjut.study.tdengine.boot.dao.WeatherMapper;
import com.zjut.study.tdengine.boot.domain.WeatherDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class WeatherService {

    @Autowired
    private WeatherMapper weatherMapper;
    private Random random = new Random(System.currentTimeMillis());
    private String[] locations = {"北京", "上海", "广州", "深圳", "天津"};

    public int init() {
        weatherMapper.dropDB();
        weatherMapper.createDB();
        weatherMapper.createSuperTable();
        long ts = System.currentTimeMillis();
        long thirtySec = 1000 * 30;
        int count = 0;
        for (int i = 0; i < 20; i++) {
            WeatherDO weather = new WeatherDO(new Timestamp(ts + (thirtySec * i)), 30 * random.nextFloat(), random.nextInt(100));
            weather.setLocation(locations[random.nextInt(locations.length)]);
            weather.setNote("note-" + i);
            weatherMapper.createTable(weather);
            count += weatherMapper.insert(weather);
        }
        return count;
    }

    public List<WeatherDO> query(Long limit, Long offset) {
        return weatherMapper.select(limit, offset);
    }

    public int save(float temperature, float humidity) {
        WeatherDO weather = new WeatherDO();
        weather.setTemperature(temperature);
        weather.setHumidity(humidity);

        return weatherMapper.insert(weather);
    }

    public int count() {
        return weatherMapper.count();
    }

    public List<String> getSubTables() {
        return weatherMapper.getSubTables();
    }

    public List<WeatherDO> avg() {
        return weatherMapper.avg();
    }

    public WeatherDO lastOne() {
        Map<String, Object> result = weatherMapper.lastOne();

        long ts = (long) result.get("ts");
        float temperature = (float) result.get("temperature");
        float humidity = (float) result.get("humidity");
        String note = (String) result.get("note");
        String location = (String) result.get("location");

        WeatherDO weather = new WeatherDO(new Timestamp(ts), temperature, humidity);
        weather.setNote(note);
        weather.setLocation(location);
        return weather;
    }
}
