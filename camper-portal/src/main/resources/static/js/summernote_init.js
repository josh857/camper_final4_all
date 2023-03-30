//利用summerNode 上傳圖片

$(document).ready(function() {
    $('#summernote').summernote({
        height: 300,
        tabsize: 2,
        lang: 'zh-CN',
        placeholder: '请输入的详细描述...',
        callbacks:{
            onImageUpload:function (files) {
                //summernote 選擇了圖片以後自動執行的方法
                let file = files[0];
                let form = new FormData();
                form.append("imageFile",file);
                console.log(form);
                $.ajax({
                    url:'/upload/image',
                    method:'POST',
                    data:form,
                    contentType:false,
                    processData:false,
                    success:function (r){
                        console.log(r);
                        if(r.code===OK){
                            let img = new Image();
                            img.src=r.message; //照片的url 路徑
                            $("#summernote").summernote('insertNode',img);
                        }else {
                            alert(r.message);
                        }
                    }
                });
            }
        }
    });
});