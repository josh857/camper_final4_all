let uploadApp =new Vue({
    el:'#upload',
    data:{
        file:[],
        filenames:[],
    },
    methods:{
     uplaod:function () {
         const file = document.getElementById("input").files[0];
         let filename = document.getElementById("input").files[0].name;
         // 設定正則表達後綴名必須為.txt
         const regExp = /.txt/
         // 用正則表達.test 返回值判定
         let t = regExp.test(filename);
         console.log(t);
         if(t==false){
             $('#hasError').show();
             return
         }
         //如果判定為true則push倒待上傳
             this.filenames.push(filename);
             this.file.push(file)
            $('#hasError').hide();
     },



     saveFile:function (){
         var formdata = new FormData();
            for(var i=0;i<this.file.length;i++){
                formdata.append("files",this.file[i]);
            }
         $.ajax({
             url:'/v1/product/upload',
             method:'POST',
             data:formdata,
             // async:false,
             // cache:false,
             contentType:false,
             processData:false,
             success:function (r){
                 this.file=[];
                 console.log(file);
                 console.log("傳入後端成功")
             }
         });
     },
    }
})


