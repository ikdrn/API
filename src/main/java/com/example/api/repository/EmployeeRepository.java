package com.example.api.repository;

import com.example.api.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 社員データベースとの窓口。
 * JpaRepositoryを継承するだけで、基本的なCRUD操作が
 * 自動で使えるようになります。魔法みたいでしょ？
 *
 * 「findById」「save」「delete」などのメソッドが、
 * 何も書かなくても最初から使えます。Springの便利機能！
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // ここは空っぽでOK。必要なメソッドはJpaRepositoryが全部用意してくれてます
}
