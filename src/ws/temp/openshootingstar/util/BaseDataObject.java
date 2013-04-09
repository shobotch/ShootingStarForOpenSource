package ws.temp.openshootingstar.util;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BaseDataObject implements Serializable {
    public String consumerKey;
    public String consumerKeySecret;
    public String accessToken;
    public String accessTokenSecret;

    public String screen_name;
    public Long   user_id;
    public int    account_type;     // 0‚Åtwitter, 1‚ÅADN
}
