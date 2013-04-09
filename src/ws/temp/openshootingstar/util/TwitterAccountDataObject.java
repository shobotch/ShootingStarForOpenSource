package ws.temp.openshootingstar.util;

import ws.temp.openshootingstar.util.AccountDbHelper.AccountTable;


@SuppressWarnings("serial")
public class TwitterAccountDataObject extends BaseDataObject{
    public String AuthorizationURL;

    public static TwitterAccountDataObject toDataObject(
            AccountTable accountTable){
        TwitterAccountDataObject dataObject = new TwitterAccountDataObject();
        dataObject.consumerKey = accountTable.a_consumer_key;
        dataObject.consumerKeySecret = accountTable.a_consumer_secret;
        dataObject.accessToken = accountTable.a_access_token;
        dataObject.accessTokenSecret = accountTable.a_access_token_secret;
        dataObject.screen_name = accountTable.a_screen_name;
        dataObject.user_id = accountTable.a_id;
        dataObject.account_type = accountTable.a_type;
        return dataObject;
    }
}