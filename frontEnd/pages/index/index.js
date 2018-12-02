//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
    totalPage:1,
    page:1,
    videoList:[],
    screenWidth:350,
    serverUrl:''
  },
  //事件处理函数
  bindViewTap: function() {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },
  onLoad() {
    let me =this;
   let screenWidth = wx.getSystemInfoSync()
  let page =this.data.page;
  let serverUrl=app.serverUrl;
  wx.showLoading({
    title: '请等待，加载中......',
  })
  wx.request({
    url: serverUrl + "/video/showAll?page=" + page,
    method: "POST", 
    success(res){
      wx.hideLoading();
       console.log(res.data)
       if(page===1){
         me.setData({
           videoList:[]
         })
       }
       let videoList = res.data.data.rows;
       let newVideoList = me.data.videoList;
       me.setData({
         videoList:newVideoList.concat(videoList),
         page:page,
         totalPage:res.data.data.total,
         serverUrl:serverUrl
       })
    }
  })
  },
  getUserInfo: function(e) {
    console.log(e)
    app.globalData.userInfo = e.detail.userInfo
    this.setData({
      userInfo: e.detail.userInfo,
      hasUserInfo: true
    })
  }
})
