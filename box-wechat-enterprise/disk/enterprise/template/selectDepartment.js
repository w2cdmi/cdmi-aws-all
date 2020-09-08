// disk/enterprise/template/selectDepartment.js
var enterpriseClient = require("../../module/enterprise.js");
var config = require("../../config.js");

var crumbs = [];
var srcDeptId = 0;      //原部门Id
var currentDeptId = 0;
var employeId = 0;

Page({

    /**
     * 页面的初始数据
     */
    data: {

    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        employeId = options.employeId;
        srcDeptId = options.srcDeptId;
        var page = this;
        page.setData({
            listHeight: wx.getSystemInfoSync().windowHeight - 200
        });
        wx.setNavigationBarTitle({
            title: '移动到...',
        })

        enterpriseClient.getEmploye(employeId, function (data) {
            data.icon = config.host + "/ecm/api/v2/users/getAuthUserImage/" + data.cloudUserId + "?authorization=" + getApp().globalData.token;
            page.setData({
                employe: data
            });
        });

        crumbs = []
        var crumb = {
            'index': crumbs.length + 1,
            "name": getApp().globalData.enterpriseName,
            "deptId": 0
        }
        currentDeptId = 0;
        crumbs.push(crumb);
        page.setData({
            crumbs: crumbs
        });
        enterpriseClient.getDepartmentByParentId(0, function (data) {
            page.setData({
                departments: data
            });
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
    clickCrumb: function (e) {
        var crumb = e.currentTarget.dataset.crumb;
        currentDeptId = crumb.deptId;

        var index = e.currentTarget.dataset.crumb.index;
        crumbs = crumbs.splice(0, index);
        this.setData({
            crumbs: crumbs,
            currentDeptId: currentDeptId
        });

        var page = this;
        getDepartmentByParentId(currentDeptId, page);
    },
    onClickDepartment: function (e) {
        var page = this;
        var deptId = e.currentTarget.dataset.deptId;
        var name = e.currentTarget.dataset.name;

        currentDeptId = deptId;
        var crumb = {
            'index': crumbs.length + 1,
            'name': name,
            'deptId': deptId
        };
        crumbs.push(crumb);
        page.setData({
            crumbs: crumbs,
            currentDeptId: currentDeptId
        });

        getDepartmentByParentId(deptId, page);
    },
    onCancelMoveEmploye: function () {
        wx.navigateBack({
            delta: 1
        })
    },
    onConfirmMove: function (e) {
        enterpriseClient.updateDepartmentEmployment(srcDeptId, currentDeptId, employeId, function () {
            wx.showToast({
                title: '修改部门成功！',
                duration: 1000
            })
            setTimeout(function () {
                wx.navigateBack({
                    delta: 1
                })
            }, 1000);
        })
    }
})

function getDepartmentByParentId(deptId, page) {
    enterpriseClient.getDepartmentByParentId(deptId, function (data) {
        page.setData({
            departments: data
        });
    });
}