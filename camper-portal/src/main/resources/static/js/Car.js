 let Cartt = new Vue ({
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
                         Cartt.CarVo= r.data;
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

let Car = new Vue ({
    el:'#top' ,
     data:{
     row:''
    },
     methods:{
        getrows:function(){
            $.ajax({
              url:'/v1/car/getrows',
                method:'GET',
                success:function (r){
                 if (r.code==OK){
                  Car.row = r.data;
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


//顯示購物車上的商品資訊
 let carApp = new Vue({
   el:'#carApp',
    data:{
       products:[],

    },
   methods: {
       product: function () {
           $.ajax({
               url: '/v1/car/getproducts',
               method: 'GET',
               success: function (r) {
                   if (r.code == OK) {
                       carApp.products = r.data;
                       console.log("搜尋到到商品")
                       console.log(r.data)
                   } else {
                       console.log("搜尋失敗")
                   }
               }
           })
       },
       plus:function(id){
          let quan=1;
          //此為加法後的product對象
          let el = null;
            for (var i =0; i<this.products.length;i++){
                if(this.products[i].id === id){
                    if(this.products[i].quantity>=10){
                        return;
                    }
                    //放進前端 顯示的對象的數量
                    this.products[i].quantity += quan;
                    this.products[i].total=this.products[i].price*this.products[i].quantity
                    el = this.products[i];
                    break;
                }
            }
            //將更新後的對象數量 quntity 傳入後端進行更新數量資訊
           $.ajax({
               url:'/v1/shopcar/update/'+id,
               data:{
                   quan:el.quantity,
                   // quan:el.total
               },
               method:'POST',
               success:function (r){
                   if(r.code==OK){
                       console.log("更新成功");
                   }
               }
           });
            // window.location.reload();
       },

       less:function(id){
           let quan=-1;
           let el=null;
           for (var i =0; i<this.products.length;i++){
               if(this.products[i].id === id){
                   if(this.products[i].quantity<=1){
                       return;
                   }
                   this.products[i].quantity+=quan;
                   this.products[i].total=this.products[i].price*this.products[i].quantity
                   el=this.products[i];
                   break;
               }
           }
           $.ajax({
               url:'/v1/shopcar/update/'+id,
               data:{
                   quan:el.quantity,
                   // quan:el.total
               },
               method:'POST',
               success:function (r){
                   if(r.code==OK){
                       console.log("更新成功");
                   }
               }
           });
           // window.location.reload();
       },


       deleteproduct:function (id){
           $.ajax({
               url:'/v1/shopcar/delete/'+id,
               method:'POST',
               success:function(r) {
                   if(r.code==OK){
                       carApp.product();
                       console.log("刪除成功")
                   }else{
                       console.log(r.message);
                   }
               }
           });
       },

   },
    created: function () {
        console.log("執行product方法");
        this.product();

    }
   });
