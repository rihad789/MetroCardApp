package com.service.metrocardbd.Metro_Card_Portal;

public class Travel_History_POJO_Class {

    String lineName,routeData,distance,journey_start,journey_end,fareAmount;

    public Travel_History_POJO_Class() {
    }

    public Travel_History_POJO_Class(String lineName, String routeData, String distance, String journey_start, String journey_end, String fareAmount) {
        this.lineName = lineName;
        this.routeData = routeData;
        this.distance = distance;
        this.journey_start = journey_start;
        this.journey_end = journey_end;
        this.fareAmount = fareAmount;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getRouteData() {
        return routeData;
    }

    public void setRouteData(String routeData) {
        this.routeData = routeData;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getJourney_start() {
        return journey_start;
    }

    public void setJourney_start(String journey_start) {
        this.journey_start = journey_start;
    }

    public String getJourney_end() {
        return journey_end;
    }

    public void setJourney_end(String journey_end) {
        this.journey_end = journey_end;
    }

    public String getFareAmount() {
        return fareAmount;
    }

    public void setFareAmount(String fareAmount) {
        this.fareAmount = fareAmount;
    }
}
