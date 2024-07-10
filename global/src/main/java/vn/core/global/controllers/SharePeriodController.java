package vn.core.global.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.core.global.dao.SharePeriodDao;
import vn.core.global.entities.SharePeriod;

import java.util.List;

@RestController
@RequestMapping("/api/share-periods")
public class SharePeriodController {
    private final SharePeriodDao sharePeriodDao;

    public SharePeriodController(SharePeriodDao sharePeriodDao) {
        this.sharePeriodDao = sharePeriodDao;
    }

    @GetMapping
    public List<SharePeriod> getAllSharePeriods() {
        return sharePeriodDao.getAllSharePeriods();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SharePeriod> getSharePeriodById(@PathVariable int id) {
        SharePeriod sharePeriod = sharePeriodDao.getSharePeriodById(id);
        if (sharePeriod != null) {
            return ResponseEntity.ok(sharePeriod);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> createSharePeriod(@RequestBody SharePeriod sharePeriod) {
        if (sharePeriodDao.createSharePeriod(sharePeriod)) {
            return ResponseEntity.ok("{\"message\": \"Share period created successfully\"}");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\": \"Failed to create share period\"}");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateSharePeriod(@PathVariable int id, @RequestBody SharePeriod sharePeriod) {
        sharePeriod.setShareId(id);
        if (sharePeriodDao.updateSharePeriod(sharePeriod)) {
            return ResponseEntity.ok("{\"message\": \"Share period updated successfully\"}");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\": \"Failed to update share period\"}");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSharePeriod(@PathVariable int id) {
        if (sharePeriodDao.deleteSharePeriod(id)) {
            return ResponseEntity.ok("{\"message\": \"Share period deleted successfully\"}");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\": \"Failed to delete share period\"}");
        }
    }
}
