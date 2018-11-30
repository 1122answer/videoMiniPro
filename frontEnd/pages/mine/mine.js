

const app = getApp()

Page({
  data: {
    faceUrl: "../resource/images/noneface.png",
  },
  onLoad(){
    let self =this;
    let user = app.userInfo;
    let serverUrl = app.serverUrl;
    wx.showLoading({
      title: '请等待...',
    });
    // 调用后端
    wx.request({
      url: serverUrl + '/user/query?userId=' + user.id,
      method: "POST",
      header: {
        'content-type': 'application/json' // 默认值
      },
      success(res) {
        console.log(res.data);
        wx.hideLoading();
        if (res.data.status == 200) {
          
           let  userInfo = res.data.data;
          let faceUrlData = userInfo.faceImage&&userInfo.faceImage || self.data.faceUrl
          self.setData({
            faceUrl: serverUrl + faceUrlData,
            nickname: userInfo.nickname,
            fansCounts: userInfo.fansCounts,
            followCounts: userInfo.followCounts,
            receiveLikeCounts: userInfo.receiveLikeCounts,
          })
        } else if (res.data.status == 500) {
          // 失败弹出框
          wx.showToast({
            title: res.data.msg,
            icon: 'none',
            duration: 3000
          })
        }
      }
    })
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
  },
  uploadVideo(){
    wx.chooseVideo({
      sourceType:['album'],
      success(res){

        var duration = res.duration;
        var tmpHeight = res.height;
        var tmpWidth = res.width;
        var tmpVideoUrl = res.tempFilePath;
        var tmpCoverUrl = res.thumbTempFilePath;

        if (duration > 11) {
          wx.showToast({
            title: '视频长度不能超过10秒...',
            icon: "none",
            duration: 2500
          })
        } else if (duration < 1) {
          wx.showToast({
            title: '视频长度太短，请上传超过1秒的视频...',
            icon: "none",
            duration: 2500
          })
        } else {
          // 打开选择bgm的页面
          wx.navigateTo({
            url: '../chooseBgm/chooseBgm?duration=' + duration
              + "&tmpHeight=" + tmpHeight
              + "&tmpWidth=" + tmpWidth
              + "&tmpVideoUrl=" + tmpVideoUrl
              + "&tmpCoverUrl=" + tmpCoverUrl
            ,
          })
        }

      }
    })
  }
})
