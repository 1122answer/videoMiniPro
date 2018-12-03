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
    let screenWidth = wx.getSystemInfoSync().screenWidth;
    me.setData({
      screenWidth:screenWidth
    })
    let page =this.data.page;
    me.getAllVideoList(page);
  },
  getAllVideoList(page){
    let me = this;
    let serverUrl = app.serverUrl;
    wx.showLoading({
      title: '请等待，加载中......',
    })
    
    wx.request({
      url: serverUrl + "/video/showAll?page=" + page,
      method: "POST",
      success(res) {
        wx.hideLoading();
        wx.hideNavigationBarLoading();
        wx.stopPullDownRefresh();
        console.log(res.data)
        if (page === 1) {
          me.setData({
            videoList: []
          })
        }
        let videoList = res.data.data.rows;
        let newVideoList = me.data.videoList;
        me.setData({
          videoList: newVideoList.concat(videoList),
          page: page,
          totalPage: res.data.data.total,
          serverUrl: serverUrl
        })
      }
    })
  },
  onReachBottom(){
    let me = this;
    let curentPage = me.data.page;
    let totalPage = me.data.totalPage;
    if(curentPage === totalPage){
      wx.showToast({
        title: '已经没有视频了',
        icon:"none"
      })
      return;
    }
    let page = curentPage + 1;
    me.getAllVideoList(page)

  },
  onPullDownRefresh(){
    wx.showNavigationBarLoading();
    this.getAllVideoList(1)
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
