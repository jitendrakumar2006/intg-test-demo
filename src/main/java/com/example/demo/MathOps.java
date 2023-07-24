package com.example.demo;

public class MathOps {

    private Integer extra;
    public MathOps(Integer extra) {
        this.extra = extra;
    }
    public int add(int a, int b) {
        return (extra == null) ? (a + b) : (a + b + extra);
    }
}
