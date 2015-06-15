package com.zeroapp.parkingserver.view;

import com.zeroapp.parkingserver.dao.BusinessDao;
import com.zeroapp.parkingserver.model.ParkingServer;

public class MainFrame {

	public static void main(String[] args) {
        ParkingServer.getServer();
        new BusinessDao().createBusiness("shinan", 1, 2, "3", "4", "5", "6", 7);
        new BusinessDao().removeBusiness(3);
	}

}
