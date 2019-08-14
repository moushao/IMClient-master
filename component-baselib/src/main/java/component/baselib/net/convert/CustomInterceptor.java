package component.baselib.net.convert;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class CustomInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request()
                .newBuilder()
                .header("User-agent", "Mozilla/4.0")
                .build();

        return chain.proceed(request);

        /*String content = AssetsToString.xmltoString(path);

        ResponseBody body = ResponseBody
                .create(MediaType.parse("application/x-www-form-urlencoded"), content);

        Response response = new Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .body(body)
                .code(1)
                .message("成功了")
                .build();
        return response;
*/
    }

}
