var session = require("../session.js");
var File = require("module/file.js");
var utils = require("module/utils.js");
var link = require("module/link.js");
var Message = require("module/message.js");
var ViewedAndShortcutFolder = require("module/viewedAndShortcutFolder.js");

var Inbox = require("module/inbox.js"); //获取link

var enterprise = require("module/enterprise.js");
var Robot = require("module/robot.js");

var app = getApp();
var sliderWidth = 96; // 需要设置slider的宽度，用于计算中间位置

var forwardId;  //转发id
var enterpriseId;   //企业id

var isFirstLoad = true;

// 滑动的起始坐标
var startX = 0;
var startY = 0;

Page({
    data: {
        tabs: [{
            "index": 1,
            "imgPath": "./images/icon/tab-files-history.png",
            "name": "最近浏览"
        },
        {
            "index": 2,
            "imgPath": "./images/icon/tab-files-share-byme.png",
            "name": "我的分享"
        },
        {
            "index": 3,
            "imgPath": "./images/icon/tab-files-share-tome.png",
            "name": "他人分享"
        }],
        commonsCount: 0,
        files: [],
        folders: [],
        isClearOldDate: true,   //清除老数据
        items: [],
        startX: 0, //开始坐标
        startY: 0,
        tabId: 1,//默认打开的是最近浏览
        ByMeSharePageNum: 0,//分页：我的分享  获取分页 offset的默认值
        ToMeSharePageNum: 0,//分页：他人分享  获取分页 offset的默认值
        currPageCount: 1,  //分页：当前的分页是第几页
        currentPageIcon: true,
        previewImageUrls: [],
        folderList: [],
        isTouchMoveIndex: -1,
        isBackupsing: false,    // 备份状态
        avatarUrl: '',
        
        isShowBlankPage: false,
        loadMoreIcon: false,//分页：是否隐藏加载更多图标
    },
    onLoad: function (options) {
        isFirstLoad = true;
        //如果是分享链接
        var linkCode = options.linkCode;
        forwardId = options.forwardId;
        enterpriseId = options.enterpriseId;
        var ownerId = options.ownerId;
        var nodeId = options.nodeId;
        if (linkCode != undefined && linkCode != '' && forwardId != undefined && forwardId != "" && enterpriseId != undefined && enterpriseId != "") {
            if (forwardId == getApp().globalData.cloudUserId) {
                getApp().globalData.indexParam = 2;     //2:我的分享
            } else {
                getApp().globalData.indexParam = 3;     //3:他人分享
            }

            wx.navigateTo({
                url: '/disk/shares/sharefile?linkCode=' + linkCode + '&forwardId=' + forwardId + "&enterpriseId=" + enterpriseId,
            })
        } else if (ownerId != undefined && ownerId != "" && nodeId != undefined && nodeId != "" && enterpriseId != undefined && enterpriseId != "") {
            if (ownerId == getApp().globalData.cloudUserId) {
                getApp().globalData.indexParam = 2;     //2:我的分享
            } else {
                getApp().globalData.indexParam = 3;     //3:他人分享
            }

            forwardId = ownerId;
            wx.navigateTo({
                url: '/disk/shares/sharefile?ownerId=' + ownerId + '&nodeId=' + nodeId + "&enterpriseId=" + enterpriseId,
            })
        }
        //收件箱跳转
        var folderId = options.folderId;
        if (folderId != undefined && folderId != "") {
            var creater = options.creater;
            var folderName = options.folderName;
            wx.navigateTo({
                url: '/disk/inbox/upload/upload?folderId=' + folderId + '&creater=' + creater + '&folderName=' + folderName + '&ownerId=' + ownerId + '&linkCode=' + linkCode,
            })
        }

        var page = this;

        //评论消息通知
        page['theCountOfCommentMessages'] = Message.theCountOfCommentMessages;


    },
    onShow: function () {

        var page = this;
        page.setData({
            avatarUrl: getApp().globalData.avatarUrl,
            isBackupsing: getApp().globalData.isOpenRobot
        })

        if (!isFirstLoad && !getApp().globalData.isSwitchEnterprise) {
            return;
        }

        if (enterpriseId != undefined && enterpriseId != "") {
            enterprise.isExistEmploye(enterpriseId, function (isExist) {
                if (!isExist) {
                    session.initGlobalData();
                    session.login({
                        enterpriseId: 0
                    });
                } else {
                    session.login({
                        enterpriseId: enterpriseId
                    });
                }
                enterpriseId = "";
            });
        } else {
            session.login();
        }
        session.invokeAfterLogin(function () {

            // 获取快捷文件夹
            var userId = getApp().globalData.userId;
            if (userId) {
                getShortcutFolder(page);
            }
            // 获取最近浏览列表
            getRecentFileList(page);
            // 获取备份状态
            getRobotCheck(page);


            var enterpriseName = getApp().globalData.enterpriseName;
            if (typeof (enterpriseName) != 'undefined' && enterpriseName != '') {
                wx.setNavigationBarTitle({ title: getApp().globalData.enterpriseName });
            }
            var data = {};
            var files = [];
            data.files = files;
            page.setData({
                data: data,
                isClearOldDate: true
            });
            switch (getApp().globalData.indexParam) {
                case 1:
                    // getRecentFileList(page)
                    page.setData({
                        isScrollX: true
                    })
                    break;
                case 2:
                    getSharedByMeFileList(page)
                    page.setData({
                        isScrollX: true
                    })
                    break;
                case 3:
                    getSharedToMeFileList(page)
                    page.setData({
                        isScrollX: true
                    })
                    break;
            }
            isFirstLoad = false;
            getApp().globalData.isSwitchEnterprise = false;
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
     * 生命周期函数--监听页面初次渲染完成
     */
    onReady: function () {
        if (wx.hideShareMenu) {
            wx.hideShareMenu();
        } else {
            // 如果希望用户在最新版本的客户端上体验您的小程序，可以这样子提示
            wx.showModal({
                title: '提示',
                content: '当前微信版本过低，无法使用该功能，请升级到最新微信版本后重试。',
                showCancel: false
            })
        }
    },
    //滚动到底部触发事件,上拉加载更多
    scrollLower: function () {

        if (this.data.pageCount == this.data.currPageCount) { //如果当前页数已经等于总共分页数，则停止
            this.setData({
                loadMoreIcon: true,  //触发到上拉事件，不隐藏加载图标
            })
            return;
        }
        wx.setStorageSync('isShowLoading', false);
        var page = this;
        var tabId = this.data.tabId;
        var datas = page.data.data;
        if (tabId == 2) {
            page.setData({
                ByMeSharePageNum: page.data.ByMeSharePageNum + 10, //每次触发上拉事件，把searchPageNum+1  
                currPageCount: page.data.currPageCount + 1, //当前是分页的第几页
                loadMoreIcon: false  //触发到上拉事件，不隐藏加载图标
            });
            File.listMySharetTo(function (data) {
                if (data.contents.length > 0) {
                    var files = translateShareRecord(data);
                    var newData = new Object();
                    newData.files = datas.files.concat(files)
                    setTimeout(function () {
                        page.setData({
                            loadMoreIcon: true,
                            data: newData,
                        })
                    }, 800);
                } else {
                    setTimeout(function () {
                        page.setData({
                            loadMoreIcon: true,
                        })
                    }, 800);
                }
                return;
            }, page.data.ByMeSharePageNum);
        } else if (tabId == 3) {
            page.setData({
                ToMeSharePageNum: page.data.ToMeSharePageNum + 10, //每次触发上拉事件，把searchPageNum+1  
                loadMoreIcon: false  //触发到上拉事件，把isFromSearch设为为false  
            });
            File.listShareToMe(function (data) {
                if (data.contents.length > 0) {
                    var files = translateShareRecord(data);
                    var newData = new Object();
                    newData.files = datas.files.concat(files)
                    setTimeout(function () {
                        page.setData({
                            data: newData,
                            loadMoreIcon: true,
                        })
                    }, 1200);
                } else {
                    setTimeout(function () {
                        page.setData({
                            loadMoreIcon: true,
                        })
                    }, 1200);
                }
                return;
            }, page.data.ToMeSharePageNum);
        } else {//最近浏览不需要分页
            wx.removeStorageSync('isShowLoading');
            return;
        }
        wx.removeStorageSync('isShowLoading');
    },

    // 跳转到微信备份
    goToBackups: function () {
        wx.navigateTo({
            url: '/disk/backup/index',
        })
    },
    onShowMenu: function () {
        this.setData({
            isShow: "true"
        })
    },

    // 跳转到微信备份中页面
    goToBackupsing: function () {
        wx.navigateTo({
            url: '/disk/weChatBackup/weChatBackup',
        })
    },

    // 跳转到最近浏览
    goToRecentlyViewed: function () {
        var length = this.data.data.files.length;
        if (length !== 0) {
            wx.navigateTo({
                url: '/disk/recentlyViewed/recentlyViewed',
            })
        }
    },

    //侧滑-手指触摸动作开始 记录起点X坐标
    touchstart: function (e) {
        // 起始位置
        startX = e.changedTouches[0].clientX;
        startY = e.changedTouches[0].clientY;

        this.setData({
            isTouchMoveIndex: -1,
        })
    },
    //侧滑-滑动事件处理
    touchend: function (e) {
        var index = e.currentTarget.dataset.index;//当前索引
        var item = e.currentTarget.dataset.item;
        var touchEndX = e.changedTouches[0].clientX;//滑动终止x坐标
        var touchEndY = e.changedTouches[0].clientY;//滑动终止y坐标
        var shortcut = e.currentTarget.dataset.shortcut;


        //获取滑动角度，Math.atan()返回数字的反正切值
        var angle = 360 * Math.atan((touchEndY - startY) / (touchEndX - startX)) / (2 * Math.PI);

        //滑动超过30度角 return
        if (Math.abs(angle) > 30) return;

        // 滑动距离过小即是点击进入文件夹
        if (((touchEndX - startX) > -50) && ((touchEndX - startX) < 50)) {
            this.fileItemClick(e);
            return;
        }

        if (touchEndX > startX) { //向右滑
            index = -1
        }
        this.setData({
            isTouchMoveIndex: index,
            shortcut: shortcut ? shortcut : 'other'
        })
    },

    // 最近浏览删除
    deleteBrowseRecords: function (e) {
        var page = this;
        var item = e.currentTarget.dataset.item;

        wx.showModal({
            cancelText: "取消",
            confirmText: "删除",
            title: '提示',
            content: '您确认删除？',
            success: (res) => {
                if (res.confirm) {
                    var nodeId = item.nodeId;
                    var ownerId = item.ownerId;
                    File.deleteBrowseRecord(ownerId, nodeId, (res) => {
                        getRecentFileList(page);
                    });
                }
            }
        });
    },

    //快捷文件夹删除
    deleteShortcutFolder: function (e) {
        var page = this;
        var item = e.currentTarget.dataset.item;
        var ownerId = item.ownerId;
        var rowId = item.id;
        var param = { ownerId, rowId };
        wx.showModal({
            cancelText: "取消",
            confirmText: "删除",
            title: '提示',
            content: '您确认删除？',
            success: (res) => {
                if (res.confirm) {
                    ViewedAndShortcutFolder.deleteShortcutFolder(param, (res) => {
                        var data = res;
                        getShortcutFolder(page);
                        this.setData({
                            isTouchMoveIndex: -1
                        })
                    });
                }
            }
        });
    },
    //切换Tab选项卡
    tabClick: function (e) {
        var page = this;

        var tabId = e.currentTarget.dataset.index;
        if (tabId == 1) {
            page.setData({
                isScrollX: true,//控制左滑删除是否出现+消息提示加不加载
                commentsTips: false,
                loadMoreIcon: true
            })
        } else if (tabId === 2) {
            page.setData({
                isScrollX: false,
                commentsTips: true
            })
        } else {
            page.setData({
                isScrollX: false,
                commentsTips: false
            })
        }

        //先初始化页面
        page.setData({
            files: [],
            isClearOldDate: true,
            tabId: tabId,
            loadingMoreIcon: true,
        });

        var index = e.currentTarget.dataset.index;
        getApp().globalData.indexParam = index;
        //切换会刷新数据
        if (index == 1) { //获得最新浏览记录
            getRecentFileList(page)
        } else if (index == 2) {//获得我的分享记录
            getSharedByMeFileList(page)
        } else if (index == 3) {//获得他人分享给我的记录
            getSharedToMeFileList(page)
        }
    },

    //点击文件或文件夹
    /**
     * 文件通过fileList模板返回：dataset.fileinfo   
     * 文件夹返回：dataset.item
     */
    fileItemClick: function (e) {
        var page = this;
        let indexParam = getApp().globalData.indexParam;
        var dataset = e.currentTarget.dataset;
        var item;
        if (dataset.fileinfo) {
            item = dataset.fileinfo;  
        } else {
            item = dataset.item
        }

        var ownerId = item.ownerId;
        var nodeId = item.nodeId;
        var fileType = item.type;

        if (dataset.fileinfo) { 
            // 预览文件
            var file = {
                id: item.id,
                ownedBy: item.ownedBy,
                size: item.size,
                name: item.name,
            };

            getApp().globalData.imgUrls = page.data.previewImageUrls;
            File.openFile(file, function (musicState) {
                music.refreshPage(musicState.state, page);
                if (musicState.state == musicService.PLAY_STATE) {
                    music.listenPlayState(page);
                    getApp().globalData.isShowMusicPanel = true;
                    page.setData({
                        isShowMusicPanel: getApp().globalData.isShowMusicPanel
                    });
                }
            });

        } else {
            // 快捷文件夹
            wx.navigateTo({
                url: '/disk/widget/filelist?ownerId=' + ownerId + '&nodeId=' + nodeId + "&name=" + encodeURIComponent(dataset.item.nodeName)
            });
        }
    },

    cantShare: function () {
        wx.showModal({
            title: '提示',
            content: '该文件夹不能分享',
            complete: () => {
                return;
            }
        });

    },

    //打开手机右上角的转发事件
    onShareAppMessage: function (e) {
        var item = e.target.dataset.item;

        return {
            title: item.nodeName,
            path: '/disk/index?ownerId=' + item.ownerId + "&nodeId=" + item.nodeId + "&linkCode=" + item.linkCode + "&enterpriseId=" + getApp().globalData.enterpriseId,
            imageUrl: '/disk/images/shares/share-card-folder.png',
            success: function (res) {
                console.log("转发成功");
                // 转发成功
            },
            fail: function (res) {
                console.log("转发失败");
                // 转发失败
            }, complete: function () {
                console.log("转发结束");
            }
        }
    },

    saveToPersion: function (e) {
        var fileInfo = e.target.dataset.fileInfo;
        wx.navigateTo({
            url: 'save/saveToPersion?ownerId=' + fileInfo.ownerId + "&inodeId=" + fileInfo.nodeId,
        })
    },
    navigateToComms: function () {
        this.setData({
            commonsCount: 0,
            mCount: 0
        });
        wx.navigateTo({
            url: '/disk/comments/indexToComments/viewComments'
        })
    },

    navigateDocSpace: function (e) {
      switch (e.currentTarget.id) {
        case "personalFiles":
          wx.navigateTo({
            url: "/disk/widget/filelist?ownerId=" + getApp().globalData.cloudUserId + '&nodeId=0&name=' + encodeURIComponent("个人文件"),
          });
          break;
        case "departmentFiles":
          wx.navigateTo({
            url: "/disk/cloud/teamspacelist?type=1",
          })
          break;
        case "teamSpaceFiles":
          wx.navigateTo({
            url: "/disk/cloud/teamspacelist?type=0",
          })
          break;
        default:
          //this.navigateToFileList();
          break;
      }
    }
});

//获取最近浏览的数据
function getRecentFileList(page) {
    if (app.globalData.token == '' || app.globalData.cloudUserId == '') {
        return;
    }
    File.listLastReadFile(app.globalData.token, app.globalData.cloudUserId, function (data) {
        var files = translateRecentRecord(data, page);
        var data = {};

        files.splice(2, files.length - 3);  //只保留三个数据
        // for (var i = 0; i < files.length; i++) {  //文件列表添加左滑默认为0
        //     files[i].isTouchMove = false
        // }
        data.files = files;
        // if (data == page.data.data) return;
        page.setData({
            data: data,
            isClearOldDate: false,
            // currentPageIcon: false //不隐藏分页图标
        });
    });
}
//获取我的分享的数据
function getSharedByMeFileList(page) {
    File.listMySharetTo(function (data) {
        var pageCount = Math.ceil(data.totalCount / 10);//分页：需要分几页
        var files = translateShareRecord(data);
        if (files.length > 10) {  //如果当前页面数据大于分页数据，则显示分页图标
            page.setData({ currentPageIcon: false });
        } else {
            page.setData({ currentPageIcon: true });
        }
        var data = {};
        data.files = files;
        page.setData({
            data: data,
            isClearOldDate: false,
            pageCount: pageCount,
            currentPageIcon: false //不隐藏分页图标
        });
    });
}

//获取他人分享的数据;
function getSharedToMeFileList(page) {
    File.listShareToMe(function (data) {
        var pageCount = Math.ceil(data.totalCount / 10);//分页：需要分几页
        var files = translateShareRecord(data);
        if (files.length > 10) {  //如果当前页面数据大于分页数据，则显示分页图标
            page.setData({ currentPageIcon: false });
        } else {
            page.setData({ currentPageIcon: true });
        }
        var data = {};
        data.files = files;
        page.setData({
            pageCount: pageCount,
            data: data,
            isClearOldDate: false,
        });
    });
}

function getUnReadMessages(page) {

    //获取当前用户文件的·评论数量
    var status = 'UNREAD';// UNREAD  表示获取 “未读” 评论数量
    Message.theCountOfCommentMessages(status, (res) => {
        if (res.count !== undefined && res.count > 0) {
            page.setData({
                mCount: res.count,
                commonsCount: res.count,
                userAvatarUrl: res.users[0].headImageUrl  //默认去最新，第一条
            })
        } else {
            page.setData({
                commonsCount: 0
            })
        }
    });
}

//将最近浏览记录转换为内部数据
function translateRecentRecord(data, page) {
    var files = [];
    var previewImageUrlPromises = [];
    var tempFiles = File.convertFileList(data.files);   //获取图片下载地址
    page.data.previewImageUrls = tempFiles.previewImageUrls;    //不分页
    for (var i = 0; i < data.files.length; i++) {
        var row = data.files[i];
        var file = {};
        file.name = row.name;
        file.type = row.type;
        file.nodeId = row.id;
        file.ownerId = row.ownedBy;
        file.ownedBy = row.ownedBy;
        file.id = row.id;
        if (row.thumbnailUrlList.length == 0) {
            file.icon = utils.getImgSrc(row);
            file.shareIcon = utils.getImgSrcOfShareCard(row.name);
        } else {
            file.icon = utils.replacePortInDownloadUrl(row.thumbnailUrlList[0].thumbnailUrl);
            file.shareIcon = utils.replacePortInDownloadUrl(row.thumbnailUrlList[1].thumbnailUrl);
        }
        file.fileSize = utils.formatFileSize(row.size);
        file.modifiedTime = utils.formatNewestTime(row.modifiedAt);
        file.size = row.size;
        file.linkCode = row.linkCode;
        files.push(file);
    }

    return files;
}

//将最近分享记录（我分享的、分享给我的，都是同一格式）转换为内部数据
function translateShareRecord(data) {
    var files = [];
    for (var i = 0; i < data.contents.length; i++) {
        var row = data.contents[i];
        var file = {};
        file.name = row.name;
        if (typeof (row.originalType) != 'undefined') {
            file.type = row.originalType;
        } else {
            file.type = row.type;
        }
        if (typeof (row.originalNodeId) != 'undefined') {
            file.nodeId = row.originalNodeId;
        } else {
            file.nodeId = row.nodeId;
        }
        if (typeof (row.originalOwnerId) != 'undefined') {
            file.ownerId = row.originalOwnerId;
        } else {
            file.ownerId = row.ownerId;//兼容转发记录, 优先使用原始值
        }
        if (typeof (row.thumbnailUrlList) != 'undefined' && row.thumbnailUrlList.length != 0) {
            file.icon = utils.replacePortInDownloadUrl(row.thumbnailUrlList[0].thumbnailUrl);
            file.shareIcon = utils.replacePortInDownloadUrl(row.thumbnailUrlList[1].thumbnailUrl);
        } else {
            file.icon = utils.getImgSrc(row);
            file.shareIcon = utils.getImgSrcOfShareCard(file.name);
        }
        file.modifiedTime = utils.formatNewestTime(row.modifiedAt);
        file.ownerName = row.ownerName;
        file.linkCode = row.linkCode;
        file.shareType = row.shareType;
        file.createId = row.modifiedBy;
        file.desc = row.ownerName;
        file.iNodeId = row.iNodeId;
        file.sharedUserId = row.sharedUserId;

        //数据中包括外链和共享信息，共享设置分享类型为share，不能再次外发
        if (row.iNodeId == -1 && row.shareType == 'link') {
            file.icon = "/disk/images/icon/batch-file-icon.png";
        } else {
            file.shareType = "share";
        }

        files.push(file);
    }

    return files;
}

// 获取快捷文件夹目录
function getShortcutFolder(page) {
    var userId = getApp().globalData.userId;
    ViewedAndShortcutFolder.getShortcutFolder(userId, (res) => {
        for (let i = 0; i < res.length; i++) {
            let data = res[i];
            if (data.type === 1) {
                data.ownerName = '个人文件';
            } else {
                data.ownerName = data.ownerName;
            }
            Inbox.getInboxLink(data.ownerId, data.nodeId, (link) => {
                if (link.links.length < 1) {  // 无link
                    Inbox.setInboxLink(data.ownerId, data.nodeId, (linkInfo) => {
                        data.linkCode = linkInfo.id;
                    });
                } else {
                    data.linkCode = link.links[0].id;
                }

                res[i] = data;
                page.setData({
                    folderList: res
                })
            });

        }

    })
}

function getRobotCheck(page) {
    var timer;
    var repeat;
    setTimeout(function repeat() {
        timer = setTimeout(repeat, 60 * 1000);
        page.setData({
            isBackupsing: getApp().globalData.isOpenRobot
        });
        console.log('getApp().globalData.isOpenRobot: ', getApp().globalData.isOpenRobot);
    }, 30 * 1000);
}
