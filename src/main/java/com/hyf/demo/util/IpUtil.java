package com.hyf.demo.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import com.hyf.demo.exception.BizException;

import javax.servlet.http.HttpServletRequest;

public class IpUtil {

    /**
     * 获取ip
     * @param request
     * @return
     */
    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (StrUtil.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0,index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (StrUtil.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }

    /**
     * 判断IP是否在指定范围
     * @param ipStart
     * @param ipEnd
     * @param ip
     * @return
     */
    public static boolean ipIsValid(String ipStart,String ipEnd, String ip) {
        if (StrUtil.isEmpty(ipStart)) {
            throw new BizException(HttpStatus.HTTP_INTERNAL_ERROR,"起始IP不能为空！");
        }
        if (StrUtil.isEmpty(ipEnd)) {
            throw new BizException(HttpStatus.HTTP_INTERNAL_ERROR,"结束IP不能为空！");
        }
        if (StrUtil.isEmpty(ip)) {
            throw new BizException(HttpStatus.HTTP_INTERNAL_ERROR,"IP不能为空！");
        }
        ipStart = ipStart.trim();
        ipEnd = ipEnd.trim();
        ip = ip.trim();
        final String REGX_IP = "((25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d)\\.){3}(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d)";
        final String REGX_IPB = REGX_IP + "\\-" + REGX_IP;
        if (!ipStart.matches(REGX_IP) || !ip.matches(REGX_IP) || !ipEnd.matches(REGX_IP)) {
            throw new BizException(HttpStatus.HTTP_INTERNAL_ERROR,"ip输入有误");
        }
        String[] sips = ipStart.split("\\.");
        String[] sipe = ipEnd.split("\\.");
        String[] sipt = ip.split("\\.");
        long ips = 0L, ipe = 0L, ipt = 0L;
        for (int i = 0; i < 4; ++i) {
            ips = ips << 8 | Integer.parseInt(sips[i]);
            ipe = ipe << 8 | Integer.parseInt(sipe[i]);
            ipt = ipt << 8 | Integer.parseInt(sipt[i]);
        }
        if (ips > ipe) {
            long t = ips;
            ips = ipe;
            ipe = t;
        }
        return ips <= ipt && ipt <= ipe;
    }

    public static void main(String[] args) {
        boolean b = ipIsValid("192.168.", "192.168.0.100", "192.168.0.10");
        System.out.println("b = " + b);
    }


    /**
     * 检查ip是否合法
     * @param str
     * @return
     */
    public static boolean isIpLegal(String str){

        //检查ip是否为空
        if(str == null){
            return false;
        }

        //检查ip长度，最短为：x.x.x.x（7位）  最长为:xxx.xxx.xxx.xxx（15位）
        if(str.length() <7 || str.length() >15){
            System.out.print(str +" 长度不正确");
            return false;

        }

        //对输入字符串的首末字符判断，如果是 "." 则是非法IP
        // charAt() 方法用于返回指定索引处的字符。索引范围为从 0 到 length() - 1

        if(str.charAt(0) == '.' || str.charAt(str.length() - 1) == '.'){
            System.out.println(str + "首尾字符不正确");
            return false;
        }


        //按 "." 分割字符串，并判断分割出来的个数，如果不是4个，则是非法IP

        String[] arr = str.split("\\.");

        if(arr.length !=4){
            System.out.println("字符串个数不对");
            return false;
        }

        //对分割出来的每个字符串进行单独判断

        for(int i =0;i<arr.length;i++){
            //如果每个字符串不是一位字符，且以 '0' 开头，则是非法的ip,如：01.123.23.124 ,
            if(arr[i].length() > 1 && arr[i].charAt(0) == '0'){
                System.out.println("非法ip");
                return false;

            }
            // 对每个字符串的每个字符进行逐一判断，如果不是数字0-9，则是非法的ip 如： 64.12.22.-1  针对 6、4、1、2、2、2、-1 逐个数字判断
            for(int j =0; j < arr[i].length();j++){

                if(arr[i].charAt(j)<'0' || arr[i].charAt(j) > '9'){
                    System.out.println("字符有不符合规定的");
                    return false;
                }
            }
        }
        //对拆分的每一个字符串进行转换成数字，并判断是否在 0 ~ 255

        for(int i = 0; i < arr.length; i++){
            int temp = Integer.parseInt(arr[i]);
            if(i == 0){
                if (temp < 1 || temp > 255){
                    return false;
                }
            }
            else{
                if(temp < 0 || temp > 255){
                    return false;
                }
            }
        }
        return true;
    }

}
