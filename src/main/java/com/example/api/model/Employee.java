package com.example.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 社員情報を入れる箱。
 * データベースのテーブルと1対1で対応してます。
 * Lombokの@Dataで、getter/setterを自動生成しているので、
 * 自分で書く必要なし！便利な時代になったものです。
 */
@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 社員の名前。「田中太郎」みたいな感じで入ります
    private String name;

    // 所属部署。「開発部」「営業部」など
    private String department;

    // メールアドレス。社内連絡用
    private String email;
}
