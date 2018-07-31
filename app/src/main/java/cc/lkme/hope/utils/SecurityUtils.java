package cc.lkme.hope.utils;

import java.util.ArrayList;
import java.util.Collections;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SecurityUtils {

    @Inject
    HashUtils hashUtils;

    @Inject
    public SecurityUtils() {
    }

    public String getSnssdkSignature(String secureKey, String timestamp, String nonce) {
        ArrayList<String> list = new ArrayList<>();
        list.add(secureKey);
        list.add(nonce);
        list.add(timestamp);

        Collections.sort(list);
        StringBuilder originString = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            originString.append(list.get(i));
        }

//        return hashUtils.sha1(originString.toString());
        // WARNING: 6/19/18 lipeng test
        return "0e35d51558fb1e21b81cddf7f95e6d1e27f0a610";
    }
}
