-- 社員情報の初期データを投入
-- アプリ起動時に自動で実行されます

-- 開発部の田中さん
INSERT INTO employees (name, department, email) VALUES
    ('田中太郎', '開発部', 'tanaka@example.com');

-- 営業部の佐藤さん
INSERT INTO employees (name, department, email) VALUES
    ('佐藤花子', '営業部', 'sato@example.com');

-- 人事部の鈴木さん
INSERT INTO employees (name, department, email) VALUES
    ('鈴木一郎', '人事部', 'suzuki@example.com');

-- デザイン部の山田さん
INSERT INTO employees (name, department, email) VALUES
    ('山田美咲', 'デザイン部', 'yamada@example.com');

-- 企画部の高橋さん
INSERT INTO employees (name, department, email) VALUES
    ('高橋健太', '企画部', 'takahashi@example.com');
