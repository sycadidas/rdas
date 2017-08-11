package com.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.common.Eum.Constants;
import com.founder.bbc.diamond.DiamondOP;
import com.founder.bbc.util.StreamUtil;

public class DiamondInitListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		        //properties
				StreamUtil.setGroupId(Constants.DIAMOND_RDAS_GROUP);
				StreamUtil.setDataId(Constants.DIAMOND_RDAS_PROPERTIES_DATA);
				DiamondOP.init(Constants.DIAMOND_RDAS_GROUP, Constants.DIAMOND_RDAS_PROPERTIES_DATA);
				//其他配置
//				DiamondOP.init(Constants.DIAMOND_SBG_GROUP, Constants.DIAMOND_SBG_SYS_CONF);
	}

}
