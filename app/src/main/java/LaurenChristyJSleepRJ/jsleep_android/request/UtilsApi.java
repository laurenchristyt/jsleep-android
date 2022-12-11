package LaurenChristyJSleepRJ.jsleep_android.request;

/**
 * The {@code UtilsApi} class provides utility methods for working with the JSleep Android app's API.
 *
 * @author Lauren Christy Tanudjaja
 * @version 1.0
 */
public class UtilsApi {
    /**
     * The base URL for the JSleep Android app's API.
     */
    public static final String BASE_URL_API = "http://10.0.2.2:8080";
    /**
     * Returns a new instance of the {@link BaseApiService}.
     * @return a new instance of the {@link BaseApiService}
     */
    public static BaseApiService getApiService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }


}
