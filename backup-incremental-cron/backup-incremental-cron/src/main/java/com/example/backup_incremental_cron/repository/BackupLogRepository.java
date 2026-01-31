package com.example.backup_incremental_cron.repository;

import com.example.backup_incremental_cron.model.BackupLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BackupLogRepository extends JpaRepository <BackupLog, Long> {
}
