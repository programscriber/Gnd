package com.indutech.gnd.service;

import com.indutech.gnd.bo.UserBO;
import com.indutech.gnd.dao.GNDAppExpection;

public interface LoginService {
	boolean validateLoginUser(UserBO userBO) throws GNDAppExpection;
}
