package com.example.backup_incremental_cron;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BackupIncrementalCronApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackupIncrementalCronApplication.class, args);
	}

}
