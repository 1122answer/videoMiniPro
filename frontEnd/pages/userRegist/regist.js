const app = getApp()

Page({
    data: {

    },
  doRegist:function(e){
    console.log(e)
    wx.showLoading({
      title: '请等待',
    })
    var formObj = e.detail.value
    var username=formObj.username;
    var password = formObj.password;
    if(username.length==0|| username.length==0){
      wx.showToast({
        title: '用户名不能为空',
        icon:"none",
        duration:2000
      })
    }else{
      var serverUrl=app.serverUrl;
      wx.request({
        url: serverUrl +"/regist",
        method:"POST",
        data:{
          username:username,
          password:password
        },
        header:{
          "content-type":"application/json"
        },
        success:function(res){
          wx.hideLoading()
          console.log(res.data)
          if (res.data.status==200){

          }
        }
      })
    }
  },

  goLoginPage: function () {
    wx.redirectTo({
      url: '../userLogin/login',
    })
  }
})