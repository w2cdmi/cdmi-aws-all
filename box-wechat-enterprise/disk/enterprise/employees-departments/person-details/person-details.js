// disk/enterprise/employees-departments/person-details/person-details.js
var enterpriseClient = require("../../../module/enterprise.js");
var config = require("../../../config.js");

Page({

    /**
     * 页面的初始数据
     */
    data: {
        employeeId: 0,
        employee: {},
        hasJob: true,
        job: [],
        roles: [],
        isShowDelete: false,
        canDeletePerson: 'yes',
    },

    showDelete: function () {
        this.setData({
            isShowDelete: true
        })
    },

    gotoPersonState: function () {
        var employeeId = this.data.employeeId;
        var canDeletePerson = this.data.canDeletePerson;
        wx.navigateTo({
            url: '/disk/enterprise/employees-departments/person-details/personState/personState?employeeId=' + employeeId + '&canDeletePerson=' + canDeletePerson,
        })
    },

    deleteDepartment: function (e) {
        var item = e.currentTarget.dataset.item;
        var employeeId = this.data.employeeId;
        var page = this;
        wx.showModal({
            title: '',
            content: '确定要删除' + item.name + '-' + item.role + '吗？',
            success: function (res) {
                // 删除管理员
                if (item.role) {
                    enterpriseClient.deleteManager(item.departmentId, employeeId, function (data) {
                        var res = data;
                    })
                }

                if (page.data.job.length === 2 && page.data.job[0] == null) {
                    enterpriseClient.updateDepartmentEmployment(page.data.job[1].departmentId, 0, employeeId, function () {
                        wx.showToast({
                            title: '删除成功！',
                            duration: 1000
                        })
                    });
                    page.setData({
                        isShowDelete: false,
                        hasJob: false,
                        job: []
                    })
                    return;
                } else if (page.data.job.length === 1){
                    enterpriseClient.updateDepartmentEmployment(page.data.job[0].departmentId, 0, employeeId, function () {
                        wx.showToast({
                            title: '删除成功！',
                            duration: 1000
                        })
                    });
                    page.setData({
                        isShowDelete: false,
                        hasJob: false,
                        job: []
                    })
                    return;
                } else {
                    if (res.confirm) {  // 删除部门数据
                        enterpriseClient.deleteDepartmentEmployment(item.departmentId, employeeId, function (data) {
                            getDepartment(employeeId, page);
                        });

                    }
                }
            },
            fail: function () {
                wx.showToast({
                    title: '请重试'
                })
            }
        })
    },

    hideDelete: function () {
        this.setData({
            isShowDelete: false
        })
    },

    back: function () {
        wx.navigateBack({
            delta: 1
        })
    },

    // 新增职务页面
    addDepartment: function () {
        var page = this;
        wx.navigateTo({
            url: '/disk/enterprise/employees-departments/addDepartment/addDepartment?employeeId=' + page.data.employeeId,
        })
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        var employeeId = options.employeeId;
        this.setData({
            employeeId: employeeId
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
        var employee = {};
        var page = this;
        enterpriseClient.getEmploye(page.data.employeeId, function (data) {
            data.icon = config.host + "/ecm/api/v2/users/getAuthUserImage/" + data.cloudUserId + "?authorization=" + getApp().globalData.token;
            employee = {
                name: data.name,
                sex: "男",
                // email: data.email, // 数据有问题暂时不显示
                email: '',
                mobile: data.mobile,
                avatarUrl: data.icon,
                state: data.status === -2 ? '离职' : '在职' // 判断在职/离职状态
            }
            page.setData({
                employee: employee
            })
        });

        getDepartment(page.data.employeeId, page);
    },
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
                                        // 只要所在的部门有一个为管理员就不能将该员工设置为离职
                                        page.setData({
                                            canDeletePerson: 'no'
                                        });
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