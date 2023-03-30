let imgUpload = new Vue({
    el:'#imgupload',
    data:{
        imgurl:''
    },
    methods:{
     imageUpload:function () {
         //獲取file 文件內容list
         var files=$('input[name="file"]').prop('files');
         console.log(files);
         var file =files[0]
         console.log(file);
         //base64 前置
         var img ="data:image/png;base64,"
         //創建formdata append "name" 與 file對象至後端
         //name 名稱 與後端 controller 傳入的參數名要一至
         let form = new FormData();
         form.append("imageFile",file)
         $.ajax({
             url:'/upload',
             method:'POST',
             data:form,
             contentType:false,
             processData:false,
             success:function (r) {
                 if(r.code==OK){
                    var image = img+r.message;
                     imgUpload.imgurl=image;
                     console.log(image);
                     alert("存取成功");
                 }else{
                     alert("存取失敗");
                 }
             }

         })
     }
    }
})