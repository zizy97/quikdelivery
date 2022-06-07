package com.quikdeliver.schedules;

import com.quikdeliver.service.PDRService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableScheduling
public class DbSchedules {
    private final PDRService pdrService;

//    @Scheduled(fixedRate = 1000*60*60*24,initialDelay = 1000)
    private void deleteRecord(){
        log.info("deleting......");
        pdrService.deletePermanent();
    }
}
