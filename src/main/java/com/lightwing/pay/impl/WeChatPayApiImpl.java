package com.lightwing.pay.impl;

import java.io.UnsupportedEncodingException;
import java.net.*;
import java.security.MessageDigest;
import java.util.*;

/**
 * 微信支付接口参数封装实现类；
 * 根据：https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=9_1
 * 或 ：https://pay.weixin.qq.com/wiki/doc/api/native_sl.php?chapter=9_1
 * 来决定哪些参数为必填。
 *
 * @author Lightwing Ng
 */
public class WeChatPayApiImpl {
    private Map<String, Object> map;
    private List<String> requiredList;
    private String key;

    /**
     * 获取微信接口地址
     *
     * @return
     */
    public String getUrl() {
        return "https://api.mch.weixin.qq.com/pay/unifiedorder";
    }

    /**
     * 必填 公众账号ID
     * 例：wxd678efh567hg6787
     * 说明：微信分配的公众账号ID（企业号corpid即为此appId）
     *
     * @param appid
     */
    public void setAppid(String appid) {
        if (null != appid) {
            this.map.put("appid", appid);
        }
        // 此变量没有实际的使用到，单纯为了编码时方便定位和是否漏掉参数而存在
        // 1   公众账号ID
    }

    /**
     * 必填 商户号
     * 例：1230000109
     * 说明：微信支付分配的商户号
     *
     * @param mch_id
     */
    public void setMch_id(String mch_id) {
        if (null != mch_id) {
            this.map.put("mch_id", mch_id);
        }
        //1   商户号
    }

    /**
     * 子商户公众账号ID
     * 例：wxd678efh567hg6787
     * 说明：微信分配的子商户公众账号ID，如需在支付完成后获取sub_openid则此参数必传。
     *
     * @param sub_appid
     */
    public void setSub_appid(String sub_appid) {
        if (null != sub_appid) {
            this.map.put("sub_appid", sub_appid);
        }
        // 子商户公众账号ID
    }

    /**
     * 必填 子商户号
     * 例：1230000109
     * 说明：微信支付分配的子商户号
     *
     * @param sub_mch_id
     */
    public void setSub_mch_id(String sub_mch_id) {
        if (null != sub_mch_id) {
            this.map.put("sub_mch_id", sub_mch_id);
        }
        //1   子商户号
    }

    /**
     * 设备号
     * 例：013467007045764
     * 说明：终端设备号(门店号或收银设备ID)，注意：PC网页或公众号内支付请传"WEB"
     *
     * @param device_info
     */
    public void setDevice_info(String device_info) {
        if (null != device_info) {
            this.map.put("device_info", device_info);
        }
        // 设备号
    }

    /**
     * 必填 随机字符串
     * 例：5K8264ILTKCH16CQ2502SI8ZNMTM67VS
     * 说明：随机字符串，不长于32位。https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=4_3
     * 默认值：30位长度由A-Z,0-9组成的随机串
     *
     * @param nonce_str
     */
    private void setNonce_str(String nonce_str) {
        if (null != nonce_str) {
            this.map.put("nonce_str", nonce_str);
        }
    }

    /**
     * 必填 签名【无需手动设值，调用时会自动计算并赋值】
     * 例：C380BEC2BFD727A4B6845133519F3AD6
     * 说明：签名，详见 https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=4_3
     * 默认值：调用时根据相应的算法自动进行计算或复制
     *
     * @param sign
     */
    private void setSign(String sign) {
        if (null != sign) {
            this.map.put("sign", sign);
        }
    }

    /**
     * 必填 商品描述
     * 例：Ipad mini  16G  白色
     * 说明:商品或支付单简要描述
     *
     * @param body
     */
    public void setBody(String body) {
        if (null != body) {
            this.map.put("body", body);
        }
    }

    /**
     * 商品详情
     * 例：Ipad mini  16G  白色
     * 说明:商品名称明细列表
     *
     * @param detail
     */
    public void setDetail(String detail) {
        if (null != detail) {
            this.map.put("detail", detail);
        }
    }

    /**
     * 附加数据
     * 例：深圳分店
     * 说明：附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
     *
     * @param attach
     */
    public void setAttach(String attach) {
        if (null != attach) {
            this.map.put("attach", attach);
        }
    }

    /**
     * 必填 商户订单号
     * 例：20150806125346
     * 说明：商户系统内部的订单号,32个字符内、可包含字母,。
     * 其他说明 https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=4_2
     *
     * @param out_trade_no
     */
    public void setOut_trade_no(String out_trade_no) {
        if (null != out_trade_no) {
            this.map.put("out_trade_no", out_trade_no);
        }
    }

    /**
     * 货币类型  默认为：CNY 人民币
     * 例：CNY
     * 说明：符合ISO 4217标准的三位字母代码，默认人民币：CNY
     * 其他详见 https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=4_2
     *
     * @param fee_type
     */
    public void setFee_type(String fee_type) {
        if (null != fee_type) {
            this.map.put("fee_type", fee_type);
        }
    }

    /**
     * 必填 总金额，必须大于0 单位：分
     * 例：888【8元8角8分】
     * 说明：订单总金额，单位为分
     *
     * @param total_fee
     */
    public void setTotal_fee(Integer total_fee) {
        if (null != total_fee && total_fee > 0) {
            this.map.put("total_fee", total_fee);
        }
    }

    /**
     * 必填 终端IP
     * 例：	123.12.12.123
     * 说明：APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。
     * 默认值：本机内网IP
     *
     * @param spbill_create_ip
     */
    private void setSpbill_create_ip(String spbill_create_ip) {
        if (null != spbill_create_ip) {
            this.map.put("spbill_create_ip", spbill_create_ip);
        }
    }

    /**
     * 交易起始时间
     * 例：20091225091010
     * 说明：订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。
     * 详细：https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=4_2
     *
     * @param time_start
     */
    public void setTime_start(String time_start) {
        if (null != time_start) {
            this.map.put("time_start", time_start);
        }
    }

    /**
     * 交易结束时间
     * 例：20091225091010
     * 说明：订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。
     * 详细：https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=4_2
     *
     * @param time_expire
     */
    public void setTime_expire(String time_expire) {
        if (null != time_expire) {
            this.map.put("time_expire", time_expire);
        }
    }

    /**
     * 商品标记
     * 例：WXG
     * 说明：商品标记，代金券或立减优惠功能的参数
     * 说明详见：https://pay.weixin.qq.com/wiki/doc/api/tools/sp_coupon.php?chapter=12_1
     *
     * @param goods_tag
     */
    public void setGoods_tag(String goods_tag) {
        if (null != goods_tag) {
            this.map.put("goods_tag", goods_tag);
        }
    }

    /**
     * 必填 通知地址（回调地址）
     * 例：http://www.weixin.qq.com/wxpay/pay.php
     * 说明：接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
     *
     * @param notify_url
     */
    public void setNotify_url(String notify_url) {
        if (null != notify_url) {
            this.map.put("notify_url", notify_url);
        }
    }

    /**
     * 必填 交易类型
     * 例：NATIVE
     * 说明：取值如下：JSAPI，NATIVE，APP
     * 详细说明：https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=4_2
     *
     * @param trade_type
     */
    public void setTrade_type(String trade_type) {
        if (null != trade_type) {
            this.map.put("trade_type", trade_type);
        }
    }

    /**
     * 商品ID
     * 例：12235413214070356458058
     * 说明：trade_type=NATIVE，此参数必传。此id为二维码中包含的商品ID，商户自行定义。
     *
     * @param product_id
     */
    public void setProduct_id(String product_id) {
        if (null != product_id) {
            this.map.put("product_id", product_id);
        }
    }

    /**
     * 指定支付方式
     * 例：no_credit
     * 说明：no_credit--指定不能使用信用卡支付
     *
     * @param limit_pay
     */
    public void setLimit_pay(String limit_pay) {
        if (null != limit_pay) {
            this.map.put("limit_pay", limit_pay);
        }
    }

    /**
     * 用户标识
     * 例：oUpF8uMuAJO_M2pxb1Q9zNjWeS6o
     * 说明：trade_type=JSAPI，此参数必传，用户在商户appid下的唯一标识。openid如何获取，可参考https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=4_4
     * 企业号请使用http://qydev.weixin.qq.com/wiki/index.php?title=OAuth%E9%AA%8C%E8%AF%81%E6%8E%A5%E5%8F%A3
     * 获取企业号内成员userid，再调用http://qydev.weixin.qq.com/wiki/index.php?title=Userid%E4%B8%8Eopenid%E4%BA%92%E6%8D%A2%E6%8E%A5%E5%8F%A3
     * 进行转换
     *
     * @param openid
     */
    public void setOpenid(String openid) {
        if (null != openid) {
            this.map.put("openid", openid);
        }
    }

    /**
     * 用户子标识
     * 例：oUpF8uMuAJO_M2pxb1Q9zNjWeS6o
     * 说明：trade_type=JSAPI，此参数必传，用户在商户appid下的唯一标识。openid如何获取，可参考https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=4_4
     * 企业号请使用http://qydev.weixin.qq.com/wiki/index.php?title=OAuth%E9%AA%8C%E8%AF%81%E6%8E%A5%E5%8F%A3
     * 获取企业号内成员userid，再调用http://qydev.weixin.qq.com/wiki/index.php?title=Userid%E4%B8%8Eopenid%E4%BA%92%E6%8D%A2%E6%8E%A5%E5%8F%A3
     * 进行转换
     *
     * @param sub_openid
     */
    public void setSub_openid(String sub_openid) {
        if (null != sub_openid) {
            this.map.put("sub_openid", sub_openid);
        }
    }

    /**
     * 将不能为空的列指定到list中并返回这个list
     *
     * @return
     */
    private List<String> requiredParam() {
        // 定义不能为空的字段
        List<String> list = new ArrayList<String>();
        list.add("appid");
        list.add("mch_id");
        list.add("sub_mch_id");
        list.add("nonce_str");
        list.add("sign");
        list.add("body");
        list.add("out_trade_no");
        list.add("total_fee");
        list.add("spbill_create_ip");
        list.add("notify_url");
        list.add("trade_type");
        return list;
    }

    /**
     * appid,mch_id,nonce_str,sign,body,out_trade_no,total_fee,spbill_create_ip,notify_url,trade_type
     * 不能为空或NULL，详见set方法说明
     * 所有属性默认为null
     * 文档地址：https://pay.weixin.qq.com/wiki/doc/api/native_sl.php?chapter=9_1
     *
     * @param key 为商户的key【API密匙】
     */
    public WeChatPayApiImpl(String key) {
        super();
        this.key = key;
        map = new HashMap<String, Object>();
        List<String> tmpList = requiredParam();
        if (tmpList.size() > 0) {
            this.requiredList = tmpList;
        } else {
            this.requiredList = null;
        }
    }

    /**
     * 将所有不为null的属性封装成map并返回
     */
    private Map<String, Object> getMap() throws Exception {
        this.setDefaultValue();
        if (null != this.requiredList) {
            for (String s : this.requiredList) {
                // 检查为空的值是否必填
                if (!this.map.containsKey(s)) {
                    // 出现未包含的必填项
                    throw new Exception(s + "--为必填项。请检查必填项：" + this.requiredList + " 是否填写正确。");
                }
                if ("total_fee".equals(s)) {
                    // 金额
                    Object o = this.map.get(s);
                    int total = (Integer) o;
                    if (total <= 0)
                        throw new Exception(s + "--为必填项。且必须大于0。");
                }
            }
        }
        return this.map;
    }

    /**
     * 若xmlRootElementName为nill则没有Root节点
     *
     * @param xmlRootElementName 可以为null
     * @return
     */
    public String getXml(String xmlRootElementName) throws Exception {
        Map<String, Object> tmap = getMap();
        StringBuffer sbf = null;
        sbf = new StringBuffer();
        if (null != xmlRootElementName)
            sbf.append("<").append(xmlRootElementName).append(">");

        for (String s : tmap.keySet()) {
            Object tmp = tmap.get(s);
            sbf.append("<").append(s).append(">").append(tmp).append("</").append(s).append(">");
        }
        if (null != xmlRootElementName)
            sbf.append("</").append(xmlRootElementName).append(">");

        try {
            return new String(sbf.toString().getBytes(), "ISO8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }


    /***
     * MD5加码 生成32位md5码
     */
    private String string2MD5(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        char hexDigits[] = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'
        };

        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(str.getBytes("UTF-8"));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf).toUpperCase();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 微信支付签名算法sign
     */
    private String getSign() {
        StringBuilder sb = new StringBuilder();
        // 获取map中的key转为array
        String[] keyArr = this.map.keySet().toArray(new String[0]);
        // 对array排序
        Arrays.sort(keyArr);

        for (String aKeyArr : keyArr) {
            if ("sign".equals(aKeyArr)) {
                continue;
            }
            sb.append(aKeyArr).append("=").append(this.map.get(aKeyArr)).append("&");
        }
        sb.append("key=").append(key);
        return string2MD5(sb.toString());
    }

    /**
     * 生成由[A-Z,0-9]生成的随机字符串
     *
     * @return
     */
    private String getRandomString() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 30; ++i) {
            int number = random.nextInt(2);
            long result;

            switch (number) {
                case 0:
                    result = Math.round(Math.random() * 25 + 65);
                    sb.append(String.valueOf((char) result));
                    break;
                case 1:
                    sb.append(String.valueOf(new Random().nextInt(10)));
                    break;
            }
        }
        return sb.toString();
    }

    /**
     * 获取本机IP地址（内网）
     *
     * @return IP
     */
    private static String getLocalIp() {
        String ip = null;
        Enumeration allNetInterfaces;
        try {
            allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                List<InterfaceAddress> InterfaceAddress = netInterface.getInterfaceAddresses();
                for (InterfaceAddress add : InterfaceAddress) {
                    InetAddress Ip = add.getAddress();
                    if (Ip instanceof Inet4Address) {
                        ip = Ip.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return ip;
    }

    // 设置默认值
    private void setDefaultValue() {
        // 随机串
        if (null == this.map.get("nonce_str"))
            this.setNonce_str(this.getRandomString());

        // 终端IP
        if (null == this.map.get("spbill_create_ip"))
            this.setSpbill_create_ip(getLocalIp());

        // 签名
        if (null == this.map.get("sign"))
            this.setSign(this.getSign());
    }
}