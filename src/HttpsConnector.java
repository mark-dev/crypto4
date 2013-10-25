import javax.net.ssl.*;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

/**
 * Created with IntelliJ IDEA.
 * User: markdev
 * Date: 10/21/13
 * Time: 5:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class HttpsConnector {
    private  String IP = "localhost";
    private  String PORT = "8080";

    static {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }
        };

        // Install the all-trusting trust manager
        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };
            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }

    public HttpsConnector(String IP, String PORT) {
        this.IP = IP;
        this.PORT = PORT;
    }

    public String calc(Operations operationId, String arg1, String arg2) throws Exception {
        String operationStr = "";
        switch (operationId) {
            case DIV:
                operationStr = "div";
                break;
            case PLUS:
                operationStr = "plus";
                break;
            case MPL:
                operationStr = "mpl";
                break;
            case MINUS:
                operationStr = "minus";

        }
        URL url = new URL(String.format("https://%s:%s/?operation=%s&arg1=%s&arg2=%s",
                IP, PORT, operationStr, arg1, arg2));
        URLConnection con = url.openConnection();
        Reader reader = new InputStreamReader(con.getInputStream());
        StringBuilder sb = new StringBuilder();
        while (true) {
            int ch = reader.read();
            if (ch == -1) {
                break;
            }
            sb.append((char) ch);
        }
        return sb.toString();
    }



}
