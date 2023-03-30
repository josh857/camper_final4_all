let hot =new Vue({
    el:'#hotp',
    data:{
        product:[],
    },
    methods:{
    getHot:function (){
     $.ajax({
         url:'/v1/product/hot',
         method:'GET',
         success:function (r){
             if (r.code==OK){
                hot.product=r.data;
                console.log("熱門商品加載成功");
             }else{
                 console.log(r.message);
             }
         }
     });
    },
        getdetail:function (){




        }
    },
    created:function(){
        console.log("執行方法getHot")
    this.getHot();
    }
});



