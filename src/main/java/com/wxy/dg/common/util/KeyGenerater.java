//package com.wxy.dg.common.util;
//
//
//import com.sun.org.apache.xml.internal.security.utils.Base64;
//
//import java.security.*;
//import java.security.spec.PKCS8EncodedKeySpec;
//
//public class KeyGenerater {
//    private byte[] priKey;
//    private byte[] pubKey;
//
//    public void generater() {
//        try {
//            java.security.KeyPairGenerator keygen = java.security.KeyPairGenerator
//                    .getInstance("RSA");
//            SecureRandom secrand = new SecureRandom();
//            secrand.setSeed("syj".getBytes()); // 初始化随机产生器
//            keygen.initialize(1024, secrand);
//            KeyPair keys = keygen.genKeyPair();
//
//            PublicKey pubkey = keys.getPublic();
//            PrivateKey prikey = keys.getPrivate();
//            Base64.encode("")
//            pubKey = Base64.encodeToByte(pubkey.getEncoded());
//            priKey = Base64.encodeToByte(prikey.getEncoded());
//
//            System.out.println("pubKey = " + new String(pubKey));
//            System.out.println("priKey = " + new String(priKey));
//        } catch (java.lang.Exception e) {
//            System.out.println("生成密钥对失败");
//            e.printStackTrace();
//        }
//    }
//
//    public byte[] getPriKey() {
//        return priKey;
//    }
//
//    public byte[] getPubKey() {
//        return pubKey;
//    }
//}
//
//
//public class Signaturer {
//    /**
//     * Description:数字签名
//     *
//     * @param priKeyText
//     * @param plainText
//     * @return
//     * @author 孙钰佳
//     * @since：2007-12-27 上午10:51:48
//     */
//    public static byte[] sign(byte[] priKeyText, String plainText) {
//        try {
//            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64
//                    .decode(priKeyText));
//            KeyFactory keyf = KeyFactory.getInstance("RSA");
//            PrivateKey prikey = keyf.generatePrivate(priPKCS8);
//
//            // 用私钥对信息生成数字签名
//            java.security.Signature signet = java.security.Signature
//                    .getInstance("MD5withRSA");
//            signet.initSign(prikey);
//            signet.update(plainText.getBytes());
//            byte[] signed = Base64.encodeToByte(signet.sign());
//            return signed;
//        } catch (java.lang.Exception e) {
//            System.out.println("签名失败");
//            e.printStackTrace();
//        }
//        return null;
//    }
//}
//
//public class SignProvider {
//    private SignProvider() {
//
//    }
//
//    /**
//     * Description:校验数字签名,此方法不会抛出任务异常,成功返回true,失败返回false,要求全部参数不能为空
//     *
//     * @param pubKeyText 公钥,base64编码
//     * @param plainText  明文
//     * @param signTest   数字签名的密文,base64编码
//     * @return 校验成功返回true 失败返回false
//     * @author 孙钰佳
//     * @since：2007-12-27 上午09:33:55
//     */
//    public static boolean verify(byte[] pubKeyText, String plainText,
//                                 byte[] signText) {
//        try {
//            // 解密由base64编码的公钥,并构造X509EncodedKeySpec对象
//            java.security.spec.X509EncodedKeySpec bobPubKeySpec = new java.security.spec.X509EncodedKeySpec(
//                    Base64.decode(pubKeyText));
//            // RSA对称加密算法
//            java.security.KeyFactory keyFactory = java.security.KeyFactory
//                    .getInstance("RSA");
//            // 取公钥匙对象
//            java.security.PublicKey pubKey = keyFactory
//                    .generatePublic(bobPubKeySpec);
//            // 解密由base64编码的数字签名
//            byte[] signed = Base64.decode(signText);
//            java.security.Signature signatureChecker = java.security.Signature
//                    .getInstance("MD5withRSA");
//            signatureChecker.initVerify(pubKey);
//            signatureChecker.update(plainText.getBytes());
//            // 验证签名是否正常
//            if (signatureChecker.verify(signed))
//                return true;
//            else
//                return false;
//        } catch (Throwable e) {
//            System.out.println("校验签名失败");
//            e.printStackTrace();
//            return false;
//        }
//    }
//}