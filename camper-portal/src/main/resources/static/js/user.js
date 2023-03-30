let my =new Vue({
    el:"#my",
    data:{
        userVo: {}
    },
    methods:{
        getMy:function (){
            $.ajax({
                url:'/v1/user/me',
                method:'GET',
                success:function (r){
                 if(r.code==OK){
                     my.userVo=r.data;
                     console.log(r.data);
                     console.log("加載成功");
                 }else{
                     console.log("加載失敗");
                     console.log(r.message);
                 }
                }
            })





        }
    },
   created: function(){
        console.log("執行方法")
        this.getMy();
    }
});