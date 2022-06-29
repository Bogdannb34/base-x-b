package com.practice.basexbackend.dto.auth.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class HttpResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy hh:ss:mm", timezone = "Europe/Bucharest")
    private Date timeStamp;
    private int httpStatusCode;
    private HttpStatus httpStatus;
    private String response;
    private String message;

    public HttpResponse(int httpStatusCode, HttpStatus httpStatus, String response, String message) {
        this.httpStatusCode = httpStatusCode;
        this.httpStatus = httpStatus;
        this.response = response;
        this.message = message;
    }
}
