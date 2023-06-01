package com.example.test2;

import com.google.gson.annotations.SerializedName;

public class LightRequest {
    @SerializedName("entity_id")
    private String entityId;

    public LightRequest(String entityId){
        this.entityId = entityId;
    }
}
