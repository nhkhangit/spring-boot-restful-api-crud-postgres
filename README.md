# spring-boot-restful-api-crud-postgres

## 1. Create table and insert dump data

```sql
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE
);
```

```sql
INSERT INTO users (name, email) VALUES
('John Doe', 'johndoe@example.com'),
('Jane Smith', 'janesmith@example.com'),
('Alice Johnson', 'alicejohnson@example.com');
```

## 2. Install postgres docker if pc not exist postgres
docker run --name postgres-v16 -e POSTGRES_PASSWORD=Abc123 -d postgres

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