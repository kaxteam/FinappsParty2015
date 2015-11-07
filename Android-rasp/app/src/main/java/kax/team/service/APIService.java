package kax.team.service;

import kax.team.Orientation;
import kax.team.PitchYawRoll;
import xml.restfuldroid.annotation.Resource;
import xml.restfuldroid.annotation.Service;

/**
 * Created by zenbook on 7/11/15.
 */

@Resource(value="http://192.168.0.102:8000/api/rasp")
public interface APIService {

    @Service(value="/orientation", method="GET", response=Orientation.class)
    Orientation getOrentation();

    @Service(value="/isFall", method="GET", response=PitchYawRoll.class)
    PitchYawRoll isFall();

    /*

        @Service(value="/test", method="GET", response=Integer.class)
        int test();

        @Service(method="GET", response=List.class)
        List<MyUser> findActiveUsers();

        @Service(value="Activated", method="GET", response=MyUser.class)
        MyUser exists();

        @Service(value="validate", method="POST")
        void validate();
*/
    }


