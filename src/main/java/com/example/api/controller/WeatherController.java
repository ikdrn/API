package com.example.api.controller;

import com.example.api.model.Weather;
import com.example.api.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 天気情報を提供する内部API。
 *
 * 外部のOpen-Meteo APIを呼び出して、その結果を
 * 自分たち用に整形してから返します。
 *
 * こうやって「外部APIをラップする」ことで：
 * - 外部APIの仕様変更に強くなる
 * - 自分たちが使いやすい形に加工できる
 * - エラーハンドリングを一箇所で管理できる
 *
 * 昔のlivedoorお天気APIみたいに、シンプルで使いやすいAPI！
 */
@RestController
@RequestMapping("/api/internal")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    /**
     * 現在の天気を取得するエンドポイント。
     *
     * 【使い方】
     * GET http://localhost:8080/api/internal/weather
     *
     * すると、こんなJSONが返ってきます：
     * {
     *   "location": "東京",
     *   "temperature": 25.5,
     *   "condition": "晴れ",
     *   "humidity": 60,
     *   "windSpeed": 3.2
     * }
     */
    @GetMapping("/weather")
    public Weather getCurrentWeather() {
        // WeatherServiceが外部APIを呼んで、データを整形してくれます
        // ここはそれを受け取って、そのまま返すだけ。シンプル！
        return weatherService.getCurrentWeather();
    }
}
