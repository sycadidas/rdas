package com.discovery.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.founder.bbc.util.JsonUtil;
import com.gome.pop.base.exception.ExException;
import com.gome.pop.dto.logistics.LogisticsCorp;
import com.gome.pop.dto.order.Order;
import com.gome.pop.dto.order.TrackingAddress;
import com.gome.pop.dto.system.NPOP_SYSTEM;
import com.gome.pop.interfaze.communication.service.Interface;
import com.gome.pop.interfaze.order.service.OrderService;
import com.gome.pop.interfaze.order.service.TrackingService;
import com.gome.pop.interfaze.shop.service.ShopCorpService;
/**
 * 订单数据发DRAG
 * @author yeaihua
 *
 */
@Controller
@RequestMapping("/orderToDrag")
public class OrderToDragInfoAction {
	protected final Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private Interface<Order, Boolean> gomeTrackingOrderInfoToDrag;
	@Autowired
	private Interface<Order, Boolean> gomeTrackingExSender;
	
	//引入新工程ShopCorpService
	@Autowired
	private ShopCorpService shopCorpService;
		
	//引入新工程TrackingService
	@Autowired
	private TrackingService trackingService;
	@Autowired
	private OrderService orderService;
	/**
	 * 查询发货信息
	 * @param shopNo 店铺编码
	 * @param subOrderId 订单号
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/queryDeliveyInfo.action")
	@ResponseBody
	public String queryDeliveyInfo(String shopNo,String subOrderId,HttpServletRequest req) {
 		try {
 			//还得查询是否是代运订单
 			Order order = orderService.queryOrderIsGomeTracking(Long.parseLong(subOrderId));
 			if(order!=null){
 				if("N".equals(order.getGomeTracking())){
 					return "noGomeTracking";
 				}else{
 					logger.error("查询代运订单信息");
 					String mobile="";
 					Map map = new HashMap();
 					map.put("shop_no", order.getOwnShop());
 					map.put("isGroupBy", true);
 					map.put("showGroupBy", true);
 					//如果查询不到发货地或者什么，就得根据店铺编码查询出该店铺下的所有发货地进行显示，放session
 					List<LogisticsCorp> lcCorpsTemp = shopCorpService.queryShopCorp(map);
 					List<LogisticsCorp> lcCorps = new ArrayList<LogisticsCorp>();
 					if(lcCorpsTemp!=null&&lcCorpsTemp.size()!=0){
 						for(int i=0;i<lcCorpsTemp.size();i++){
 							if("2".equals(lcCorpsTemp.get(i).getLogisticsFlag())){//将不是国美代运的物流移除
 								LogisticsCorp corp = lcCorpsTemp.get(i);
 								corp.setLogisticsName(java.net.URLEncoder.encode(corp.getLogisticsName(),"UTF-8"));
 								lcCorps.add(corp);
 							}
 						}
 						logger.error("国美代运物流公司个数："+lcCorps.size());
 					}
 					//发货地
 					List<TrackingAddress> listaddress = trackingService.queryGomeAdress(shopNo);
 					if(listaddress!=null&&listaddress.size()!=0){
 						for(TrackingAddress add:listaddress){
 							add.setAddress_short_name(java.net.URLEncoder.encode(add.getAddress_short_name(), "UTF-8"));
 						}
 						logger.error("发货地个数："+listaddress.size());
 					}
 					String gomeGreightType = orderService.queryGomeFreightType(shopNo);
 					order.setGomeFreightType(gomeGreightType);
 					List<String> orderList = new ArrayList<String>();
 					orderList.add(subOrderId);
 					//客户手机号可以调OMS接口查
 					Map<String,String> mapM = orderService.searchCustomerMobileNoByOrderNo(orderList, NPOP_SYSTEM.BG,"pop_rads",shopNo);//传店铺编码+手机号查询(返回订单号与手机号以map形式返回)或者传多个订单编码查询
 					if(!mapM.isEmpty()){
 						mobile = mapM.get(subOrderId);
 					}
 					order.setMobile(mobile);
 					logger.error("订单"+subOrderId+"手机号为："+mobile);
 					Map<String,Object> jsonParam = new HashMap<String,Object>();
 					jsonParam.put("lcCorpList", lcCorps);
 					jsonParam.put("addressList", listaddress);
 					jsonParam.put("mobile", mobile);
 					String data = com.alibaba.fastjson.JSON.toJSONString(jsonParam, true);
 					return data;
 				}
 			}else{
 				return "noOrder";
 			}
 		} catch (Exception e) {
 			logger.error(e.getMessage(), e);
 			return "false";
 		}
 	}
	
	/**
	 * 发送代运订单报文信息B4.3跟订单状态B3.5到DRAG
	 * @param shopNo 店铺编码
	 * @param subOrderId 订单号
	 * @return
	 */
	@RequestMapping("/sendOrderXml.action")
	@ResponseBody
	public String sendOrderXml(Long shopNo,String subOrderId,String trackingNo,String orderNum,String mobile,String logistics,String trackingAddressId){
		Map<String,Boolean> m = new HashMap<String,Boolean>();
		m.put("isGroupBy", true);
		try {
			//查询
			Order order = orderService.queryOrderByIdForGomeTracking(subOrderId,null, m);
			if(order!=null){
				if(order.getConsignee()!=null){
					order.getConsignee().setMobile(mobile);
				}
				//发送订单SO(B4.3接口)
				Boolean flag_so = gomeTrackingOrderInfoToDrag.communicate(order);
				if(!flag_so){
					throw new ExException(new RuntimeException("出库：发送订单信息发生异常：订单号：subOrderId:"+order.getSubOrderId()));
				}
				//发送订单SO(B3.5接口)
				order.setTrackingNo(trackingNo+"_&_"+logistics);//63333_&_21000118abcba国美代运（顺丰）abcba2
				logger.error("trackingAddressId为："+trackingAddressId);
				List<TrackingAddress> trackingAddress = trackingService.getTrackingAddresse(Long.parseLong(trackingAddressId));
				if(trackingAddress!=null && trackingAddress.size() > 0){
					logger.error("trackingAddress为："+trackingAddress.get(0).getDetail_address());
					trackingAddress.get(0).setTel(mobile);
					order.setTrackingAddress(trackingAddress.get(0));
				}
				boolean flag_status = gomeTrackingExSender.communicate(order);
				if(!flag_status){
					throw new ExException(new RuntimeException("出库：发送订单状态发生异常：订单号：subOrderId:"+order.getSubOrderId()));
				}
				return "true";
			}else{
				return "false";
			}
		}catch (Exception e) {
 			logger.error(e.getMessage(), e);
 			return "false";
 		}
	}
}
