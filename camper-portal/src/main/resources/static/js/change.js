let change = new Vue({
    el:"#change",
    data:{
        oldpassword:'',
        password:'',
        confirm:'',

    },
    methods:{
        ch:function () {
            if(this.password!=this.confirm){
                alert("確認密碼與密碼不一致")
            }
            let id = location.search;
            id=id.substring(1);
                console.log(id);
            let data ={
                oldpassword:this.oldpassword,
                password: this.password,
                confirm: this.confirm
            };
            console.log(data);
            $.ajax({
                url:'/change/'+id,
                data:data,
                method:'POST',
                success:function(r){
                    if(r.code==OK){
                        console.log(r.message);
                        alert("修改密碼成功");
                        location.href='/index.html';
                    }else{
                        alert(r.message);
                        console.log(r.message);
                    }
                }
            });
        }
    }
})