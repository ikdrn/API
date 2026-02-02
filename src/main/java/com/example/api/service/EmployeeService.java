package com.example.api.service;

import com.example.api.model.Employee;
import com.example.api.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 社員情報を取り扱うサービス層。
 * Controllerから「このIDの社員情報ちょうだい！」と頼まれたら、
 * Repositoryに頼んでデータベースから探してきます。
 *
 * なぜControllerが直接Repositoryを呼ばないの？と思うかもしれませんが、
 * ここに「ビジネスロジック」を書くためです。今回はシンプルですが、
 * 実際の現場では「権限チェック」とか「データ加工」とかをここでやります。
 */
@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * 社員IDから社員情報を取得します。
     * 見つからない場合はOptional.empty()が返ります。
     *
     * Optional？ってなった人へ：
     * 「あるかもしれないし、ないかもしれない値」を表現する箱です。
     * nullチェックを忘れてバグるのを防ぐJavaの便利機能。
     */
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }
}
