package com.leverx.proxypets.responsehandler;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

@Component
class CustomResponseHandler implements ResponseHandler<String>{

    public String handleResponse(final HttpResponse response) throws IOException{

        //Get the status of the response
        int status = response.getStatusLine().getStatusCode();
        if (status >= 200 && status < 300) {
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return "";
            }
            else {
                return EntityUtils.toString(entity);
            }

        }
        else {
            return "";
        }
    }
}
