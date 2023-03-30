let App = new Vue({
    el:"#App",
    data:{
        products:[],
        pageInfo:{}
    },
    methods:{
        getproducttype_3:function (pagenum){
            if(!pagenum){
                pagenum=1;
            }
             $.ajax({
                url:'/v1/product/type_3',
                 method:'GET',
                 data:{
                    pagenum:pagenum
                 },
                 success:function (r){
                  console.log(r.code);
                    if(r.code==OK){
                   console.log(r.data);
                        App.products=r.data.list;
                        App.pageInfo=r.data
                        console.log(this.products);
                      console.log("加載成功");
                  }else{
                        console.log("加載失敗")
                    }
                 }
             });
        },
        gotocar: function(id){
            $.ajax({
                url: '/v1/car/incar/'+id,
                method:'POST',
                success:function (r){
                    if(r.code==CREATED){
                        window.alert(r.message);
                        Cart.getrows();
                    }else{
                        window.alert(r.message);
                    }
                }
            });
        }
    },
   created:function (){
        console.log("執行getproduct")
        this.getproducttype_3(1);
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


