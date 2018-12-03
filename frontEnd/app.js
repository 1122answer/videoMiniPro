//app.js
App({
  serverUrl:"http://localhost:8085",
  userInfo:null,
  setGlobalUserInfo(user){
    wx.setStorageSync("userInfo", user)
  },
  getGlobalUserInfo(){
    return wx.getStorageSync("userInfo")
  }
})