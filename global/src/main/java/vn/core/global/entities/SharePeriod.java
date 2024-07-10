package vn.core.global.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SharePeriod {
    private int shareId;
    private int userId;
    private int sharedWithUserId;
    private Date sharedDate;
}
