let ShopList = new Vue({
    el:'#shopList',
    data:{
        List:[]
    },
    methods:{
     getshopList:function (){
         $.ajax({
             url:'/v1/orders/getlist',
             method:'GET',
             success:function (r){
                 if(r.code==OK){
                     ShopList.List = r.data;
                     console.log("獲取成功");
                     console.log(r.data);
                 }else{
                     console.log("獲取失敗");
                 }
             }
         })
     },
    },
    created:function(){
        this.getshopList();
    }
});
let Cart = new Vue ({
    el:'#top' ,
    data:{
        CarVo:{}
    },
    methods:{
        getrows:function(){
            $.ajax({
                url:'/v1/car/getrows',
                method:'GET',
                success:function (r){
                    if (r.code==OK){
                        Cart.CarVo= r.data;
                        console.log("獲取數量成功!")
                    }else{
                        console.log(r.message);
                    }
                }
            })
        },
    },
    created:function (){
        this.getrows();
        console.log("執行方法")
    }
});