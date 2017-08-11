package com.discovery.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gome.pop.interfaze.communication.service.Interface;
import com.gome.pop.interfaze.order.service.OrderStatusAlter;
import com.gome.pop.interfaze.order.service.OrderStatusContext;
import com.gome.pop.interfaze.shop.service.ShopCorpService;
import com.gome.pop.interfaze.warehouse.WareHouseService;
import com.gome.pop.dto.logistics.LogisticsCorp;
import com.gome.pop.dto.order.Order;
import com.gome.pop.dto.order.OrderStatusEnum;
import com.gome.pop.dto.order.ReturnOrder;
import com.gome.pop.dto.warehouse.WareHouse;

import java.sql.Timestamp;
/**
 * 订单数据相关
 * @author sunyanchen
 */
@Controller
@RequestMapping("/order")
public class OrderDataRepair extends ControllerBase{
	protected final Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private OrderStatusContext context;//pop-order提供的订单处理接口
	@Autowired
	private OrderStatusAlter OrderAlterService;//回滚订单状态的接口
	@Autowired
	private Interface<Order, Boolean> ppSender;//推送pp状态的接口
	@Autowired
	private Interface<Order, Boolean> exSender;//推送ex状态的接口
	@Autowired
	private Interface<Order, Boolean> dlSender;//推送dl状态的接口
	@Autowired
	private Interface<Order, Boolean> rtSender;//推送rt状态的接口
	@Autowired
	private Interface<Order, Boolean> clSender;//推送cl状态的接口
	@Autowired
	private Interface<ReturnOrder, Boolean> rscSender;
	@Autowired
	private Interface<ReturnOrder, Boolean> rmSender;
	@Autowired
	private Interface<ReturnOrder, Boolean> rwaSender;
	@Autowired
	private Interface<ReturnOrder, Boolean> rbgcSender;
	@Autowired
	private Interface<ReturnOrder, Boolean> rrfSender;
	
	@Autowired
	private WareHouseService remoteWareHouseService;
	
	@Autowired
	private ShopCorpService shopCorpService;
	
	
	
	//由主页面跳转至atg修复页面
	@RequestMapping("/atgRepairPage.action")
	public String gotoATGRepairPage(){
		return "orderAtgRepairPage";
	}
	//由atg修复页面跳转至drg修复页面
	@RequestMapping("/drgRepairPage.action")
	public String gotoDRGRepairPage(){
		return "orderDrgRepairPage";
	}
	
	//回滚状态后重新处理订单&&推送报文
	@RequestMapping("/send.action")
	@ResponseBody
	public String AlterAndSendOrderInfo(String status,String order_no,String shop_info,String tracking_info,String sign){
		String flag = "true";
		Order order = new Order();
		ReturnOrder ro = new ReturnOrder();
		preObjAssembling(shop_info,order,ro);
		switch(status)
		{
		case "PP":
			PPobjAssembling(order,order_no);
			break;
		case "EX":
			if("alter_send".equals(sign)){
				EXobjAssembling(order,order_no,tracking_info);
			}else{
				EXobjAssembling2(order,order_no,tracking_info);
			}
			break;
		case "DL":
			if("alter_send".equals(sign)){
				DLobjAssembling(order,order_no,tracking_info);
			}else{
				DLobjAssembling2(order,order_no,tracking_info);
			}
			break;
		case "RT":
			if("alter_send".equals(sign)){
				RTobjAssembling(order,order_no,tracking_info);
			}else{
				RTobjAssembling2(order,order_no,tracking_info);
			}
			break;
		case "RCC":
			if("alter_send".equals(sign)){
				RCCobjAssembling(order,order_no);
			}else{
				RCCobjAssembling2(order,order_no);
			}
			break;
		case "DFC":	
			if("alter_send".equals(sign)){
				DFCobjAssembling(order,order_no);
			}else{
				DFCobjAssembling2(order,order_no);
			}
			break;
        case "RM":
			RMobjAssembling(ro,order_no);
			break;
		case "RWA":
			RWAobjAssembling(ro,order_no);
			break;
		case "RCP":
			RCPobjAssembling(ro,order_no);
			break;
		case "RGBC":
			RGBCobjAssembling(ro,order_no);
			break;
		case "RRF":
			RRFobjAssembling(ro,order_no);
		case "RFL":	
			RFLobjAssembling(ro,order_no);
			break;
		}
		//推送状态
		try{
			if("alter_send".equals(sign)){
				if("PP".equals(status)||"EX".equals(status)||"DL".equals(status)||"RT".equals(status)||"RCC".equals(status)||"DFC".equals(status)){
					   context.processStatus(order);
					}else{
					   context.processStatus(ro);
				}
			}else{
			 //只推送报文
				switch(status)
				{
				case "PP":
					ppSender.communicate(order);
					break;
				case "EX":
					exSender.communicate(order);
					break;
				case "DL":
					dlSender.communicate(order);
					break;
				case "RT":
					rtSender.communicate(order);
					break;
				case "RCC":
					clSender.communicate(order);
					break;
				case "DFC":	
					clSender.communicate(order);
					break;
		        case "RM":
		        	rscSender.communicate(ro);
					break;
				case "RWA":
					rmSender.communicate(ro);
					break;
				case "RCP":
					rwaSender.communicate(ro);
					break;
				case "RGBC":
					rscSender.communicate(ro);
					break;
				case "RRF":
					rmSender.communicate(ro);
					break;
				}
			}
			
			
		}catch(Exception e){
			flag = "false";
			logger.error("推送信息报错了");
			e.printStackTrace();
		}
		return flag;
	}
	
	//回滚订单状态的方法
	@RequestMapping("/alter.action")
	@ResponseBody
	public String OrderStatusAlter(String orderNo,String status){
		String falg = "false" ;
		try{
			int alterResult = OrderAlterService.alterStatusByOrder_No(orderNo, status);
			if(1==alterResult){
				falg = "true";
			}else{
				falg = "false";
			}
		}catch(Exception e){
			logger.error("状态回滚失败");
			e.printStackTrace();
		}
		return falg;
	}
	
	//仅推报文的数据组装
	private void EXobjAssembling2(Order order, String order_no, String tracking_info) {
	    order.setSubOrderId(Long.valueOf(order_no));
		order.setOrderState("EX");
        order.setOrderStatus("EX");
        order.setOrderStatusEnum(OrderStatusEnum.EX);
        order.setOrderStateRemark("已出库");//履历描述
        order.setTrackingNo(tracking_info.split("/")[0]+"_&_"+tracking_info.split("/")[1]+"abcba"+tracking_info.split("/")[2]+"abcba2");
        //商家自由物流 增加承运商信息
        if("99900046".equals(tracking_info.split("/")[1])){
        	WareHouse warehouse = new WareHouse();
			warehouse.setShopNo(Long.parseLong(order.getShopNo()));
			warehouse.setWareHouseId(tracking_info.split("/")[3]);
			WareHouse w = remoteWareHouseService.queryWareHouseByWarehouseID(warehouse);
			if(w!=null){
				order.setContectFixedPhone(w.getContactnumber());
				order.setContectTelPhone(w.getTelephone());
				order.setContectName(w.getContact());
			}
        }else{
        	//根据承运商编码查询快递公司名称跟电话
			LogisticsCorp crop = shopCorpService.queryLogisticsCorpById(Integer.parseInt(tracking_info.split("/")[1]));
			if(crop!=null){
				order.setContectFixedPhone(crop.getServicePhone());
			}
        }
	}
}
