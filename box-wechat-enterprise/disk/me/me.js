// disk/me/me.js
var util = require("../module/utils.js");
var user = require("../module/user.js");
var config = require("../config.js");
var httpclient = require("../module/httpclient.js");
var session = require("../../session.js");
var enterpriseClient = require("../module/enterprise.js");

var isFirstLoad = true;

Page({

    /**
     * 页面的初始数据
     */
    data: {
        enterpriseName: '',  //企业名称
        storageSize: '',
        spaceUsed: 0,
        spaceSize: 0,
        localCache: 0,
    },


    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        isFirstLoad = true;
    },

    // 跳转到收件箱
    toInbox: function () {
        wx.navigateTo({
            url: '../inbox/inbox',
        })
    },

    // 跳转个人文件宝
    jumpToPerson: function (e) {
        wx.navigateToMiniProgram({
            appId: 'wxfbc7b65a9edb00f9',
            path: 'disk/index',
            extraData: {},
            envVersion: 'release',
            success(res) {
                console.log("跳转个人文件宝");
            },
            fail(res) {
                wx.showToast({
                    title: '跳转失败！',
                    duration: 1000
                })
            }
        })
    },

    /**
     * 生命周期函数--监听页面初次渲染完成
     */
    onReady: function () {
    },


    clearStorage: function () {
        try {
            wx.clearStorageSync();
            this.setData({
                localCache: 0 + 'KB'
            })
            wx.showToast({
                title: '清除成功！',
                icon: "success"
            })
        } catch (e) {
            wx.showToast({
                title: '清除缓存失败',
            })
        }
    },

    /**
         * 生命周期函数--监听页面显示
     */
    onShow: function () {
        var page = this;
        // 获取缓存大小
        try {
            wx.getStorageInfo({
                success: function (res) {
                    var data = res.currentSize > 1000 ? res.currentSize / 1000 + 'MB' : res.currentSize + 'KB';
                    page.setData({
                        localCache: data
                    })
                },
                fail: function () {
                    page.setData({
                        localCache: 0 + 'KB'
                    })
                },

            });
        } catch (e) {
            wx.showToast({
                title: '获取缓存失败',
            })
        }
        if (!isFirstLoad){
            return;
        }
        session.login();
        session.invokeAfterLogin(function () {
            wx.setNavigationBarTitle({ title: getApp().globalData.enterpriseName });
            page.setData({
                enterpriseName: getApp().globalData.enterpriseName,
                accountType: getApp().globalData.accountType,
                accountTypeName: util.formatAccountType(getApp().globalData.accountType),
                avatarUrl: getApp().globalData.avatarUrl,
                nick: getApp().globalData.userName,
                spaceUsed: 0,
                spaceSize: 0,
                isAdmin: getApp().globalData.isAdmin
            });

            var userId = getApp().globalData.userId;
            user.getUserInfo(getApp().globalData.userId, function (data) {
                var spaceUsed = (data.spaceUsed / data.spaceQuota) * 100;
                var spaceSize = util.formatFileSize(data.spaceUsed) + "/" + util.formatFileSize(data.spaceQuota);
                if (spaceSize == "/") {
                    spaceSize = 0;
                }
                page.setData({
                    spaceUsed: spaceUsed,
                    spaceSize: spaceSize
                });
            });

            user.getUserVipInfo(function (data) {
                page.setData({
                    expireDate: util.getFormatDate(data.expireDate, "yyyy-MM-dd")
                });
            });

            var res = wx.getStorageInfoSync();
            if (res.keys.length == 0) {
                page.setData({
                    storageSize: 0
                });
            } else {
                page.setData({
                    storageSize: res.currentSize
                });
            }
            isFirstLoad = false;
        });
    },

    onShowMenu: function () {
        this.setData({
            isShowMenu: "true"
        })
    },

    onUploadImage: function (e) {
        var page = this;
        var tempFiles = e.detail;

        getApp().globalData.tempFiles = tempFiles;
        wx.navigateTo({
            url: '/disk/widget/selectFolder?jumpType=' + "uploadImage",
        })
    },
    onUploadVideo: function (e) {
        var page = this;
        var tempFile = e.detail;

        getApp().globalData.tempFile = tempFile;
        wx.navigateTo({
            url: '/disk/widget/selectFolder?jumpType=' + "uploadVideo",
        })
    },
    showCreateFolder: function () {
        wx.showToast({
            title: '请您进入文件目录创建文件夹！',
            icon: 'none'
        })
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

    switchEnterprise: function () {
        enterpriseClient.getEnterpriseList(function (data) {
            getApp().globalData.enterpriseList = data;
            wx.navigateTo({
                url: '/disk/enterprise/enterpriselist',
            });
            isFirstLoad = true;
        });
    },
    bindtoEnterpriseManage: function () {
        wx.navigateTo({
            url: '../enterprise/management',
        })
    },
    openVIP: function () {
        var url = config.host + "/ecm/api/v2/tempAccount/up2VIP";
        var data = { "orderId": 1 };
        var header = {
            'Authorization': getApp().globalData.token
        }
        httpclient.post(url, data, header);
    },
    gotoBuyPage: function () {
        wx.navigateTo({
            url: '../buy/buyPersonStorage',
        });
    },
    gotorecycle: function () {
        wx.navigateTo({
            url: '../recycle/recycle',
        });
    },
    gotoPerson: function () {
        wx.navigateToMiniProgram({
            appId: 'wxfbc7b65a9edb00f9',
            path: 'disk/index',
            extraData: {},
            envVersion: 'release',
            success(res) {
                console.log("跳转个人文件宝");
            },
            fail(res) {
                wx.showToast({
                    title: '跳转失败！',
                    duration: 1000
                })
            }
        })
    },
    jumpCreateEnterprisePage: function () {
        wx.navigateTo({
            url: '/disk/enterprise/registered/registered?delta=' + 1,
        })
    },
    jumpToProtocol: function () {
        wx.navigateTo({
            url: '/disk/enterprise/protocol/protocol',
        })
    }
})