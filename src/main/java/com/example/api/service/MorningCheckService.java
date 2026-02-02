package com.example.api.service;

import com.example.api.model.Employee;
import com.example.api.model.MorningCheckResponse;
import com.example.api.model.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 朝の挨拶メッセージを作る、統合お助けサービス。
 *
 * ★ここが今回のプロジェクトの主役★
 *
 * 直接データベースは見ません。代わりに：
 * 1. 社員情報API（自分で作ったやつ）に「この人の情報ちょうだい」
 * 2. 天気API（自分で作ったやつ）に「今の天気どう？」
 * と、HTTPで聞いて回ります。
 *
 * これがAPIのオーケストレーション。
 * 指揮者が楽団をまとめるように、複数のAPIを組み合わせて
 * 1つの価値（朝の挨拶メッセージ）を作り出します。
 */
@Service
public class MorningCheckService {

    private final RestTemplate restTemplate;

    // application.propertiesから内部APIのベースURLを読み込み
    @Value("${api.internal.base-url:http://localhost:8080}")
    private String internalApiBaseUrl;

    public MorningCheckService() {
        this.restTemplate = new RestTemplate();
    }

    /**
     * 社員IDを受け取って、朝の挨拶メッセージを組み立てます。
     *
     * ポイント：ここは他のAPIを「HTTP通信」で呼んでます。
     * 同じプログラム内だけど、わざとAPIとして呼ぶことで、
     * 「APIってこうやって連携するんだ！」が体感できます。
     */
    public MorningCheckResponse getMorningCheck(Long employeeId) {
        try {
            // 【1つ目のAPI呼び出し】自前の社員APIを叩く
            // ここで別のエンドポイントに「教えて！」と頼んでます
            // プログラムがプログラムに話しかける、APIらしい瞬間！
            String employeeUrl = internalApiBaseUrl + "/api/internal/employees/" + employeeId;
            Employee employee = restTemplate.getForObject(employeeUrl, Employee.class);

            // 【2つ目のAPI呼び出し】自前の天気APIを叩く
            // こっちも同じく、HTTPで「今の天気は？」と聞いてます
            String weatherUrl = internalApiBaseUrl + "/api/internal/weather";
            Weather weather = restTemplate.getForObject(weatherUrl, Weather.class);

            // 2つのAPIから集めた情報を組み合わせて、温かいメッセージを作る
            return buildMorningMessage(employee, weather);

        } catch (RestClientException e) {
            // API呼び出しに失敗した時の保険。
            // 本番環境では、ここでリトライしたり、別の手段を試したりします
            System.err.println("API呼び出しエラー: " + e.getMessage());
            return createErrorResponse();
        }
    }

    /**
     * 社員情報と天気情報を組み合わせて、心温まるメッセージを作ります。
     * ここが「データの料理人」。複数の材料（API）から1つの料理（メッセージ）を作る！
     */
    private MorningCheckResponse buildMorningMessage(Employee employee, Weather weather) {
        if (employee == null || weather == null) {
            return createErrorResponse();
        }

        MorningCheckResponse response = new MorningCheckResponse();
        response.setEmployeeName(employee.getName());
        response.setDepartment(employee.getDepartment());

        // 天気情報を読みやすくまとめる
        String weatherInfo = String.format(
                "%s、気温%.1f℃、湿度%d%%、風速%.1fm/s",
                weather.getCondition(),
                weather.getTemperature(),
                weather.getHumidity(),
                weather.getWindSpeed()
        );
        response.setWeatherInfo(weatherInfo);

        // メインディッシュ：朝の挨拶メッセージ
        String message = String.format(
                "%sさん、おはようございます！%sに所属されているんですね。" +
                        "今日の%sの天気は【%s】ですよ（気温%.1f℃）。今日も1日頑張りましょう！",
                employee.getName(),
                employee.getDepartment(),
                weather.getLocation(),
                weather.getCondition(),
                weather.getTemperature()
        );
        response.setMorningMessage(message);

        // タイムスタンプ。「いつのデータ？」が分かると親切
        response.setTimestamp(LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        ));

        return response;
    }

    // エラー時の保険。何か問題があっても、ちゃんとレスポンスは返す
    private MorningCheckResponse createErrorResponse() {
        MorningCheckResponse response = new MorningCheckResponse();
        response.setEmployeeName("ゲスト");
        response.setDepartment("不明");
        response.setWeatherInfo("情報取得に失敗しました");
        response.setMorningMessage("おはようございます！今日も良い一日を！");
        response.setTimestamp(LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        ));
        return response;
    }
}
