// disk/enterprise/employees-departments/addDepartment/addEmployee.js
var enterpriseClient = require("../../../module/enterprise.js");

Page({

    /**
     * 页面的初始数据
     */
    data: {
        employeeId: 0,
        roles: [
            // '普通成员', '知识专员', '主管'
            '普通成员', '主管'
        ],
        selectedRole: -1,
        selectedItem: '',
        deptId: -1,
    },

    onSelectRole: function (e) {
        var index = e.currentTarget.dataset.index;
        var item = e.currentTarget.dataset.item;
        this.setData({
            selectedRole: index,
            selectedItem: item
        })
    },

    completed: function (e) {
        var roleIndex = this.data.selectedRole;
        var roles = this.data.roles;
        if (roleIndex === -1) {
            wx.showToast({
                title: '请选择职务！',
            })
        } else {
            var page = this;
            // 删除管理员数据
            var employeeId = this.data.employeeId;
            var deptId = this.data.deptId;

            enterpriseClient.deleteManager(deptId, employeeId, function (data) {
                var res = data;
            })

            if (roles[roleIndex] === '普通成员') {
                // 不设置，自动为普通成员

                wx.navigateBack({
                    delta: 2
                })
            } else if (roles[roleIndex] === '知识专员') {
                // 暂未开发知识专员部分

                wx.navigateBack({
                    delta: 2
                })
            } else {
                
                var employeeId = {
                    employeeId: page.data.employeeId
                }
                enterpriseClient.setManager(page.data.deptId, employeeId, function (data) {
                    wx.navigateBack({
                        delta: 2
                    })
                });
            }
        }
    },

    deleteDepartment: function () {
        var employeeId = this.data.employeeId;
        var deptId = this.data.deptId;

        enterpriseClient.deleteManager(deptId, employeeId, function (data) {
            var res = data;
        })
    

},

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        this.setData({
            employeeId: options.employeeId,
            deptId: options.deptId
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

function getDepartment(employeeId, page) {
    enterpriseClient.getDepartment(employeeId, function (data) {
        if (data.length === 1 && data[0] == null) {
            page.setData({
                isShowDelete: false,
                hasJob: false,
                job: []
            })
        } else {
            var role;
            for (var i = 0; i < data.length; i++) {
                if (data[i]) {
                    let index = i;
                    var deptId = {
                        deptid: data[i].departmentId
                    }

                    enterpriseClient.getDepartmentRole(employeeId, deptId, function (roleData) {
                        if (roleData.length) {
                            switch (roleData[0].role) {
                                case 3:
                                    data[index].role = '主管';
                                    break;
                                case 2:
                                    data[index].role = '知识管理员';
                                    break;
                                default:
                                    data[index].role = '普通成员';
                            }
                        } else {
                            data[index].role = '普通成员';
                        }

                        page.setData({
                            hasJob: true,
                            job: data
                        })
                    });
                }

            }
        }
    });
}