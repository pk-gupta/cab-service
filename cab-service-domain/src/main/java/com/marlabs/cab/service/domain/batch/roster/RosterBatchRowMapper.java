package com.marlabs.cab.service.domain.batch.roster;

import org.springframework.batch.item.excel.RowMapper;
import org.springframework.batch.item.excel.support.rowset.RowSet;

/**
 * This class demonstrates how we can implement a row mapper that maps
 * a row found from an Excel document into a {@code StudentDTO} object. If
 * the Excel document has no header, we have to use this method for transforming
 * the input data into {@code StudentDTO} objects.
 *
 * @author SureshGowda
 */
public class RosterBatchRowMapper implements RowMapper<RosterMapperVO> {

    @Override
    public RosterMapperVO mapRow(RowSet rowSet) throws Exception {
    	RosterMapperVO rosterMapperVO = new RosterMapperVO();

    	rosterMapperVO.setEmployeeId(rowSet.getColumnValue(0));
    	rosterMapperVO.setEmployeePhone(rowSet.getColumnValue(1));
    	rosterMapperVO.setManagerId(rowSet.getColumnValue(2));
    	rosterMapperVO.setProjectId(rowSet.getColumnValue(3));
    	rosterMapperVO.setFromDate(rowSet.getColumnValue(4));
    	rosterMapperVO.setToDate(rowSet.getColumnValue(5));//DateUtil.isCellDateFormatted
    	rosterMapperVO.setRequestServiceType(rowSet.getColumnValue(6));
    	
    	rosterMapperVO.setRequestSpecificDate(rowSet.getColumnValue(7));
    	
    	rosterMapperVO.setRequestType(rowSet.getColumnValue(8));
    	rosterMapperVO.setOficeBranch(rowSet.getColumnValue(9));
    	rosterMapperVO.setWeekOff1(rowSet.getColumnValue(10));
    	rosterMapperVO.setWeekOff2(rowSet.getColumnValue(11));
    	rosterMapperVO.setWeekOff3(rowSet.getColumnValue(12));
    	rosterMapperVO.setNoOffWeekOffs(rowSet.getColumnValue(13));
    	rosterMapperVO.setLandMark(rowSet.getColumnValue(14));
    	rosterMapperVO.setLoginTime(rowSet.getColumnValue(15));
    	
    	rosterMapperVO.setLogOutTime(rowSet.getColumnValue(16));
    	rosterMapperVO.setReason(rowSet.getColumnValue(17));
    	rosterMapperVO.setManagerRemark(rowSet.getColumnValue(18));
    	
        return rosterMapperVO;
    }
}
