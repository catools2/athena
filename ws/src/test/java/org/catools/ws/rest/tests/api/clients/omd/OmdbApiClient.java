package org.catools.ws.rest.tests.api.clients.omd;

import com.jayway.jsonpath.JsonPath;
import org.assertj.core.api.Assertions;
import org.catools.ws.enums.CHttpRequestType;
import org.catools.ws.model.CHttpResponse;
import org.catools.ws.rest.CHttpRestClient;
import org.catools.ws.rest.tests.api.config.ConfigUtils;

public abstract class OmdbApiClient<O> extends CHttpRestClient<O> {
  private static final String BASE_URL = ConfigUtils.getApiHost();
  private static final String API_KEY = ConfigUtils.getApiKey();

  public OmdbApiClient(CHttpRequestType requestType) {
    this(requestType, null);
  }

  public OmdbApiClient(CHttpRequestType requestType, String targetPath) {
    super(requestType, BASE_URL, targetPath);
    getRequest().addQueryParameter("apikey", API_KEY);
  }


  /**
   * method to verify error message from response
   *
   * @param response     the rest response to read the message from
   * @param errorMessage the expected message
   */
  protected static void verifyErrorMessage(CHttpResponse response, String errorMessage) {
    String actualMessage = JsonPath.read(response.getContentString(), "Error");
    Assertions.assertThat(actualMessage).isEqualTo(errorMessage);
  }

  /**
   * method to check if response contains a "Response" element with "True"
   * value which indicated if the final response has any value
   *
   * @param response the rest response to read the message from
   */
  protected static boolean hasResponse(CHttpResponse response) {
    return "True".equals(JsonPath.read(response.getContentString(), "Response"));
  }
}
