// disk/enterprise/employees-departments/person-details/personState/personState.js
var enterpriseClient = require("../../../../module/enterprise.js");

Page({

    /**
     * 页面的初始数据
     */
    data: {
        state: ['在职', '离职'],
        personState: '在职',
        employeeId: '',
    },

    toggleState: function (e) {
        var item = e.currentTarget.dataset.item;
        var page = this;
        var employeeId = page.data.employeeId;
        var personState = page.data.personState;
        var canDeletePerson = page.data.canDeletePerson;
        if (personState === item) return;
        if (item === '离职') {
            wx.showModal({
                content: '是否确定该成员为离职状态',
                success: function (res) {
                    if (res.confirm) {
                        if (canDeletePerson === 'no') {
                            wx.showModal({
                                title: '错误',                                
                                content: '不能将部门管理员设置为离职',
                            });
                        } else {
                            page.setData({
                                personState: '离职'
                            });
                            enterpriseClient.toggleState(employeeId, function () {
                                wx.showModal({
                                    content: '已设置该成员为离职状态',
                                })
                                wx.navigateBack({
                                    delta: 1
                                })
                            });
                        }
                    }
                }
            })
            return;
        }
    },
    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        this.setData({
            employeeId: options.employeeId,
            canDeletePerson: options.canDeletePerson
        })
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