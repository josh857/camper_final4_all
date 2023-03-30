//顯示首頁商品
let App = new Vue({
    el: "#App",
    data: {
        products: [],
        pageInfo: {},
        CarVo:{},
        key: ''
    },
    methods: {
        getsearch: function (pagenum) {
            if (!pagenum) {
                pagenum = 1;
            }
            $.ajax({
                url: '/v1/product/search',
                data: {
                    pagenum: pagenum,
                    key: this.key
                },
                method: 'GET',
                success: function (r) {
                    if (r.code == OK) {
                        App.pageInfo = r.data;
                        App.products = r.data.list;
                        console.log("加載搜尋成功")
                    } else {
                        console.log("加載失敗")
                    }
                }

            });
        },

    // getproduct:function (pagenum){
    //     if(!pagenum){
    //         pagenum=1;
    //     }
    //      $.ajax({
    //         url:'/v1/product',
    //          method:'GET',
    //          data:{
    //             pagenum:pagenum
    //          },
    //          success:function (r){
    //           console.log(r.code);
    //             if(r.code==OK){
    //            console.log(r.data);
    //                 App.products=r.data.list;
    //                 App.pageInfo=r.data
    //               console.log("加載成功");
    //           }else{
    //                 console.log("加載失敗")
    //             }
    //          }
    //      });
    // },
    //點選加入購物車 存取car1次
    gotocar: function (id) {
        console.log(id);
        $.ajax({
            url: '/v1/car/incar/' + id,
            method: 'POST',
            success: function (r) {
                if (r.code == CREATED) {
                    Cart.getrows();
                    window.alert(r.message)
                    console.log("創建成功")
                    // window.location.reload()
                }else{
                    window.alert(r.message)
                }
            }
        });
    },
},
    created:function () {
        console.log("執行getproduct")
        this.getsearch(1);
    }

});
//顯示購物車商品數量
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


