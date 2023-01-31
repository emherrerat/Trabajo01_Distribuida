package com.distribuida;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class Servidor {
    public static void main(String[] args) {
        Quarkus.run(args);
    }
}
