package com.discovery.controller;

import java.sql.Timestamp;

import com.gome.pop.dto.order.Order;
import com.gome.pop.dto.order.OrderStatusEnum;
import com.gome.pop.dto.order.ReturnOrder;

public class ControllerBase {
	
	protected void preObjAssembling(String shop_info, Order order, ReturnOrder ro){
		//正向单参数
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		order.setEmpId(-1L);//EMPID
		order.setUserCode("数据修复系统");//履历-操作人
		order.setShopNo(shop_info.split("/")[0]);//店铺编码
		order.setOwnShop(Long.valueOf(shop_info.split("/")[1]));//ownShop
		order.setOrderStateTime(timestamp);//状态变更时间
		//逆向单参数
	    ro.setEmpId(-1L);
        ro.setShop_no(shop_info.split("/")[0]);
        order.setOwnShop(Long.valueOf(shop_info.split("/")[1]));
        ro.setUserCode("数据修复系统");
        ro.setBack_emark("");//修复系统暂不设置
	}
	
	protected void RFLobjAssembling(ReturnOrder ro, String order_no) {
		ro.setNextstate("RFL");
        ro.setCurrstate("RWA");
        ro.setOrderStatus("RWA");
        ro.setOrderStatusEnum(OrderStatusEnum.RWA);
        ro.setOrder_no(Long.valueOf(order_no));
	}
	protected void RRFobjAssembling(ReturnOrder ro, String order_no) {
		ro.setNextstate("RRF");
        ro.setCurrstate("RM");
        ro.setOrderStatus("RM");
        ro.setOrderStatusEnum(OrderStatusEnum.RM);
        ro.setOrder_no(Long.valueOf(order_no));
	}
	protected void RGBCobjAssembling(ReturnOrder ro, String order_no) {
		ro.setNextstate("RGBC");
        ro.setCurrstate("RSC");
        ro.setOrderStatus("RSC");
        ro.setOrderStatusEnum(OrderStatusEnum.RSC);
        ro.setOrder_no(Long.valueOf(order_no));
	}
	protected void RCPobjAssembling(ReturnOrder ro, String order_no) {
		ro.setNextstate("RCP");
        ro.setCurrstate("RWA");
        ro.setOrderStatus("RWA");
        ro.setOrderStatusEnum(OrderStatusEnum.RWA);
        ro.setOrder_no(Long.valueOf(order_no));
	}
	protected void RWAobjAssembling(ReturnOrder ro, String order_no) {
		ro.setNextstate("RWA");
        ro.setCurrstate("RM");
        ro.setOrderStatus("RM");
        ro.setOrderStatusEnum(OrderStatusEnum.RM);
        ro.setOrder_no(Long.valueOf(order_no));
	}
	protected void RMobjAssembling(ReturnOrder ro, String order_no) {
	    ro.setNextstate("RM");
        ro.setCurrstate("RSC");
        ro.setOrderStatus("RSC");
        ro.setOrderStatusEnum(OrderStatusEnum.RSC);
        ro.setOrder_no(Long.valueOf(order_no));
       

	}
	protected void DFCobjAssembling(Order order, String order_no) {
		order.setSubOrderId(Long.valueOf(order_no));
        order.setOrderState("DFC");
        order.setOrderStatus("DFC");
        order.setOrderStatusEnum(OrderStatusEnum.DFC);
        order.setOrderStateRemark("商家取消");
        order.setOpinionDesc("同意");
        order.setStatusReasonCode("");
        order.setStatusReasonDesc("");
	}
	protected void RCCobjAssembling(Order order, String order_no) {
		order.setSubOrderId(Long.valueOf(order_no));
		order.setOrderState("RCC");
        order.setOrderStatus("RCC");
        order.setOrderStatusEnum(OrderStatusEnum.RCC);
        order.setOrderStateRemark("客服取消");
        order.setOpinionDesc("同意");//商家备注
        order.setStatusReasonCode("");//修复系统修复的数据 暂不设置
        order.setStatusReasonDesc("");
	}
	protected void RTobjAssembling(Order order, String order_no,
			String tracking_info) {
		 order.setSubOrderId(Long.valueOf(order_no));
		 order.setOrderState("RT");
         order.setOrderStatus("RT");
         order.setOrderStatusEnum(OrderStatusEnum.RT);
         order.setOrderStateRemark("买家已拒收");//履历描述
         order.setTrackingNo(tracking_info.split("/")[0]+"_&_"+tracking_info.split("/")[1]+"abcba"+tracking_info.split("/")[2]+"abcba2");
		
	}
	protected void DLobjAssembling(Order order, String order_no,String tracking_info) {
		 order.setSubOrderId(Long.valueOf(order_no));
		 order.setOrderState("DL");
         order.setOrderStatus("DL");
         order.setOrderStatusEnum(OrderStatusEnum.DL);
         order.setOrderStateRemark("已妥投");//履历描述
         order.setTrackingNo(tracking_info.split("/")[0]+"_&_"+tracking_info.split("/")[1]+"abcba"+tracking_info.split("/")[2]+"abcba2");
	}
	protected void EXobjAssembling(Order order, String order_no, String tracking_info) {
		 order.setSubOrderId(Long.valueOf(order_no));
		 order.setOrderState("EX");
         order.setOrderStatus("EX");
         order.setOrderStatusEnum(OrderStatusEnum.EX);
         order.setOrderStateRemark("已出库");//履历描述
         order.setTrackingNo(tracking_info.split("/")[0]+"_&_"+tracking_info.split("/")[1]+"abcba"+tracking_info.split("/")[2]+"abcba2");
	}
	protected void PPobjAssembling(Order order, String order_no){
		order.setOrderState("PP");
		order.setOrderStatus("PP");
		order.setSubOrderId(Long.valueOf(order_no));
		order.setOrderStateRemark("订单处理中");
		order.setOrderStatusEnum(OrderStatusEnum.PP);
	}

	
	protected void DLobjAssembling2(Order order, String order_no,String tracking_info) {
		order.setSubOrderId(Long.valueOf(order_no));
		order.setOrderState("DL");
        order.setOrderStatus("DL");
        order.setOrderStatusEnum(OrderStatusEnum.DL);
        order.setOrderStateRemark("已妥投");//履历描述
        order.setTrackingNo(tracking_info.split("/")[0]+"_&_"+tracking_info.split("/")[1]+"abcba"+tracking_info.split("/")[2]+"abcba2");
        order.setP_lsp_code(tracking_info.split("/")[1]);
        order.setP_lsp_name(tracking_info.split("/")[2]);
        order.setTrackingNo(tracking_info.split("/")[0]);

	}
	protected void RTobjAssembling2(Order order, String order_no,
			String tracking_info) {
		 order.setSubOrderId(Long.valueOf(order_no));
		 order.setOrderState("RT");
         order.setOrderStatus("RT");
         order.setOrderStatusEnum(OrderStatusEnum.RT);
         order.setOrderStateRemark("买家已拒收");//履历描述
         order.setTrackingNo(tracking_info.split("/")[0]+"_&_"+tracking_info.split("/")[1]+"abcba"+tracking_info.split("/")[2]+"abcba2");
         order.setP_lsp_code(tracking_info.split("/")[1]);
         order.setP_lsp_name(tracking_info.split("/")[2]);
         order.setTrackingNo(tracking_info.split("/")[0]);
	}
	protected void RCCobjAssembling2(Order order, String order_no) {
		order.setSubOrderId(Long.valueOf(order_no));
//		order.setOrderState("RCC");
        order.setOrderStatus("RCC");
        order.setOrderStatusEnum(OrderStatusEnum.RCC);
        order.setOrderStateRemark("客服取消");
        order.setOpinionDesc("同意");//商家备注
        order.setStatusReasonCode("");//修复系统修复的数据 暂不设置
        order.setStatusReasonDesc("");
        order.setOrderState("CL");
	}
	protected void DFCobjAssembling2(Order order, String order_no) {
		order.setSubOrderId(Long.valueOf(order_no));
//      order.setOrderState("DFC");
        order.setOrderStatus("DFC");
        order.setOrderStatusEnum(OrderStatusEnum.DFC);
        order.setOrderStateRemark("商家取消");
        order.setOpinionDesc("同意");
        order.setStatusReasonCode("");
        order.setStatusReasonDesc("");
        order.setOrderState("CL");
	}
}
