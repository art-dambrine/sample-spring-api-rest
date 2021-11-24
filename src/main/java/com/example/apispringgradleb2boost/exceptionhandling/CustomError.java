package com.example.apispringgradleb2boost.exceptionhandling;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CustomError extends RuntimeException {

    private final int code;
    private final String message;

    public CustomError(int code, final String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * Overriding getMessage from RuntimeException to avoid printing stackTrace and suppressedExceptions as for ex :
     * {
     *     "code": 404,
     *     "message": "No handler found for GET /partnersdf",
     *     "stackTrace": [],
     *     "suppressedExceptions": []
     * }
     * @return overrideMessage
     * */
    @Override
    public String getMessage() {
        Gson gson = new Gson();
        JsonObject body = gson.fromJson(message, JsonObject.class);
        JsonElement extractedCode = body.get("code");
        JsonElement extractedMessage = body.get("message");
        return "{\"code\":" + extractedCode + ", \"message\": " + extractedMessage + "}";
    }
}
