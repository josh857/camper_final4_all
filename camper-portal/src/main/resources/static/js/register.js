
let app =new Vue({
    el:'#reg',
    data:{
        nickname:'',
        username:'',
        password:'',
        confirm:'',
        phone:'',
        address:'',
        hasError:false,
        message:''
    },
    methods:{
        register:function(){
            let _this = this;
        let data={
            nickname: this.nickname,
            username:this.username,
            password: this.password,
            confirm: this.confirm,
            phone:this.phone,
            address:this.address
        };

        console.log("執行方法")
            if(data.password!==data.confirm){
                this.message="確認密碼不一致";
                this.hasError=true;
                return;
            }

        $.ajax({
            url:"/register",
            data:data,
            method:"POST",
            success:function (r){
                console.log("接收到:"+r);
                if(r.code==CREATED){
                   console.log("保存成功");
                   console.log(r.message);
                   _this.hasError=false;
                   location.href='/login.html?register';
                }else{
                    _this.hasError=true;
                    _this.message=r.message;
                 console.log(r.message);
                }
            }
        })
        }
    }

});