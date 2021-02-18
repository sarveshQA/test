package test;

import client.ClientMethods;
import driver.BaseDriver;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import util.TestUtil;

import java.io.IOException;

public class GetAPI extends BaseDriver {

    BaseDriver testBase;
    String apiURL;
    String serviceURLCase_1;
    String url_1;
    ClientMethods restClient;
    CloseableHttpResponse closeableHttpResponse;


    @BeforeMethod
    public void setUp() throws ClientProtocolException, IOException {
        testBase = new BaseDriver();
        apiURL = prop.getProperty("URL");
        serviceURLCase_1 = prop.getProperty("serviceURL_1");
        url_1 = apiURL + serviceURLCase_1;
        HashMap<String, String> headerMap= new HashMap<>();
        headerMap.put(“X-Auth-Token”, prop.getProperty(“X_Auth_Token”));

    }



    @Test(priority = 1)
    public void getAPITestURL_1() throws ClientProtocolException, IOException {
        restClient = new ClientMethods();
        closeableHttpResponse = restClient.get(url_1, headerMap);

        //a. Status Code:
        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        System.out.println("Status Code--->" + statusCode);

        Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status code is not 200");

        //b. Json String:
        String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");

        JSONObject responseJson = new JSONObject(responseString);
        System.out.println("Response JSON from API---> " + responseJson);

        //status:
        String status = TestUtil.getValueByJPath(responseJson, "/status");
        System.out.println("value of Status -->" + status);
        Assert.assertEquals(status, "SUCCESS");




    }

    




}
