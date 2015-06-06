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
        mBox = new MessageBox();
        PostMan man = new PostMan(mBox);
        new Thread(man).start();
        new MessagePool().startLooping();
    }

    public MessageBox getBox() {
        return mBox;
    }

    public void setBox(MessageBox box) {
        this.mBox = box;
    }
}