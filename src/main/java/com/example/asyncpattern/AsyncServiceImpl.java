package com.example.asyncpattern;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static java.lang.Thread.sleep;

@Service
public class AsyncServiceImpl extends AsyncService<SampleIfResult, Long, SampleAsyncResult> {

    @Override
    public SampleIfResult executeIf(Long waitSec) {

        // 通信処理想定
        SampleIfResult sampleResult = new SampleIfResult();
        sampleResult.setFooStatus("foo");

        System.out.println("start executeIf " + LocalDateTime.now());

        try {
            sleep(waitSec * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("end executeIf " + LocalDateTime.now());

        return sampleResult;
    }

    @Override
    public SampleAsyncResult verifyResult(SampleIfResult sampleIfResult) {

        SampleIfResult SampleIfResult = sampleIfResult;
        SampleAsyncResult sampleAsyncResult = new SampleAsyncResult();

        sampleAsyncResult.setSampleIfResult(SampleIfResult);

        if ("foo".equals(SampleIfResult.getFooStatus())) {
            sampleAsyncResult.setStatus("SUCCESS");
        }

        sampleAsyncResult.setMessage(LocalDateTime.now().toString());

        return sampleAsyncResult;
    }

}

