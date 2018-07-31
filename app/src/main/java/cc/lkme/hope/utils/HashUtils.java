package cc.lkme.hope.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;

@Singleton
public class HashUtils {

    @Inject
    public HashUtils() {
    }

    /**
     * 对字符串进行sha1加密
     *
     * @param input 需要被加密的字符串
     * @return sha1后的字符串
     */
    public String sha1(String input) {
        try {
            MessageDigest mDigest = MessageDigest.getInstance("SHA1");
            byte[] result = mDigest.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte aResult : result) {
                sb.append(Integer.toString((aResult & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            Timber.d(e);
        }
        return "";
    }
}
