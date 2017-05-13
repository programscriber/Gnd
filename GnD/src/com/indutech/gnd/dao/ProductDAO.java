package com.indutech.gnd.dao;

import java.util.List;

import com.indutech.gnd.dto.MasterBank;
import com.indutech.gnd.dto.MasterDcms;
import com.indutech.gnd.dto.MasterType;
import com.indutech.gnd.dto.Network;
import com.indutech.gnd.dto.Product;

public interface ProductDAO {
	
	void saveProduct(Product product);

	List<MasterBank> getBankId(String bankName);

	Long getNetworkId(String NetworkName);

	List<MasterDcms> getDcmsId(String dcmsName);

	List<MasterType> getTypeId(String typeName);

	String getBankCode(String shortCode);


	List<Product> getProductList(String productCode);

	List<Product> getProduct(Product product);

	List<Product> getProducts(String productCode);

	List<Network> getNetworkList(Long networkId);

	String getBin(String productCode, Long bankId);

	List<Product> getProductList(String product, Long bankId);

	List<Product> getProduct(String shortCode, String bin, Long bankId);

}
