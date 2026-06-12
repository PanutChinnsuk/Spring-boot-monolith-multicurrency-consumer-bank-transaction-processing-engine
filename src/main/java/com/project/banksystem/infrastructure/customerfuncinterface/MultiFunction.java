package com.project.banksystem.infrastructure.customerfuncinterface;

@FunctionalInterface
public interface MultiFunction<P1,P2,P3,V> {
    V apply(P1 p1, P2 p2, P3 p3);
}
