// disk/enterprise/reselection/knowledger.js
var enterpriseClient = require("../../module/enterprise.js");
var config = require("../../config.js");

Page({

    /**
     * 页面的初始数据
     */
    data: {
        departmentName: "",
        manager: {
            avatar: '',
            name: '罗云飞',
            department: '研发部 市场综合部 技术部'
        },
        deptId: '',
    },
    reselectionManager: function () {
        wx.navigateTo({
            url: '/disk/enterprise/template/selectEmploye?deptId=' + this.data.deptId,
        });
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        var managerId = options.id;
        var page = this;
        enterpriseClient.getEmploye(managerId, function (data) {
            data.icon = config.host + "/ecm/api/v2/users/getAuthUserImage/" + data.cloudUserId + "?authorization=" + getApp().globalData.token;
            page.setData({
                manager: {
                    avatar: data.icon,
                    name: data.name,
                    // department: data.department
                },
                departmentName: options.departmentName,
                deptId: options.deptId
            })
        });

        enterpriseClient.getDepartment(managerId, function (data) {
            var departments = "";
            if (data.length !== 0) {
                for (var i = 0; i < data.length; i++) {
                    if (data[i]) {
                        departments = departments + data[i].name + " ";
                    }
                }
                page.setData({
                    department: departments,
                })
            }
        });
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