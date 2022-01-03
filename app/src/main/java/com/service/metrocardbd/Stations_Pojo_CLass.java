package com.service.metrocardbd;

import androidx.annotation.NonNull;

public class Stations_Pojo_CLass {

    public String id;
    public String name;

    public Stations_Pojo_CLass(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
