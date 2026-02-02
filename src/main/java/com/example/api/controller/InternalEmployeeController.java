package com.example.api.controller;

import com.example.api.model.Employee;
import com.example.api.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 社員情報を提供する内部API。
 *
 * ブラウザやPostmanから「http://localhost:8080/api/internal/employees/1」
 * とアクセスすると、ID=1の社員情報がJSON形式で返ってきます。
 *
 * @RestControllerを付けると、返り値が自動的にJSONに変換されます。
 * 昔は自分でJSON文字列を組み立ててたんですよ...便利な時代！
 */
@RestController
@RequestMapping("/api/internal")
public class InternalEmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 社員IDで社員情報を取得するエンドポイント。
     *
     * 【使い方】
     * GET http://localhost:8080/api/internal/employees/1
     *
     * すると、こんなJSONが返ってきます：
     * {
     *   "id": 1,
     *   "name": "田中太郎",
     *   "department": "開発部",
     *   "email": "tanaka@example.com"
     * }
     */
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable Long id) {
        // ServiceにIDを渡して、社員情報を取得
        return employeeService.getEmployeeById(id)
                .map(ResponseEntity::ok)  // 見つかったら200 OKで返す
                .orElse(ResponseEntity.notFound().build());  // 見つからなかったら404
    }
}
