package cn.tedu.camper.portal;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
@EnableBatchProcessing
@MapperScan("cn.tedu.camper.portal.mapper")
@SpringBootApplication
@EnableScheduling
public class CamperPortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(CamperPortalApplication.class, args);
    }

}
