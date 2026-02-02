package com.example.api.controller;

import com.example.api.model.MorningCheckResponse;
import com.example.api.service.MorningCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 統合お助けAPI - 朝の挨拶メッセージを提供するエンドポイント。
 *
 * ★★★ここが今回のメインディッシュ★★★
 *
 * このControllerは、自分でデータを持っていません。
 * 代わりに、他の2つのAPI（社員API、天気API）を呼び出して、
 * その結果を組み合わせて、心温まる朝のメッセージを作ります。
 *
 * これが「APIオーケストレーション」。
 * 複数のAPIを指揮して、新しい価値を生み出す。かっこいいでしょ？
 */
@RestController
@RequestMapping("/api/v1")
public class MorningCheckController {

    @Autowired
    private MorningCheckService morningCheckService;

    /**
     * 社員IDを受け取って、朝の挨拶メッセージを返します。
     *
     * 【使い方】
     * GET http://localhost:8080/api/v1/morning-check/1
     *
     * すると、こんなJSONが返ってきます：
     * {
     *   "employeeName": "田中太郎",
     *   "department": "開発部",
     *   "weatherInfo": "晴れ、気温25.5℃、湿度60%、風速3.2m/s",
     *   "morningMessage": "田中太郎さん、おはようございます！...",
     *   "timestamp": "2025-01-15 08:30:00"
     * }
     *
     * たった1回のAPI呼び出しだけど、裏では：
     * 1. 社員APIに問い合わせ（HTTP通信）
     * 2. 天気APIに問い合わせ（HTTP通信）
     * 3. 外部お天気サービスに問い合わせ（HTTP通信）
     * と、3段階のAPI連携が起きてます。これがAPIの威力！
     */
    @GetMapping("/morning-check/{id}")
    public MorningCheckResponse getMorningCheck(@PathVariable Long id) {
        // MorningCheckServiceが、複数のAPIを呼んでデータを集めてきます
        // ここはそれを受け取って、そのまま返すだけ
        return morningCheckService.getMorningCheck(id);
    }
}
