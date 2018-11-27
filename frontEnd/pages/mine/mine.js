

const app = getApp()

Page({
  data: {
    faceUrl: "../resource/images/noneface.png",
  },
  logout:function(){

    var serverUrl = app.serverUrl;
    var user = app.userInfo;
    wx.request({
      url: serverUrl + "/logOut?userId=" + user.id,
      method: "POST",
      header: {
        "content-type": "application/json"
      },
      success: function (res) {
        // 登录成功跳转 
        if(res.data.status===200){
          wx.showToast({
            title: '注销成功',
            icon: 'success',
            duration: 2000
          });
          app.userInfo =null;

          wx.redirectTo({
            url: '../userLogin/login',
          })
        }
       
      }
    })
  },
  changeFace:function(){
    var me=this
    console.log(111)
    wx.chooseImage({
      count:1,
      sizeType:['compressed'],
      sourceType:['album'],
      success: function(res) {
        var tempFilePaths=res.tempFilePaths;
        var serverUrl=app.serverUrl
        console.log(res,app.userInfo.id)
        wx.showLoading({
          title: '上传中',
        })
        wx.uploadFile({
          url: serverUrl + '/user/uploadFace?userId=' + app.userInfo.id, //仅为示例，非真实的接口地址
          filePath: tempFilePaths[0],
          name: 'file',
          success(res) {
            wx.hideLoading()
            const data = JSON.parse( res.data)
            console.log(data);
            wx.showToast({
              title: '上传成功—',
              icon:'success'
            })
            var imgUrl= data.data
            me.setData({
              faceUrl:serverUrl+imgUrl
            })
          },
          complete(res){
            console.log(res)
          }
        })
      },
    })
  }
})
