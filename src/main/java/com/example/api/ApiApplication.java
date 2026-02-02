package com.example.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Bootアプリケーションのエントリーポイント（入り口）。
 *
 * このmainメソッドを実行すると、Spring Bootが起動して、
 * Tomcatサーバーが立ち上がり、APIが使えるようになります。
 *
 * @SpringBootApplicationというアノテーション1つで、
 * 設定ファイルの読み込み、Bean登録、サーバー起動などなど、
 * めちゃくちゃたくさんのことを自動でやってくれます。
 * 昔は全部手動で設定してたんですよ...ほんと便利になった！
 */
@SpringBootApplication
public class ApiApplication {

    public static void main(String[] args) {
        // これだけ！これだけでWebサーバーが起動します
        SpringApplication.run(ApiApplication.class, args);

        System.out.println("\n========================================");
        System.out.println("🎉 APIサーバーが起動しました！");
        System.out.println("========================================");
        System.out.println("試しにブラウザで以下にアクセスしてみてください：");
        System.out.println("📋 社員情報: http://localhost:8080/api/internal/employees/1");
        System.out.println("☀️  天気情報: http://localhost:8080/api/internal/weather");
        System.out.println("💌 朝の挨拶: http://localhost:8080/api/v1/morning-check/1");
        System.out.println("========================================\n");
    }
}
