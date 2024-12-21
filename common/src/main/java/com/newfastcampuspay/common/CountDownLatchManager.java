package com.newfastcampuspay.common;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import org.springframework.context.annotation.Configuration;

//멀티 쓰레드 환경

/**
 * CountDownLatch latch = new CountDownLatch(1);
 * latch.await();
 * 다른 곳에서 latch.countDown(); 해주지 않는 이상 await()가 통과되지 않음!
 * 즉, latch 값이 0이 되어야만 다음 로직 실행 가능!
 */
@Configuration
public class CountDownLatchManager {
    private final Map<String, CountDownLatch> countDownLatchMap;
    private final Map<String, String> stringMap;

    public CountDownLatchManager() {
        this.countDownLatchMap = new HashMap<>();
        this.stringMap = new HashMap<>();
    }

    public void addCountDownLatch(String key) {
        this.countDownLatchMap.put(key, new CountDownLatch(1));
    }

    public void setDataForKey(String key, String data){
        this.stringMap.put(key, data);
    }
    public String getDataForKey(String key){
        return this.stringMap.get(key);
    }
    public CountDownLatch getCountDownLatch(String key) {
        return this.countDownLatchMap.get(key);
    }
}
