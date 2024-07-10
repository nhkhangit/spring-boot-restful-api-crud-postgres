# spring-boot-restful-api-crud-postgres

## 1. Create table and insert dump data

```sql
-- Tạo bảng User
CREATE TABLE "User" (
    user_id SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    gender VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tạo bảng Share_Period
CREATE TABLE Share_Period (
    share_id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES "User"(user_id),
    shared_with_user_id INTEGER REFERENCES "User"(user_id),
    shared_date DATE
);
```

```sql
-- Insert data vào bảng User
INSERT INTO "User" (email, password, gender) VALUES
('user1@example.com', 'password123', 'Female'),
('user2@example.com', 'securepass', 'Male'),
('user3@example.com', 'pass1234', 'Other'),
('user4@example.com', 'strongpwd', 'Female'),
('user5@example.com', 'safepass', 'Male');

-- Insert data vào bảng Share_Period
INSERT INTO Share_Period (user_id, shared_with_user_id, shared_date) VALUES
(1, 2, '2024-01-15'),
(2, 3, '2024-02-01'),
(3, 1, '2024-02-10'),
(4, 5, '2024-03-05'),
(5, 4, '2024-03-20');
```

## 2. Install postgres docker if pc not exist postgres
docker run --name postgres-v16 -e -p 5432:5432 POSTGRES_PASSWORD=Abc123 -d postgres

## 3. Edit the connection string in application.properties
![alt text](image.png)

If using the docker then replace the name db 

```cmd
database.url=jdbc:postgresql://postgres-v16:5432/postgres
```

## 4. Create network
```cmd
 docker network create line-postgres-5432
```

## 5. connect network
```cmd
docker network connect line-postgres-5432 postgres-v16
```
## 6. Build and run container

cd to folder has dockerfile and open the cmd and run:

```cmd
docker build -t springboot-crud-postgres-service .
```

```cmd
docker run -d  -p 8080:8080 --network line-postgres-5432 springboot-crud-postgres-service
```