package com.example.api.service;

import com.example.api.model.Weather;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;

import java.util.Map;

/**
 * 外部のお天気APIを呼び出すサービス。
 * ここがまさに「プログラムが別のプログラムに聞く」瞬間！
 *
 * Open-Meteo APIを使ってます。無料で使えて、登録不要。
 * 昔のlivedoorお天気APIみたいに「サクッと試せる」のが魅力です。
 */
@Service
public class WeatherService {

    private final RestTemplate restTemplate;

    // Open-Meteo APIのエンドポイント（東京の座標で固定）
    // 緯度35.6895, 経度139.6917 = 東京
    private static final String WEATHER_API_URL =
            "https://api.open-meteo.com/v1/forecast?latitude=35.6895&longitude=139.6917&current=temperature_2m,relative_humidity_2m,weather_code,wind_speed_10m";

    public WeatherService() {
        this.restTemplate = new RestTemplate();
    }

    /**
     * 東京の現在の天気を取得します。
     *
     * RestTemplateが、HTTPリクエストを送って、
     * 返ってきたJSONを自動でJavaのMapに変換してくれます。
     * これがないと、自分でHTTP通信を書かないといけない...大変！
     */
    public Weather getCurrentWeather() {
        try {
            // ここで外部APIに「天気教えて！」とお願いしてます
            // インターネット越しに別のサーバーと会話する瞬間です
            Map<String, Object> response = restTemplate.getForObject(WEATHER_API_URL, Map.class);

            if (response != null && response.containsKey("current")) {
                Map<String, Object> current = (Map<String, Object>) response.get("current");

                Weather weather = new Weather();
                weather.setLocation("東京");

                // JSONから値を取り出して、自分たちのWeatherオブジェクトに詰め替え
                weather.setTemperature(getDoubleValue(current, "temperature_2m"));
                weather.setHumidity(getIntegerValue(current, "relative_humidity_2m"));
                weather.setWindSpeed(getDoubleValue(current, "wind_speed_10m"));

                // 天気コードを日本語に変換（WMOコード準拠）
                Integer weatherCode = getIntegerValue(current, "weather_code");
                weather.setCondition(convertWeatherCode(weatherCode));

                return weather;
            }

            // データが取れなかった時の保険
            return createDefaultWeather();

        } catch (RestClientException e) {
            // 外部APIが落ちていても、自分のシステムが止まらないように
            // エラーを逃がしておきます。これ大事。
            System.err.println("天気APIの呼び出しに失敗しました: " + e.getMessage());
            return createDefaultWeather();
        }
    }

    /**
     * WMO天気コードを日本語に変換。
     * 0=快晴、1-3=晴れ/曇り、45-48=霧、51-67=雨、71-77=雪、80-99=雷雨
     */
    private String convertWeatherCode(Integer code) {
        if (code == null) return "不明";

        if (code == 0) return "快晴";
        if (code >= 1 && code <= 3) return "晴れ";
        if (code >= 45 && code <= 48) return "霧";
        if (code >= 51 && code <= 67) return "雨";
        if (code >= 71 && code <= 77) return "雪";
        if (code >= 80 && code <= 99) return "雷雨";

        return "曇り";
    }

    // Map から Double を安全に取り出すヘルパー
    private Double getDoubleValue(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        return 0.0;
    }

    // Map から Integer を安全に取り出すヘルパー
    private Integer getIntegerValue(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        return 0;
    }

    // 外部API呼び出し失敗時のデフォルト値
    private Weather createDefaultWeather() {
        Weather weather = new Weather();
        weather.setLocation("東京");
        weather.setTemperature(20.0);
        weather.setCondition("情報取得中");
        weather.setHumidity(50);
        weather.setWindSpeed(3.0);
        return weather;
    }
}
