/*
通用JS庫
 */
/*
AJAX返回狀態
與服務器R對象中的定義相同
 */
/*200 OK-[GET]:服務器成功返回用戶請求的數據*/
let OK=200;
/*201 CREATED-[POST/PUT/PATCH]:用戶新建或修改數據成功*/
let CREATED=201;
/*202 ACCEPTED-[*]:表示一個請求已經進入後台排隊(異步任務)*/
let ACCEPTED=202;
/*204 NO CONTENT -[DELETE]:刪除數據成功*/
let NO_CONTENT=204;
/*400 INVALID REQUEST-[POST/PUT/PATCH]:用戶發出的請求有錯誤,服務器沒有進行新建或修改數據的操作*/
let INVALID_REQUEST = 400;
/* 401 Unauthorized - [*]：
 表示用户没有权限（令牌、用户名、密码错误）。 */
let final_int_UNAUTHORIZED = 401;
/* 403 Forbidden - [*]
 表示用户得到授权（与401错误相对），但是访问是被禁止的。*/
let FORBIDDEN = 403;
/* 404 NOT FOUND - [*]：
 用户发出的请求针对的是不存在的记录，服务器没有进行操作。 */
let NOT_FOUND = 404;

/* 410 Gone -[GET]：
 用户请求的资源被永久删除，且不会再得到的。*/
let GONE = 410;

/* 422 Unprocesable entity - [POST/PUT/PATCH]
 当创建一个对象时，发生一个验证错误。 */
let UNPROCESABLE_ENTITY = 422;

/* 500 INTERNAL SERVER ERROR - [*]：
 服务器发生错误，用户将无法判断发出的请求是否成功。 */
let INTERNAL_SERVER_ERROR = 500;


function addDuration (item){
    if(!item||!item.createtime){
        return;
    }
    //創建question時間毫秒數
    let createTime=new Date(item.createtime).getTime();
    //當前時間毫秒數
    let now =new Date().getTime();
    let duration=now-createTime;
    if(duration<1000*60){
        item.duration="剛剛";
    }else if(duration<1000*60*60){
        item.duration=(duration/1000/60).toFixed(0)+"分鐘前";
    }else if(duration<1000*60*60*24){
        item.duration=(duration/1000/60/60).toFixed(0)+"小時前";
    }else {
        item.duration=(duration/1000/60/60/24).toFixed(0)+"天前";
    }

}