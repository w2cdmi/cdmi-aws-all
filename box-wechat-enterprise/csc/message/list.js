// csc/message/list.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    conversations: [{ //对话列表
      username: '张三',
      headurl: '',
      message: '明天过来吃鸡',
      from: '小程序',
      
    }, {
      username: '李四',
      headurl: '',
      message: '你妈妈喊你回家',
      from: '公众号',
    }, {
      username: '王五',
      headurl: '',
      message: '程序又错了',
      from: '企业文件宝',
    }]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})