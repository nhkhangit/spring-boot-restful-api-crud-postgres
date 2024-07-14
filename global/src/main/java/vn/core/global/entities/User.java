package vn.core.global.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int userId;
    private String email;
    private String password;
    private String gender;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
