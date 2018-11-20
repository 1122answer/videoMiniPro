const app = getApp()

Page({
<<<<<<< HEAD
    data: {},
    doRegist: function (e) {
        console.log(e)
        var formObj = e.detail.value
        var username = formObj.username;
        var password = formObj.password;
        if (username.length == 0 || username.length == 0) {
            wx.showToast({
                title: '用户名不能为空',
                icon: "none",
                duration: 2000
            })
        } else {
            var serverUrl = app.serverUrl;
            wx.request({
                url: serverUrl + "/regist",
                method: "POST",
                data: {
                    username: username,
                    password: password
                },
                header: {
                    "content-type": "application/json"
                },
                success: function (res) {
                    console.log(res.data)
                    let status= res.data.status
                  if (status == 200) {
                      wx.showToast({
                        title: '注册成功！',
                        icon: "none",
                        duration: 3000
                      })
                    app.userInfo=res.data.data
                    }else{
                      wx.showToast({
                        title: res.data.msg,
                        icon: "none",
                        duration: 3000
                      })
                    }
                }
            })
=======
    data: {

    },
  doRegist:function(e){
    console.log(e)
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
          console.log(res.data)
          if(res==200){

          }
>>>>>>> parent of 8648787... add frontEnd folder
        }
      })
    }
  }
})