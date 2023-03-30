package cn.tedu.camper.portal.service;

import cn.tedu.camper.portal.Vo.R;


/*
業務層異常
包含異常號Code
 */
public class ServiceException extends RuntimeException{
    private int code= R.INTERNAL_SERVER_ERROR;

    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ServiceException(int code) {
        this.code = code;
    }

    public ServiceException(String message, int code) {
        super(message);
        this.code = code;
    }

    public ServiceException(String message, Throwable cause, int code) {
        super(message, cause);
        this.code = code;
    }

    public ServiceException(Throwable cause, int code) {
        super(cause);
        this.code = code;
    }

    public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int code) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }

    /*
     get獲取一個對象,但沒有對象的紀錄
     */
    public static ServiceException notFound(String message){
        return new ServiceException(message, R.NOT_FOUND);
    }

    /*
    get查詢對象永久被刪除
     */
    public static ServiceException gone(String message){
        return new ServiceException(message,R.GONE);
    }

    /*
    在收到的表單數據無法進行處理時,拋出"無法處理實體"異常
     */
    public static ServiceException unprocessableEntry(String message){
        return new ServiceException(message,R.UNPROCESABLE_ENTITY);
    }

    public int getCode() {
        return code;
    }

    public static ServiceException busy(){
        return new ServiceException("伺服器繁忙中",R.INTERNAL_SERVER_ERROR);
    }
}
