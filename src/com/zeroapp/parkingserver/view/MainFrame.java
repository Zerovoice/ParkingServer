package com.zeroapp.parkingserver.view;


import com.google.gson.Gson;
import com.zeroapp.parking.message.AreaContants;
import com.zeroapp.parkingserver.common.Area;
import com.zeroapp.parkingserver.dao.AreaDao;
import com.zeroapp.parkingserver.dao.BusinessDao;
import com.zeroapp.parkingserver.model.ParkingServer;
import com.zeroapp.tools.BmapPoint;

public class MainFrame {

	public static void main(String[] args) {
        ParkingServer.getServer();
//        new BusinessDao().createBusiness("shinan", 1, 2, 4, 3, "5", "6",1903);
//        BmapPoint[] bmp2Area={AreaContants.p1ForArea1,AreaContants.p2ForArea1,AreaContants.p3ForArea1};
//        Area a = new Area();
//        a.setAreaName("shinan");
//        a.setCityId(1903);
//        a.setBmapPoints(new Gson().toJson(bmp2Area));
//        new AreaDao().createArea(a);
	}

}
