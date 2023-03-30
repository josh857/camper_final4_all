

Vue.component('v-select',VueSelect.VueSelect);
let OrderApp = new Vue({
    el: '#orderApp',
    data: {
        user: {},
        username: '',
        phone: '',
        // city:[],
        // selectcity:'',
        range: [],
        selectrange: '',
        num: '',
        address: '',
        type1: '',
        type2: '',
        floor: '',
        dopay:'2',
    },
    methods: {
        getuser: function () {
            $.ajax({
                url: '/v1/orders/getuser',
                method: 'GET',
                success: function (r) {
                    if (r.code == OK) {
                        OrderApp.user = r.data;
                        console.log(r.data);
                        console.log("成功加載用戶資料");
                    } else {
                        console.log(r.message);
                    }
                }
            })
        },
        // getcity(){
        //  $.ajax({
        //    url:'/v1/orders/getcity',
        //      method:'GET',
        //      success:function(r){
        //       if(r.code==OK){
        //           OrderApp.city=r.data;
        //           console.log(r.data);
        //       }
        //      }
        //  })
        // },
        getrange: function () {
            var type = $('select').val();
            $.ajax({
                url: "/v1/orders/getrange/" + type,
                method: 'GET',
                success: function (r) {
                    if (r.code == OK) {
                        console.log(r.data);
                        OrderApp.range = r.data;
                        console.log("獲取range成功")
                    } else {
                        console.log(r.message);
                    }
                }
            })
        },
        insertorder: function () {
            var city = $('select').val();
            //獲得拼湊地址
            var address = city + "," + this.selectrange + "," + this.num + "," + this.address + "," + this.floor + "樓";

            let type = null;
            if (this.type1) {
                type = this.type1;
            } else {
                type = this.type2;
            }
            let data = {
                nickname: this.username,
                phone: this.phone,
                address: address,
                type: type
            }
            console.log(data);
            $.ajax({
                url: '/v1/orders/insert',
                data: data,
                method: 'POST',
                success: function (r) {
                    if (r.code == OK) {
                        OrderApp.dopay='1'
                        console.log("存取成功");
                    } else {
                        console.log(r.message);
                        window.alert("請確認格式")
                        return
                    }
                },
            })
            if(OrderApp.dopay=='1') {
                this.paypal();
            }
        },
        paypal: function () {
            $.ajax({
                url: '/v1/orders/paypal',
                method: 'GET',
                success: function (r) {
                    if (r.code == OK) {
                        var url = r.message;
                        console.log(url);
                        window.location = (url)
                    }

                }
            })
        },

    },
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