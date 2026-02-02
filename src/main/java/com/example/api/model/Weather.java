package com.example.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 天気情報を運ぶお弁当箱。
 * 外部のお天気APIから取得したデータを、
 * 自分たちが使いやすい形に詰め替えます。
 *
 * JSONデータが行ったり来たりする時も、この形になってくれます。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Weather {

    // 場所の名前（東京、大阪など）
    private String location;

    // 気温（摂氏）。「25.5」みたいな小数で入ります
    private Double temperature;

    // 天気の状態。「晴れ」「曇り」「雨」など
    private String condition;

    // 湿度（パーセント）
    private Integer humidity;

    // 風速（m/s）
    private Double windSpeed;
}
