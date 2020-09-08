var config = require("../config.js");
var session = require("../../session.js");
var httpclient = require("httpclient.js");

// // 获取最近浏览的三条信息
// function getFolderList(param, callback) {
//     var { ownerId, folderId, offset, LIMIT, linkCode } = param
//     var header = {
//         Authorization: getApp().globalData.token,
//         'content-type': 'application/json'
//     };
//     var data = {
//         'offset': offset,
//         'limit': LIMIT,
//         'order': [{ field: "type", direction: "ASC" }, { field: "modifiedAt", direction: "DESC" }],
//         'thumbnail': [{ width: 96, height: 96 }, { width: 250, height: 200 }]
//     }
//     return httpclient.post(config.host + '/ufm/api/v2/folders/' + ownerId + '/' + folderId + '/items', data, header, callback);
// }

// // 获取最近浏览的所有信息
// function getFolderList(param, callback) {
//     var { ownerId, folderId, offset, LIMIT, linkCode } = param
//     var header = {
//         Authorization: getApp().globalData.token,
//         'content-type': 'application/json'
//     };
//     var data = {
//         'offset': offset,
//         'limit': LIMIT,
//         'order': [{ field: "type", direction: "ASC" }, { field: "modifiedAt", direction: "DESC" }],
//         'thumbnail': [{ width: 96, height: 96 }, { width: 250, height: 200 }]
//     }
//     return httpclient.post(config.host + '/ufm/api/v2/folders/' + ownerId + '/' + folderId + '/items', data, header, callback);
// }

// 获取快捷文件夹的所有目录
function getShortcutFolder(userId, callback) {
    var header = {
        Authorization: getApp().globalData.token,
        'content-type': 'application/json'
    };
    return httpclient.post(config.host + '/ufm/api/v2/folders/' + userId + '/shortcut/list', {}, header, callback);
}

// 删除快捷文件夹
function deleteShortcutFolder(param, callback) {
    var { ownerId, rowId } = param
    var header = {
        Authorization: getApp().globalData.token,
        'content-type': 'application/json'
    };

    return httpclient.remove(config.host + '/ufm/api/v2/folders/' + ownerId + '/shortcut/' + rowId, {}, header, callback);
}

module.exports = {
    getShortcutFolder,
    deleteShortcutFolder
};
