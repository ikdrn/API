package com.example.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 朝の挨拶メッセージを届けるための箱。
 * 社員情報APIと天気APIから集めたデータを組み合わせて、
 * 心温まる一言を作ります。これがAPIのオーケストレーション！
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MorningCheckResponse {

    // 社員の名前
    private String employeeName;

    // 所属部署
    private String department;

    // 現在の天気情報
    private String weatherInfo;

    // 朝の挨拶メッセージ（メインディッシュ）
    private String morningMessage;

    // データを取得した日時。「いつの情報？」が分かると親切
    private String timestamp;
}
