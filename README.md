# spring-boot-restful-api-crud-postgres

## I. Cài đặt môi trường local máy để start source code 

- 1. **HomeBrew**
- 2. **JDK 17**
- 3. **Docker**
- 4. **Maven**
- 5. **Intelij**





### 1. Install brew on macos

1. Install Xcode Command Line Tools
```cmd
xcode-select --install
```

2. Install Homebrew

```cmd
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
```

3. setup profile env 

```cmd 
echo 'eval "$(/opt/homebrew/bin/brew shellenv)"' >> /Users/your-username/.zshrc
eval "$(/opt/homebrew/bin/brew shellenv)"
```

**Replace**  Đổi your-username thành tên pc của bạn 

- Để lấy tên user máy sử dụng lệnh tuần tự như bên dưới:
```cmd 
cd
```

```cmd 
pwd
```

- -> Phía sau /User/<đây là tên user máy > 


4. Check homebrew đã cài đặt thành công chưa

Chạy lệnh bên dưới nếu brew doctor không báo command not found là cài đặt thành công 
```cmd
brew doctor
```

### 2. Install JDK 17 
Down load và install java 17 
```cmd
https://download.oracle.com/java/17/archive/jdk-17.0.10_macos-aarch64_bin.dmg
```


### 3. Install docker 

- Install docker 
```cmd
brew install --cask docker
```

- Start docker
```cmd
open /Applications/Docker.app
```

### 4. Install Maven

- Install maven 

```cmd 
brew install maven
```

Sau khi chạy install maven sẽ có đoạn thông báo khi chạy hoàn tất, chuôi số x.x.x phía sau apache-maven là version của maven, thay version vào lệnh bên dưới và thực thi:  

```cmd 
export PATH=/opt/homebrew/Cellar/maven/<version maven>/bin:$PATH
```

ví dụ:

*export PATH=/opt/homebrew/Cellar/maven/3.6.3/bin:$PATH*


- Setup ENV profile 
```cmd 
echo 'export PATH=/opt/homebrew/Cellar/maven/<thay đổi thành version ở trên>/bin:$PATH' >> ~/.zshrc
```


- Reload ENV profile 
```cmd 
source ~/.zshrc
```

## II. Setup Database Postgres Docker  

### 1. Start instance postgres 
```cmd
docker run --name postgres-v16 -p 5432:5432 -e  POSTGRES_PASSWORD=Abc123 -d postgres
```

Sau khi run postgres, chạy lệnh bên dưới để có thông tin id container:
```cmd
docker ps 
```

### 2. Connect and Create table and insert dump data

Chạy lệnh sau để connect database:

```cmd 
docker exec -it id_container_cua_bạn psql -U postgres
```
Ví dụ: 

docker exec -it 2a1796201681 psql -U postgres

Sau khi connect thành công, copy các lệnh bên dưới để tạo table và insert data: 

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


## 3. Pull & config connection string to database application.properties

1. Setup IDEA 

Truy cập link sau và Download IntelIJ 

https://www.jetbrains.com/idea/download/?section=mac



2. connection String 

![alt text](image.png)

Nếu chạy source code ở local máy của bạn thì thay postgres-v16 thành 0.0.0.0 và ngược lại, ví dụ như sau: 

```cmd
database.url=jdbc:postgresql://postgres-v16:5432/postgres
```


