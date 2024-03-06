package com.account.account.models.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class Status {

    @JsonProperty("Code")
    public String Code;
    @JsonProperty("Description")
    public String Description;
    @JsonProperty("Message")
    public String Message;
    @JsonProperty("Timestamp")
    public long Timestamp;
    @JsonProperty("Errors")
    public List<String> Errors;

    public Status(String code, String description, String message, long timestamp, String error) {
        this.Code = code;
        this.Description = description;
        this.Message = message;
        this.Timestamp = timestamp;
        this.Errors = new ArrayList();
        this.Errors.add(error);
    }

//    public Status(String code, String description, String message, long timestamp, List<String> errors) {
//        this.Code = code;
//        this.Description = description;
//        this.Message = message;
//        this.Timestamp = timestamp;
//        this.Errors = errors;
//    }

    public Status(String code, String description, String Message, long Timestamp, List<String> Errors) {
        this.Code = code;
        this.Description = description;
        this.Message = Message;
        this.Timestamp = Timestamp;
        this.Errors = Errors;
    }

}
