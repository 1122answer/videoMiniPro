

const app = getApp()

Page({
  data: {
    faceUrl: "../resource/images/noneface.png",
    isMe: true,
    isFollow: false,


    videoSelClass: "video-info",
    isSelectedWork: "video-info-selected",
    isSelectedLike: "",
    isSelectedFollow: "",

    myVideoList: [],
    myVideoPage: 1,
    myVideoTotal: 1,

    likeVideoList: [],
    likeVideoPage: 1,
    likeVideoTotal: 1,

    followVideoList: [],
    followVideoPage: 1,
    followVideoTotal: 1,

    myWorkFalg: false,
    myLikesFalg: true,
    myFollowFalg: true
  },
  onLoad(params){
    let me =this;
    //var user = app.userInfo;
    let user = app.getGlobalUserInfo();
    var userId = user.id;
    var serverUrl = app.serverUrl;

    var publisherId = params.publisherId;
    if (publisherId != null && publisherId != '' && publisherId != undefined) {
      userId = publisherId;
      me.setData({
        isMe: false,
        publisherId: publisherId,
        serverUrl: app.serverUrl
      })
    }
    me.setData({
      userId: userId
    })
    wx.showLoading({
      title: '请等待...',
    });
    // 调用后端
    wx.request({
      url: serverUrl + '/user/query?userId=' + userId + "&fanId=" + user.id,
      method: "POST",
      header: {
        'content-type': 'application/json', // 默认值
        'headerUserId': user.id,
        'headerUserToken': user.userToken
      },
      success(res) {
        console.log(res.data);
        wx.hideLoading();
        if (res.data.status == 200) {
          
           let  userInfo = res.data.data;
          let faceUrlData = userInfo.faceImage && (serverUrl + userInfo.faceImage ) || me.data.faceUrl
          me.setData({
            faceUrl:  faceUrlData,
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

    let serverUrl = app.serverUrl;
    //var user = app.userInfo;
    let user = app.getGlobalUserInfo();
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
          //app.userInfo =null;
          //注销以后清空缓存
          wx.removeStorageSync("userInfo");
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
       // console.log(res,app.userInfo.id)
        wx.showLoading({
          title: '上传中',
        })
        // fixme 修改原有的全局对象为本地缓存
        var userInfo = app.getGlobalUserInfo();
        wx.uploadFile({
          url: serverUrl + '/user/uploadFace?userId=' + userInfo.id, //仅为示例，非真实的接口地址
          filePath: tempFilePaths[0],
          header: {
            'content-type': 'application/json', // 默认值
            'headerUserId': userInfo.id,
            'headerUserToken': userInfo.userToken
          },
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

        if (duration > 16) {
          wx.showToast({
            title: '视频长度不能超过15秒...',
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
