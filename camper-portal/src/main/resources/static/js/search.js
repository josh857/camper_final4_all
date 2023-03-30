let searchapp = new Vue({
    el:'#searchApp',
    data:{
        pageInfo:{},
        products:[],
        key:''
    },
    methods:{
     getsearch:function (pagenum) {
         if (!pagenum) {
             pagenum = 1;
         }
         $.ajax({
             url:'/v1/product/search',
             data:{
                 pagenum:pagenum,
                 key:this.key
             },
             method:'GET',
             success:function (r){
                 if(r.code==OK){
                     searchapp.pageInfo=r.data;
                     searchapp.products=r.data.list;
                     console.log("加載搜尋成功")
                 }else{
                     console.log("加載失敗")
                 }
             }

         });
     }
    }
})