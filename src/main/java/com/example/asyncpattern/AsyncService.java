package com.example.asyncpattern;

import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

/**
 *
 * @param <T1> IF戻り値
 * @param <T2> IF引数
 * @param <T3> IF実行結果および、その検証内容
 */
public abstract class AsyncService <T1 extends Object, T2 extends Object, T3 extends AsyncResult> {

    abstract public T1 executeIf(T2 param);
    abstract public T3 verifyResult(T1 ifResult);

    @Async
    public CompletableFuture<T3> execute(T2 param) {

        return CompletableFuture.completedFuture(verifyResult(executeIf(param)));
    }

}
