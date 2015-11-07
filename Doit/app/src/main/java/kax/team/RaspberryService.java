package kax.team;

import java.util.List;

import xml.restfuldroid.annotation.Resource;
import xml.restfuldroid.annotation.Service;

/**
 * Created by zenbook on 7/11/15.
 */

@Resource(value="http://192.168.0.102:8000/api/rasp")
public interface RaspberryService {

    @Service(value="/orientation", method="GET", response=Orientation.class)
    Orientation getOrentation();

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


