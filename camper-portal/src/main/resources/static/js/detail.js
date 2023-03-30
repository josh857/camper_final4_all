
let Detail = new Vue({
    el:"#detail",
    data:{
        product:{},
        imag:[]
    },
    methods:{
        getproduct:function (){
            let id= location.search;
            id=id.substring(1);
            console.log(id);
            $.ajax({
               url:'/v1/product/detail/'+id,
                methdo:'GET',
                success:function (r){
                   if(r.code==OK){
                    Detail.product=r.data;
                    Detail.imag=r.data.image;
                       console.log(r)
                       console.log("獲得對象")
                   }else{
                       console.log(r.message);
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
                        Cart.getrows();
                        window.alert(r.message)
                        console.log("創建成功")
                    }else{
                        alert(r.message);
                    }
                }
            });
        }
    },
    created:function (){
        console.log("執行getproduct");
        this.getproduct();
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
