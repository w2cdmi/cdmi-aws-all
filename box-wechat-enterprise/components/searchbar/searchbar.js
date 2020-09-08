// components/searchbar/searchbar.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    isShowSort: {
      type: String,
      value: false
    },
    isShowViewStyle: {
      type: String,
      value: false
    }
  },

  /**
   * 组件的初始数据
   */
  data: {
    viewstyle: 'list'
  },
  
  /**
   * 组件的方法列表
   */
  methods: {
    changeViewStyle: function (e) {
      this.setData({
        viewstyle: ""
      });
    },
  }
})
