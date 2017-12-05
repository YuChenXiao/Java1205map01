package com.example.auser.java1205map01;

/**
 * Created by auser on 2017/12/5.
 */

//範例{
//    "results" : [
//    {
//    "elevation" : 1608.637939453125,
//    "location" : {
//    "lat" : 39.7391536,
//    "lng" : -104.9847034
//    },
//    "resolution" : 4.771975994110107
//    }
//    ],
//    "status" : "OK"
//    }


public class Elevation {
public MyResult[] results;
public String status;


}
class MyLocation
{
    public double lat;
    public double lng;
}
class MyResult
{
    public double elevation;
    public MyLocation location;
    public double resolution;
}