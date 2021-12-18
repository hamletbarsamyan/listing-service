package am.jsl.listings.api.rest;

import java.io.Serializable;

/**
 * The Response class is used by rest controllers for generating response.
 *
 * @param <T> the data type
 */
public class Response<T> implements Serializable {

    /** True if response status is error */
    private boolean error;

    /** The response code */
    private int code;

    /** The response body */
    private T data;

    /**
     * Instantiates a new Response.
     */
    public Response() {
       super();
       this.error = false;
    }

    /**
     * Instantiates a new Response.
     *
     * @param error the error
     * @param code  the code
     * @param data  the data
     */
    public Response(boolean error, int code, T data) {
        this.error = error;
        this.code = code;
        this.data = data;
    }

    /**
     * Instantiates a new Response.
     * @param error the error
     * @param code the code
     */
    public Response(boolean error, int code) {
        this.error = error;
        this.code = code;
    }

    /**
     * Instantiates a new Response.
     *
     * @param error the error
     * @param data  the data
     */
    public Response(boolean error, T data) {
        this.error = error;
        this.data = data;
    }

    /**
     * Instantiates a new Response.
     *
     * @param data the data
     */
    public Response(T data) {
        this.data = data;
    }

    /**
     * Error response.
     *
     * @param message the message
     * @return the response
     */
    public static Response<String> error(String message) {
        return new Response<>(true, message);
    }

    /**
     * Creates a Error response with error code.
     *
     * @param errorCode the error code
     * @return the response
     */
    public static Response<?> errorWithCode(int errorCode) {
        return new Response<>(true, errorCode);
    }

    /**
     * Ok response.
     *
     * @param message the message
     * @return the response
     */
    public static Response<String> ok(String message) {
        return new Response<>(message);
    }

    /**
     * Ok response.
     *
     * @return the response
     */
    public static Response<String> ok() {
        return new Response<>();
    }

    public static <T> Response<T> ok(T data) {
        return new Response<>(data);
    }

    /**
     * Is error boolean.
     *
     * @return the boolean
     */
    public boolean isError() {
        return error;
    }

    /**
     * Sets error.
     *
     * @param error the error
     */
    public void setError(boolean error) {
        this.error = error;
    }

    /**
     * Gets code.
     *
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * Sets code.
     *
     * @param code the code
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * Gets data.
     *
     * @return the data
     */
    public T getData() {
        return data;
    }

    /**
     * Sets data.
     *
     * @param data the data
     */
    public void setData(T data) {
        this.data = data;
    }
}
