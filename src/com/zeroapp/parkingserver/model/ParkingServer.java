package com.zeroapp.parkingserver.model;



public class ParkingServer {

    private MessageBox mBox;
    private static ParkingServer mServer = null;
    private ParkingServer() {
        bind();
    }

    public static ParkingServer getServer() {
        if(mServer==null){
            mServer = new ParkingServer();
        }
        return mServer;
        
    }

    private void bind() {
        PostMan man = new PostMan();
        new Thread(man).start();
    }
}