package com.qu.crowd.util;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.comm.ResponseMessage;
import com.aliyun.oss.model.PutObjectResult;
import com.qu.crowd.constant.CrowdConstant;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author 瞿琮
 * @create 2020-06-30 12:57
 * Crowd的工具类
 */
public class CrowdUtil {

    //OOS文件上传
    /**
     * 专门负责上传文件到OSS 服务器的工具方法
     * @param endpoint OSS 参数
     * @param accessKeyId OSS 参数
     * @param accessKeySecret OSS 参数
     * @param inputStream 要上传的文件的输入流
     * @param bucketName OSS 参数
     * @param bucketDomain OSS 参数
     * @param originalName 要上传的文件的原始文件名
     * @return 包含上传结果以及上传的文件在OSS 上的访问路径
     */
    public static ResultEntity<String> uploadFileToOss(
            String endpoint,
            String accessKeyId,
            String accessKeySecret,InputStream inputStream,
            String bucketName,
            String bucketDomain,
            String originalName)
    {
            // 创建OSSClient 实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 生成上传文件的目录,为了防止所有文件上传进一个文件夹，所以通过时间每天创建一个文件夹存放
            String folderName = new SimpleDateFormat("yyyyMMdd").format(new Date());

            // 生成上传文件在OSS 服务器上保存时的文件名
            // 原始文件名：beautfulgirl.jpg
            // 生成文件名：wer234234efwer235346457dfswet346235.jpg
            // 使用UUID 生成文件主体名称
            //使用UUID生成文件名，取出"-"和""
            String fileMainName = UUID.randomUUID().toString().replace("-", "");

            // 从原始文件名中获取文件扩展名
            String extensionName = originalName.substring(originalName.lastIndexOf("."));

            // 使用目录、文件主体名称、文件扩展名称拼接得到对象名称
            String objectName = folderName + "/" + fileMainName + extensionName;
            try {
                 // 调用OSS 客户端对象的方法上传文件并获取响应结果数据
                PutObjectResult putObjectResult = ossClient.putObject(bucketName, objectName,inputStream);

                // 从响应结果中获取具体响应消息
                ResponseMessage responseMessage = putObjectResult.getResponse();

                // 根据响应状态码判断请求是否成功
                if(responseMessage == null) {
                    // 拼接访问刚刚上传的文件的路径
                    String ossFileAccessPath = bucketDomain + "/" + objectName;
                    // 当前方法返回成功
                    return ResultEntity.successWithData(ossFileAccessPath);
                } else {
                    // 获取响应状态码
                    int statusCode = responseMessage.getStatusCode();
                    // 如果请求没有成功，获取错误消息
                    String errorMessage = responseMessage.getErrorResponseAsString();
                    // 当前方法返回失败
                    return ResultEntity.failed(" 当前响应状态码="+statusCode+" 错误消息 ="+errorMessage);
                }
            } catch (Exception e) {
                e.printStackTrace();
                // 当前方法返回失败
                return ResultEntity.failed(e.getMessage());
            } finally {
                if(ossClient != null) {
                // 关闭OSSClient。
                    ossClient.shutdown();
                }
            }
    }

  /*  public static void main(String[] args) throws FileNotFoundException {
        InputStream inputStream = new FileInputStream("src/me.jpg");
        ResultEntity<String> resultEntity = CrowdUtil.uploadFileToOss(
                "http://oss-cn-zhangjiakou.aliyuncs.com",
                "LTAI4GL6z9yg5di5EvAo68Jn",
                "LnilrxsqIqiPcjRrNIflR21Zrac8KP",
                inputStream,
                "crowdydgk",
                "http://crowdydgk.oss-cn-zhangjiakou.aliyuncs.com",
                "src/me.jpg"
        );
        System.out.println(resultEntity.getQueryData());
    }*/
    //获取验证码
    public static ResultEntity<String> sendCodeByShortMessage(
            // 【1】请求地址 支持http 和 https 及 WEBSOCKET
            String host,
            //【2】后缀
            String path,
            // 【3】开通服务后 买家中心-查看AppCode
            String appcode,
            // 【4】签名,编号
            String sign,
            // 【4】模板号
            String skin,
            // 【4】发送手机号
            String phone
    ){
        // 【1】请求地址 支持http 和 https 及 WEBSOCKET
        //String host = "https://feginesms.market.alicloudapi.com";
        //【2】后缀
        //String path = "/codeNotice";

        /*String appcode = "ed5ee66b9a7648ca988468641c2e98c3";
        String sign = "1";
        String skin = "20";
        String param = "123456";
        String phone = "18062820923";*/

        //生成验证码
        String param = SecurityCode.generateCode();

        // 【5】拼接请求链接
        String urlSend = host + path + "?sign=" + sign + "&skin=" + skin+ "&param=" + param+ "&phone=" + phone;
        try {
            URL url = new URL(urlSend);
            HttpURLConnection httpURLCon = (HttpURLConnection) url.openConnection();
            // 格式Authorization:APPCODE
            httpURLCon.setRequestProperty("Authorization", "APPCODE " + appcode);
            // (中间是英文空格)
            int httpCode = httpURLCon.getResponseCode();
            if (httpCode == 200) {
                System.out.println(httpCode);
                String json = read(httpURLCon.getInputStream());
                System.out.println("获取返回的json:");
                System.out.print(json);
                JSONObject jsonObject = JSONObject.parseObject(json);
                System.out.println(jsonObject.get("Code"));
                if ("OK".equals(jsonObject.get("Code").toString().trim())){
                    return ResultEntity.successWithData(param);
                }else {
                    return ResultEntity.failed(jsonObject.getString("Code")+": "+jsonObject.getString("Message"));
                }
            } else {
                Map<String, List<String>> map = httpURLCon.getHeaderFields();
                String error = map.get("X-Ca-Error-Message").get(0);
                if (httpCode == 400 && error.equals("Invalid AppCode `not exists`")) {
                    System.out.println("AppCode错误 ");
                } else if (httpCode == 400 && error.equals("Invalid Url")) {
                    System.out.println("请求的 Method、Path 或者环境错误");
                } else if (httpCode == 400 && error.equals("Invalid Param Location")) {
                    System.out.println("参数错误");
                } else if (httpCode == 403 && error.equals("Unauthorized")) {
                    System.out.println("服务未被授权（或URL和Path不正确）");
                } else if (httpCode == 403 && error.equals("Quota Exhausted")) {
                    System.out.println("套餐包次数用完 ");
                } else {
                    System.out.println("参数名错误 或 其他错误");
                    System.out.println(error);
                }
                return ResultEntity.failed(String.valueOf(httpCode)+": "+error);
            }

        } catch (MalformedURLException e) {
            System.out.println("URL格式错误");
            return ResultEntity.failed("URL格式错误");
        } catch (UnknownHostException e) {
            System.out.println("URL地址错误");
            return ResultEntity.failed("URL地址错误");
        } catch (Exception e) {
            // 打开注释查看详细报错异常信息
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }

    }
    /*
     * 读取返回结果
     */
    private static String read(InputStream is) throws IOException {
        StringBuffer sb = new StringBuffer();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = null;
        while ((line = br.readLine()) != null) {
            line = new String(line.getBytes(), "utf-8");
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }



    //判断请求是普通请求，还是ajax请求，用于后面异常处理分别对不同请求作出响应
    public static boolean judgeRequestType(HttpServletRequest request){

        //取到请求头XMLHttpRequest
        String accept = request.getHeader("Accept");
        String ajaxHeader = request.getHeader("X-Requested-With");

        /*通过请求头判断是那种类型请求，是ajax请求返回true，否则返回flase
        accept.contains("application/json")判断accept中是否包含application/json
        "XMLHttpRequest".equalsIgnoreCase(ajaxHeader)判断ajaxHeader是否与XMLHttpRequest相同
        */
        return (
                (accept!=null && !"".equals(accept) && accept.contains("application/json"))
                ||
                (ajaxHeader!=null && !"".equals(ajaxHeader) && "XMLHttpRequest".equalsIgnoreCase(ajaxHeader))
                );
    }


    //密码加密方法，使用md5进行加密
    public static String md5(String source){
        //1、判断传入的字符串是否有效
        if (source==null||"".equals(source)){
            throw new RuntimeException(CrowdConstant.MESSAGE_INVALID_STRING);
        }

        //这是一个工具方法我们一定会调用，所以try
        try {
            //2、指定加密方式，获取对象
            String algorithm="md5";
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);

            //3、获取明文字节
            byte[] input = source.getBytes();

            //4、通过明文字节进行加密
            byte[] output = messageDigest.digest(input);

            //5、创建BigInteger对象
            Integer signum = 1;//表示是正数
            BigInteger bigInteger = new BigInteger(signum,output);

            //6、按照16 进制将bigInteger 的值转换为字符串
            Integer radix=16;
            String encoded = bigInteger.toString(radix).toUpperCase();

            //7、返回密文
            return encoded;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;

    }
}
