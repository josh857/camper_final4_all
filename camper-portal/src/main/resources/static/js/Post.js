
let updateApp = new Vue({
    el:'#postPrduct',
    data:{
        selectimgurl:[],
        imgurl:'',
        Products:[],
        product:{},

        productName:'',
        person:'',
        functional:'',
        content:'',
        price:'',
        type:'',

        hasError:false,
        message:'',
        file:''
    }
    ,
    methods:{
        //暫先存入變數
        saveProduct: function () {
            let data = {
                productName: this.productName,
                person:this.person,
                functional:this.functional,
                content:this.content,
                price:this.price,
                type:this.type,
                file:this.file
            }
            this.Products.push(data);
            this.productName='';
            this.person='';
            this.functional='';
            this.content='';
            this.price='';
            this.type='';
            updateApp.selectimgurl.push(updateApp.imgurl);
            console.log(this.Products)
        },

        upload:function () {
        const file =document.getElementById("input").files[0];

            let form = new FormData();
            form.append("file",file)
            var img ="data:image/png;base64,"
            $.ajax({
                url:'/v1/product/getfile',
                method:'POST',
                data:form,
                contentType:false,
                processData:false,
                success:function (r){
                    if(r.code==OK){
                        var image = img+r.message;
                        updateApp.imgurl=image;
                        updateApp.file=r.message;
                    }
                }
            })
        },
        postProduct:function () {
            let data={ products:this.products}
            $.ajax({
                    url:'/v1/product/save',
                    method:'POST',
                    data:JSON.stringify(data),
                    datatype:'json',
                    contentType: 'application/json;charset=utf-8',
                    success:function (r) {
                        if(r.code==OK){
                            updateApp.message=r.message;
                            updateApp.hasError=true;
                        }else{
                            updateApp.message=r.message;
                            updateApp.hasError=true;
                        }
                    }
            })
        }
    }

});