package com.example.asyncpattern;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
public class AsyncController {

    private AsyncService asyncService1;

    private AsyncService asyncService2;

    public AsyncController(
            @Qualifier("asyncServiceImpl") AsyncService asyncService1
            ,@Qualifier("asyncServiceImpl2")  AsyncService asyncService2) {
        this.asyncService1 = asyncService1;
        this.asyncService2 = asyncService2;
    }

    @GetMapping("/async")
    public CompletableFuture<SampleAsyncResult> getCompletable(@RequestParam(defaultValue = "0") long waitSec, Model model) {

        System.out.println("Start get.");

        System.out.println(
                "acceptedTime:" + LocalDateTime.now()
        );

        CompletableFuture<SampleAsyncResult> future1 = asyncService1.execute(waitSec);
        CompletableFuture<SampleAsyncResult> future2 = asyncService2.execute(waitSec);

        CompletableFuture<Void> allOf = CompletableFuture.allOf(future1, future2);

        allOf.whenComplete((ret, ex) -> {
                    if (ex == null) {
                        try {
                            SampleAsyncResult sampleAsyncResult = future1.get();
                            System.out.println(
                                    "#1 completedTime:" + sampleAsyncResult.getMessage()
                            );
                            SampleAsyncResult sampleAsyncResult2 = future2.get();
                            System.out.println(
                                    "#2 completedTime:" + sampleAsyncResult.getMessage()
                            );
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        System.out.println("End get.");

        // 実処理ではレスポンスの合成とか
        return future1;
    }
}
