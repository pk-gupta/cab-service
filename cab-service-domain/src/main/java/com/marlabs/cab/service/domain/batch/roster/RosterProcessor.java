package com.marlabs.cab.service.domain.batch.roster;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;

import com.marlabs.cab.service.common.constant.Constants;

/**
 * This custom {@code ItemProcessor} simply writes the information of the
 * processed student to the log and returns the processed object.
 *
 * @author SureshGowda
 */
public class RosterProcessor implements ItemProcessor<RosterMapperVO, RosterMapperVO> {

	private static Logger logger = LogManager.getLogger(RosterProcessor.class);

	@Override
	public RosterMapperVO process(RosterMapperVO rosterMapperVO) throws Exception {

		logger.info("process() -> Started Roster Processor...");

		if (rosterMapperVO.getRequestType().equalsIgnoreCase(Constants.REQUEST_TYPE_SINGLE)) {
			rosterMapperVO.setFromDate(rosterMapperVO.getRequestSpecificDate());
			rosterMapperVO.setToDate(rosterMapperVO.getRequestSpecificDate());
		}

		logger.debug("process() -> Completed Roster Processor");

		return rosterMapperVO;
	}
}
