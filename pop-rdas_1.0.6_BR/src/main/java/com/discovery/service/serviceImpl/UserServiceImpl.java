package com.discovery.service.serviceImpl;

import java.util.Random;

import org.springframework.stereotype.Service;

import com.common.Eum.Constant;
import com.common.utils.ProTool;
import com.discovery.service.serviceInte.UserService;
import com.founder.bbc.util.SMSUtil2;
import com.founder.bbc.util.SMSUtil3;
import com.founder.bbc.util.redis.RedisUtil;
import com.gome.loom.model.TpModel;
@Service
public class UserServiceImpl implements UserService {
	//验证码生成字符集__仅仅数字
	private static final 	char[] CODE_SEQUENCE_JUST_NUMBER = {'0','1','2', '3', '4', '5', '6', '7', '8', '9' };
		//验证码长度
	private Integer codeCount = 6;
	public Integer getCodeCount() {
		return codeCount;
	}
	public void setCodeCount(Integer codeCount) {
		this.codeCount = codeCount;
	}
		
	//0分钟
	private Integer expireMinites = 3;
	public Integer getExpireMinites() {
		return expireMinites;
	}
	public void setExpireMinites(Integer expireMinites) {
		this.expireMinites = expireMinites;
	}
	
	/**
	 * 发送手机验证码
	 */
	public boolean sendMessageForShopUser(String userName) throws Exception {
		String key = "npop_rads_" + userName;
		String code = RedisUtil.get(key, String.class);
		if(code == null) {
			//根据userName 去配置文件中查询出来配置的手机号 不要从页面取 数据不安全
			String phoneNo = new ProTool().getUserInfo(Constant.CTString.UserInfoProperties.get_code(), userName,"phoneNo");
			String messageVerifyCode = generatorMessageVerifyCodeJustNumber();
			boolean flag = sendMessageFromGome(phoneNo, messageVerifyCode);
			RedisUtil.set(key, messageVerifyCode, 180);
			if(flag){
				return true;
			}else{
				return false;
			}
		} else {
			return false;
		}
	}
	
	
	/**
	 * 生成验证码__只含数字
	 */
	private String generatorMessageVerifyCodeJustNumber() {
		//randomCode用于保存随机产生的验证码，以便用户登录进行验证。 
		StringBuffer randomCode = new StringBuffer(); 
		Random random = new Random(); 
    	//随机产生codeCount数字的验证码。 
		for (int i = 0; i < codeCount; i++) {
    		String strRand = String.valueOf(CODE_SEQUENCE_JUST_NUMBER[random.nextInt(CODE_SEQUENCE_JUST_NUMBER.length)]); 
    		//将产生的n个随机数组合在一起。 
    		randomCode.append(strRand); 
		}
		return randomCode.toString();
	}
	
	
	/**
	 * 发短信
	 * @param targetPhoneNumber
	 * @param message
	 * @return
	 */
	private  boolean sendMessageFromGome(String targetPhoneNumber,String verifyCode){
		TpModel smsModel = new TpModel("pop_interface_GomeDataRepairSysLogin","9011"); //对应申请的BusinessName，短信模板Id 
		smsModel.setPhone("86"+targetPhoneNumber); //发送的手机号
		smsModel.putTempParams("code", verifyCode);
		smsModel.setIntervalTime(0);//是否延迟发送如果延迟发送需要设置,单位:小时.（实时发送丌需要设置，默认0） 
		//dubbo调用发送短信(result一定会返回，丌用做null判断) 
		return SMSUtil3.sendMsg(smsModel);

	}
	
	
}
