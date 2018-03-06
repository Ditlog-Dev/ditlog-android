package id.ac.itb.ditlog.monitorandperformance;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

//@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginResponse {
//    @JsonProperty("success")
    private Boolean success;
//    @JsonProperty("statuscode")
    private Integer statusCode;
//    @JsonProperty("message")
    private String message;
//    @JsonProperty("payload")
    private Object payload;

    public Boolean getSuccess(){
        return success;
    }

    public void setSuccess(Boolean s){
        success = s;
    }

    public Integer getStatusCode(){
        return statusCode;
    }

    public void setPayload(Object p){
        payload = p;
    }

    public Object getPayload(){
        return payload;
    }
}
