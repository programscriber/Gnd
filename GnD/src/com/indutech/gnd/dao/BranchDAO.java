package com.indutech.gnd.dao;

import java.util.List;

import com.indutech.gnd.dto.Bank;
import com.indutech.gnd.dto.Branch;
import com.indutech.gnd.dto.District;
import com.indutech.gnd.dto.State;

public interface BranchDAO {

	void saveBranch(Branch branch);

	Long saveOrUpdateDistrict(District district);

	Long saveOrUpdateState(State state);

	Long saveOrUpdateBank(Bank bank);

	List<Branch> getBranchList(String branchCode);

	List<Branch> searchShortCode(String branchCode);

	List<Branch> getBranch(String shortCode, Long bankId);
	
	public List<Branch> getBankBranch(Long bankId);
}
