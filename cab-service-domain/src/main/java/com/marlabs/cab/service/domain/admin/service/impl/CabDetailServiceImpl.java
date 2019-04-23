package com.marlabs.cab.service.domain.admin.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marlabs.cab.service.common.constant.Constants;
import com.marlabs.cab.service.common.util.CabServiceUtil;
import com.marlabs.cab.service.domain.admin.service.CabDetailService;
import com.marlabs.cab.service.persistance.admin.dao.CabDetailDAO;
import com.marlabs.cab.service.persistance.entity.CabDetailEntity;
import com.marlabs.cab.service.persistance.entity.CabOwnerEntity;
import com.marlabs.cab.service.persistance.entity.OfficeCityEntity;
import com.marlabs.cab.service.persistance.vo.CabDetailVO;
import com.marlabs.cab.service.persistance.vo.CabOwnerVO;

@Service
public class CabDetailServiceImpl implements CabDetailService {
	
	private static Logger logger = LogManager.getLogger(CabDetailServiceImpl.class);

	@Autowired
	private CabDetailDAO cabDetailDAO;

	@Override
	public List<CabOwnerVO> cabDetailDashboard() {
		logger.info("cabDetailDashboard() -> Cab Detail Dashboard Service call...");
	
		List<CabOwnerEntity> ownerEntity = null;
		ownerEntity= cabDetailDAO.cabDetailDashboard();
		
		return  getCabDetailsDashbaord(ownerEntity);
	}

	@Override
	public String addUpdateCabOwner(CabOwnerEntity cabOwnerEntity) {
		logger.info("addUpdateCabOwner() -> Add/Update Cab Owner Service call...");
		
		if(cabOwnerEntity.getActive() == null){
		cabOwnerEntity.setActive(Constants.CAB_OWNER_STATUS);
		}
		cabOwnerEntity.setAttachments(Constants.CAB_OWNER_ATTACHMENTS);
		
		return cabDetailDAO.addUpdateCabOwner(cabOwnerEntity);
	}

	@Override
	public CabOwnerEntity editCabOwner(Long cabOwnerId) {
		logger.info("cabDetailDashboard() -> Edit Cab Owner Service call...");
		
		return cabDetailDAO.editCabOwner(cabOwnerId);
	}

	@Override
	public String addUpdateCabDetail(CabDetailVO cabDetailVO) {
		logger.info("addUpdateCabDetail() -> Add/Update Cab Detail Service call...");
		
		CabDetailEntity cabDetailEntity = new CabDetailEntity();
		CabOwnerEntity cabOwnerEntity = new CabOwnerEntity();
		OfficeCityEntity officeCity = new OfficeCityEntity();
		
		cabOwnerEntity.setCabOwnerId(cabDetailVO.getCabOwnerId());
		
		if(!CabServiceUtil.isNULL(cabDetailVO.getCabDetailId())){
			cabDetailEntity.setCabDetailId(cabDetailVO.getCabDetailId());
		}
		
		setCabDetails(cabDetailVO, cabDetailEntity, cabOwnerEntity);
		
		officeCity.setOfficeCityId(cabDetailVO.getOfficeCityId());
		cabDetailEntity.getOfficeCity().add(officeCity);
		
		if(cabDetailEntity.getActive() == null){
			cabDetailEntity.setActive(Constants.CAB_DETAIL_STATUS);
		}
		cabDetailEntity.setAttachments(Constants.CAB_DETAIL_ATTACHMENTS);
		
		return cabDetailDAO.addUpdateCabDetail(cabDetailEntity);
	}

	private void setCabDetails(CabDetailVO cabDetailVO, CabDetailEntity cabDetailEntity,CabOwnerEntity cabOwnerEntity) {
		
		cabDetailEntity.setCabOwner(cabOwnerEntity);
		cabDetailEntity.setActive(Constants.CAB_DETAIL_STATUS);
		cabDetailEntity.setDriverFirstName(cabDetailVO.getDriverFirstName());
		cabDetailEntity.setDriverLastName(cabDetailVO.getDriverLastName());
		cabDetailEntity.setDriverMiddleName(cabDetailVO.getDriverMiddleName());
		cabDetailEntity.setDriverPhone1(cabDetailVO.getDriverPhone1());
		cabDetailEntity.setDriverPhone2(cabDetailVO.getDriverPhone2());
		cabDetailEntity.setDriverLicense(cabDetailVO.getDriverLicense());
		cabDetailEntity.setDriverAddress(cabDetailVO.getDriverAddress());
		cabDetailEntity.setInsuranceDate(cabDetailVO.getInsuranceDate());
		cabDetailEntity.setRegistrationNo(cabDetailVO.getRegistrationNo());
		cabDetailEntity.setCreatedBy(cabDetailVO.getCreatedBy());
		cabDetailEntity.setUpdatedBy(cabDetailVO.getUpdatedBy());
		cabDetailEntity.setStartDate(cabDetailVO.getStartDate());
		cabDetailEntity.setNumberOfSeats(cabDetailVO.getNumberOfSeats());
	}

	@Override
	public CabDetailVO editCabDetail(Long cabDetailId) {
		logger.info("editCabDetail() -> Edit Cab Detail Service call...");
		
		CabDetailEntity cabDetail = new CabDetailEntity();
		CabDetailVO cabDetailVO= null;
		
		cabDetail = cabDetailDAO.editCabDetail(cabDetailId);
		
		cabDetailVO = setCabDetails(cabDetail);
		
		return cabDetailVO;
	}

	@Override
	public String updateCabOwnerStatus(CabOwnerEntity cabOwnerEntity) {
		logger.info("updateCabOwnerStatus() -> Update Cab Owner Status Service call...");
		CabOwnerEntity cabOwner = null;
		
		cabOwner = getCabOwnerDetails(cabOwnerEntity);
		
		return cabDetailDAO.updateCabOwnerStatus(cabOwner);
	}

	@Override
	public String updateCabDetailStatus(CabDetailEntity cabDetailEntity) {
		logger.info("updateCabDetailStatus() -> Update Cab Detail Status Service call...");
		   
		return cabDetailDAO.updateCabDetailStatus(cabDetailEntity);
	}
	
	
	private CabDetailVO setCabDetails(CabDetailEntity cabDetail){
		
		CabDetailVO cab = new CabDetailVO();
		Set<OfficeCityEntity> officeCity = new HashSet<OfficeCityEntity>();
		cab.setCabDetailId(cabDetail.getCabDetailId());
		cab.setCabOwnerId(cabDetail.getCabOwner().getCabOwnerId());
		cab.setDriverFirstName(cabDetail.getDriverFirstName());
		cab.setDriverMiddleName(cabDetail.getDriverMiddleName());
		cab.setDriverLastName(cabDetail.getDriverLastName());
		cab.setDriverLicense(cabDetail.getDriverLicense());
		cab.setAttachments(cabDetail.getAttachments());
		cab.setDriverPhone1(cabDetail.getDriverPhone1());
		cab.setDriverPhone2(cabDetail.getDriverPhone2());
		cab.setInsuranceDate(cabDetail.getInsuranceDate());
		cab.setRegistrationNo(cabDetail.getRegistrationNo());
		cab.setStartDate(cabDetail.getStartDate());
		cab.setNumberOfSeats(cabDetail.getNumberOfSeats());
		cab.setDriverAddress(cabDetail.getDriverAddress());
	
		officeCity= cabDetail.getOfficeCity();
		
		officeCity.forEach(officeCityDetails -> {
			cab.setOfficeCityId(officeCityDetails.getOfficeCityId());
		  });
		 
		return cab;
	}
	
	private List<CabOwnerVO> getCabDetailsDashbaord(List<CabOwnerEntity> cabOwnerList){
		
		List<CabOwnerVO> ownerList = new ArrayList<>();
		
		cabOwnerList.forEach(cabOwnerDetail ->{
			
			List<CabDetailEntity> cabDetailList = new ArrayList<>();
			CabOwnerVO cabowner = new CabOwnerVO();
			List<CabDetailVO> cabList = new ArrayList<>();
			
			cabowner.setCabOwnerId(cabOwnerDetail.getCabOwnerId());
			cabowner.setOwnerPhone1(cabOwnerDetail.getContactPersonPhone1());
			cabowner.setOwnerFirstName(cabOwnerDetail.getOwnerFirstName());
			cabowner.setOwnerMiddleName(cabOwnerDetail.getOwnerMiddleName());
			cabowner.setOwnerLastName(cabOwnerDetail.getOwnerLastName());
			cabowner.setAgencyName(cabOwnerDetail.getAgencyName());
			cabowner.setActive(cabOwnerDetail.getActive());
			cabowner.setStartDate(cabOwnerDetail.getStartDate());
			cabowner.setAttachments(cabOwnerDetail.getAttachments());
			cabowner.setContactPersonPhone1(cabOwnerDetail.getContactPersonPhone1());
			cabowner.setContactPerson(cabOwnerDetail.getContactPerson());
			cabowner.setEndDate(cabOwnerDetail.getEndDate());
			cabowner.setOwnerCityName(cabOwnerDetail.getOwnerCityName());
			cabowner.setOwnerAddress(cabOwnerDetail.getOwnerAddress());
			cabowner.setActive(cabOwnerDetail.getActive());
			
			cabDetailList.addAll(cabOwnerDetail.getCabDetailList());
			
			cabDetailList.forEach(cabDetail ->{
				
				CabDetailVO cab = new CabDetailVO();
				Set<OfficeCityEntity> officeCity = new HashSet<OfficeCityEntity>();
				
				cab.setCabDetailId(cabDetail.getCabDetailId());
				cab.setCabOwnerId(cabDetail.getCabOwner().getCabOwnerId());
				cab.setDriverFirstName(cabDetail.getDriverFirstName());
				cab.setDriverMiddleName(cabDetail.getDriverMiddleName());
				cab.setDriverLastName(cabDetail.getDriverLastName());
				cab.setDriverLicense(cabDetail.getDriverLicense());
				cab.setAttachments(cabDetail.getAttachments());
				cab.setDriverPhone1(cabDetail.getDriverPhone1());
				cab.setDriverPhone2(cabDetail.getDriverPhone2());
				cab.setInsuranceDate(cabDetail.getInsuranceDate());
				cab.setRegistrationNo(cabDetail.getRegistrationNo());
				cab.setStartDate(cabDetail.getStartDate());
				cab.setNumberOfSeats(cabDetail.getNumberOfSeats());
				cab.setDriverAddress(cabDetail.getDriverAddress());
				cab.setActive(cabDetail.getActive());
				
				officeCity= cabDetail.getOfficeCity();
				officeCity.forEach(officeCityDetails -> {
					cab.setOfficeCityId(officeCityDetails.getOfficeCityId());
					cab.setOfficeCityName(officeCityDetails.getCityName());
				});
				
				cabList.add(cab);
				cabowner.setCabDetailList(cabList);
			 });	
			
			ownerList.add(cabowner);
		});
		
		return ownerList;
	}
	
	private CabOwnerEntity  getCabOwnerDetails(CabOwnerEntity cabOwnerEntity) {
		
		CabOwnerEntity cabOwner = new CabOwnerEntity();
		
		cabOwner.setCabOwnerId(cabOwnerEntity.getCabOwnerId());
		cabOwner.setActive(cabOwnerEntity.getActive());
		cabOwner.setUpdateDate(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
		cabOwner.setUpdatedBy(cabOwnerEntity.getUpdatedBy());
		cabOwner.setAgencyName(cabOwnerEntity.getAgencyName());
		cabOwner.setOwnerCityName(cabOwnerEntity.getOwnerCityName());
		cabOwner.setOwnerFirstName(cabOwnerEntity.getOwnerFirstName());
		cabOwner.setContactPersonPhone1(cabOwnerEntity.getContactPersonPhone1());
		cabOwner.setAttachments(Constants.CAB_OWNER_ATTACHMENTS);
		cabOwner.setStartDate(cabOwnerEntity.getStartDate());
		
		return cabOwner;
		
	}
	
}
