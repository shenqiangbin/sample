package lisenceDemo;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.KeyPair;

public class RsaDemo {

    public static void main(String[] args) throws UnsupportedEncodingException {
//        KeyPair keyPair = SecureUtil.generateKeyPair("RSA");
//
//        String str = HexUtil.encodeHexStr(keyPair.getPrivate().getEncoded());
//        String publicKey = HexUtil.encodeHexStr(keyPair.getPublic().getEncoded());

//        System.out.println("privateKey:" + str);
//        System.out.println("publicKey:" + publicKey);

        String str = "30820275020100300d06092a864886f70d01010105000482025f3082025b0201000281810093f2881fd6c48d5ecc0d0c79939618d108a93dd623066082e050668eb1457f3f08b5bf350625fc6fd9b1d3ee21d9fb76fe914714810054689fc18459d205be90ad92c9338af6fdc1345b697d4ab610d0ba51b969796c0b264990559d1f4ce8c46e4328f805dc420d8c2202962c1908ccbde16f288596e4a6bacec920874d824f02030100010281804f607ebc66644a5d24299419de9f576d3fb25de4f821c1c2364bacdabe2dc559b278a0bff82e8544158e9d60547539bfdc5161cb9472f2d6b770026c968c5273486db7fbe2ceff50589572829b3adfd29c7076083b1e4a443425c081c7a39d270a693cd586eb7b224546ad50198992b992529af0ac10d942569f208238575931024100c684fddc67d4f96bbb21bb2915ef5984670422c0638fe42ad69560d05cc2e4ce1002bc260f8aaf73410a592b6ef60f6ed8d7ce2c8b5f2f01c0f8828dc4ed538b024100bec8f23145bd050d5a503e29efdcaaa5f290bdc0906ea0aa968287d3d22ea7250a3793cf363fd9f090b7917cbeaafcf47e4732e835e84883ace650593c8854cd02402182152f7cbf301285159e77c2e211a40d975f1e462fe57d6d96ed2e7e59cf3110f6c5374f6c434d1aef5a39c092fbebe3e21944df89836fb258c00099facd1d024029c33f10fe1d146009e70ad48f4714abe2df404a48d38ea408f265dd3e632bff75af18d2012415070e06c0f8379ca266bac5c72501b7e937b9bf68d33fe6eff9024039f8c96be1fe5121ebb01bdf0bbdbddd8dc3d1782057ed5b082a1bf02cf03c3ffe3716a03ad78b542b3ed814866e05952bb2632e6d8bfb8e3118888082fdc352";
        String publicKey = "30819f300d06092a864886f70d010101050003818d003081890281810093f2881fd6c48d5ecc0d0c79939618d108a93dd623066082e050668eb1457f3f08b5bf350625fc6fd9b1d3ee21d9fb76fe914714810054689fc18459d205be90ad92c9338af6fdc1345b697d4ab610d0ba51b969796c0b264990559d1f4ce8c46e4328f805dc420d8c2202962c1908ccbde16f288596e4a6bacec920874d824f0203010001";
        String decryptStr = "jwrfOCnAJhhhlX8TmXDLO/LHT43h5eOZ3d+tEY7rTbxlIQ8aS0LivzrLTMlsz4U1R8gpSnnQvpWTXtl1uMSOO3UbzdsNmE18fQaFphC53f7vLlvOAYxrd4sY6tFVw229Dn3loCHdVV3tPH/YWNVV4JHaNy+rskPhF9cNoEP4IA=";

//        RSA rsa = new RSA(str, null);
//        byte[] encryptByte = rsa.encrypt("123", KeyType.PrivateKey);
//        String decryptStr = Base64.encode(encryptByte);
//
//        System.out.println("decryptStr:" + decryptStr);

        RSA rsa2 = new RSA(null, publicKey);
        byte[] encryptByte2 = Base64.decode(decryptStr);
        byte[] val = rsa2.decrypt(encryptByte2, KeyType.PublicKey);
        String valStr = StrUtil.str(val, CharsetUtil.CHARSET_UTF_8);

        System.out.println(valStr);

        System.out.println("over");
    }

}
