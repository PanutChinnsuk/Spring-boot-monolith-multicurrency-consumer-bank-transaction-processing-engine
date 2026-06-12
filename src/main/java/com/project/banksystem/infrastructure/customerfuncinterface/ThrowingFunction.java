package com.project.banksystem.infrastructure.customerfuncinterface;

@FunctionalInterface
public interface ThrowingFunction<T,R> {
    R apply(T t) throws Exception;
}
