import { AdminFindLayout,
  Component,
  Breadcrumb,
  FileVersionModal,
  MoveAndCopyModal,
  SaveToTeamSpaceModal,
  CreateShareLinkModal,
  ShareLinkModal,
  SendShareLinkModal,
  ShareModal,
  ContextMenu } from 'wolf'
import { Modal, message } from 'antd'
import { getUserInfo } from 'utils'
import AllfileDataGrid from './AllfileDataGrid'
import ToolBar from './ToolBar'

const confirm = Modal.confirm

/**
 * 用户空间UI
 */
export default class Userspace extends Component {
  constructor (props) {
    super(props)
    this.state = {
      dataSource: [],
      selectedRecords: [],
      navList: [],
      togStyle: 0,
      parent: 0,
      editable: false,
    }
  }

  componentDidMount () {
    this.cloudUserId = getUserInfo().cloudUserId
    this.ds = this.http()
    this.getData(0)
  }

  getData = (id, sortid, callback) => {
    this.ds.url = `/folders/${this.cloudUserId}/${id}/items`
    this.ds.data = {}
    if (sortid) {
      this.ds.data = JSON.stringify({ order: [{ field: sortid, direction: 'DESC' }] })
    }
    this.ds.post((data) => {
      if (callback) {
        callback()
      }
      this.selectedRecords = []
      this.setState({
        selectedRecords: [],
        dataSource: [...data.folders, ...data.files],
      })
    })
    this.state.parent = id
  }

  getSearchData = (value) => {
    this.ds.url = `/nodes/${this.cloudUserId}/search`
    if (value) {
      this.ds.data = JSON.stringify({ name: value })
    } else {
      this.ds.data = JSON.stringify({ name: '*' })
    }
    this.ds.post((data) => {
      this.setState({
        navList: [],
        dataSource: [...data.folders, ...data.files],
      })
    })
  }

  selectedFiles = (files) => {
    this.setState({
      selectedRecords: [...files],
    })
  }

  togDgStyle =(num) => {
    if (this.olddata) {
      this.olddata.editable = false
    }
    this.setState({
      editable: false,
      togStyle: num,
    })
  }

  onRowClick = (row) => {
    if (this.olddata) {
      this.olddata.editable = false
    }
    this.state.editable = false
    if (row.type === 0) {
      this.getData(row.id, null, () => {
        this.getFilePath(row)
      })
    }
  }

  /**
   * 获取文件目录
   * @param record
   */
  getFilePath = (record) => {
    this.ds.url = `/nodes/${record.ownedBy}/${record.id}/path`
    this.ds.data = {}
    this.ds.get((data) => {
      data.reverse()
      this.state.navList = [...data]
      this.state.navList.push(record)
      this.setState({})
    })
  }
  /**
   * 返回上一级目录
   * @param e
   */
  onBack = () => {
    this.state.navList.pop()
    let id = 0
    if (this.state.navList.length > 0) {
      id = this.state.navList[this.state.navList.length - 1].id
    }
    this.getData(id)
  }

  /**
   * 目录跳转
   * @param index
   * @param e
   */
  onBreadcrumbItemClick = (index, e) => {
    this.refs.datagrid.clearSelected()
    let id = 0
    if (index === 0) {
      this.state.navList = []
    } else {
      this.state.navList.splice(index)
      id = this.state.navList[index - 1].id
    }
    this.getData(id)
  }

  /**
   * 编辑表格
   * @param record
   */
  editRecord = (record) => {
    if (this.olddata) {
      this.olddata.editable = false
    }

    this.state.editable = true
    record.editable = true
    this.olddata = record
    this.setState({ selectedRowKeys: [] })
  }
  /**
   * 文件删除
   */
  fileDelete = (record) => {
    return new Promise((resolve) => {
      if (record.type === 1) {
        this.ds.url = `/files/${record.ownedBy}/${record.id}`
      } else {
        this.ds.url = `/folders/${record.ownedBy}/${record.id}`
      }
      this.ds.data = {}
      this.ds.delete(() => resolve())
    })
  }
  /**
   * 删除记录
   * @param record
   */
  deleteRecord = (record) => {
    const self = this
    const parent = self.state.parent
    confirm({
      title: '系统提示',
      content: '确认要删除吗',
      okText: '确定',
      okType: 'danger',
      cancelText: '取消',
      onOk () {
        if (Array.isArray(record)) {
          let filesDelete = []
          record.map(item => filesDelete.push(self.fileDelete(item)))
          Promise.all(filesDelete).then(() => {
            message.info('删除成功')
            self.getData(parent)
          })
        } else {
          self.fileDelete(record).then(() => {
            message.info('删除成功')
            self.getData(parent)
          })
        }
      },
    })
  }

  /**
   * 重命名
   * @param record
   * @param newValue
   */
  updateRecord = (record, newValue) => {
    this.state.editable = false
    record.editable = false

    if (newValue && newValue !== record.name) {
      record.name = newValue
      if (record.type === 1) {
        this.ds.url = `/files/${record.ownedBy}/${record.id}`
      } else {
        this.ds.url = `/folders/${record.ownedBy}/${record.id}`
      }
      this.ds.data = JSON.stringify({ name: newValue })
      this.ds.put(() => {
        this.setState({})
      })
    } else {
      this.setState({})
    }
  }

  /**
   * 显示文件版本列表
   * @param record
   */
  showFileVersion = (record) => {
    this.refs.fileVersionModal.show(record)
    this.ds.url = `/files/${record.ownedBy}/${record.id}/versions`
    this.ds.get((data) => {
      console.log(data);
      this.setState({
        fileVersionData: data.versions,
      })
    })
    this.refs.fileVersionModal.show(record.name)
  }

  /**
   * 显示复制移动窗口
   * @param record
   */
  moveAndCopyRecord = (record) => {
    if (Array.isArray(record)) {
      this.selectedRecords = [...record]
    } else {
      this.selectedRecords = []
      this.selectedRecords.push(record)
    }
    this.refs.moveAndCopyModal.show()
  }

  /**
   * 显示转发到团队空间窗口
   * @param record
   */
  saveToTeamSpaceRecord = (record) => {
    if (Array.isArray(record)) {
      this.selectedRecords = [...record]
    } else {
      this.selectedRecords = []
      this.selectedRecords.push(record)
    }
    this.refs.saveToTeamSpaceModal.show()
  }

  /**
   * 查询
   * @param value 关键字
   */
  handleSearch = (value) => {
    this.getSearchData(value)
  }

  /**
   * 查询取消
   */
  handleSearchCancel = () => {
    this.getData(0)
    this.setState({
      navList: [],
    })
  }
  /**
   * 上传
   */
  handleUpload = () => {
    console.log('上传');
  }

  /**
   * 创建文件夹
   */
  handleNewFolder = () => {
    this.state.editable = false
    if (this.olddata) {
      this.olddata.editable = false
    }
    const items = this.state.dataSource.filter((item) => {
      return /^(新建文件夹){1}\(+\d+\)+$/.test(item.name)
        || /^新建文件夹$/.test(item.name)
    })
    let newName
    if (items.length === 0) {
      newName = '新建文件夹'
    } else if (items.length === 1 && items[0].name === '新建文件夹') {
      newName = '新建文件夹(2)'
    } else {
      const numbers = []
      items.map((item) => {
        if (item.name !== '新建文件夹') {
          let number = item.name.match(/\d+/gi)
          numbers.push(parseInt(number[0]))
        }
      })
      const max = numbers.sort((a, b) => { return b - a })[0] + 1
      newName = `新建文件夹(${max})`
    }

    this.ds.url = `/folders/${this.cloudUserId}/`
    this.ds.data = JSON.stringify({ name: newName, parent: this.state.parent })
    this.ds.post((data) => {
      this.olddata = data
      this.state.editable = true
      data.editable = true
      this.state.dataSource.unshift(data)
      this.setState({})
    })
  }

  /**
   * 排序
   * @param sortid
   */
  handleSort = (sortid) => {
    this.getData(this.state.parent, sortid)
  }

  /**
   * 文件移动
   * @param record
   * @param destParent
   * @param destOwnerId
   */
  fileMove = (record, destParent, destOwnerId) => {
    return new Promise((resolve) => {
      if (record.type === 1) {
        this.ds.url = `/files/${record.ownedBy}/${record.id}/move`
      } else if (record.type === 0) {
        this.ds.url = `/folders/${record.ownedBy}/${record.id}/move`
      }
      this.ds.data = JSON.stringify({ destParent, destOwnerId, autoRename: true })
      this.ds.put(() => resolve())
    })
  }

  /**
   * 移动
   * @param folderId
   */
  onMoveClick = (folderId) => {
    let filesMove = []
    this.selectedRecords.map(record => filesMove.push(this.fileMove(record, folderId, this.cloudUserId)))
    Promise.all(filesMove).then(() => {
      message.info('移动成功')
      this.getData(this.state.parent)
    })
    this.refs.moveAndCopyModal.hide()
  }

  /**
   * 文件复制
   * @param record
   * @param destParent
   * @param destOwnerId
   */
  fileCopy = (record, destParent, destOwnerId) => {
    return new Promise((resolve) => {
      if (record.type === 1) {
        this.ds.url = `/files/${record.ownedBy}/${record.id}/copy`
      } else if (record.type === 0) {
        this.ds.url = `/folders/${record.ownedBy}/${record.id}/copy`
      }
      this.ds.data = JSON.stringify({ destParent, destOwnerId, autoRename: true })
      this.ds.put(() => resolve())
    })
  }

  /**
   * 文件复制
   * @param folderId
   */
  onCopyClick = (folderId) => {
    let filesCopy = []
    this.selectedRecords.map(record => filesCopy.push(this.fileCopy(record, folderId, this.cloudUserId)))
    Promise.all(filesCopy).then(() => {
      message.info('复制成功')
    })
    this.refs.moveAndCopyModal.hide()
  }

  /**
   * 转发到团队空间
   */
  onSaveToTeamSpaceClick = (teamSpaceId, folderId) => {
    let filesCopy = []
    this.selectedRecords.map(record => filesCopy.push(this.fileCopy(record, folderId, teamSpaceId)))
    Promise.all(filesCopy).then(() => message.info('转发到团队空间成功'))
    this.refs.saveToTeamSpaceModal.hide()
  }

  /**
   * 分享
   * @param record
   */
  handleShare = (record) => {
    this.refs.shareModal.show(record)
  }

  /**
   * 链接
   * @param record
   */
  onLinkClick = (record) => {
    if (record.isSharelink) {
      this.refs.shareLinkModal.show(record)
    } else {
      this.refs.createShareLinkModal.show(record)
    }
  }
  onContextMenu = (row, e) => {
    this.refs.datagrid.clearOverkey()
    this.refs.contextMenu.show(row, e)
  }

  render () {
    const { selectedRecords } = this.state
    return (
      <AdminFindLayout topHeight={80} vspace={0}>
        <div style={{ marginLeft: 10, marginTop: 5 }}>
          <ToolBar
            selectedRecords={selectedRecords}
            togDgStyle={this.togDgStyle}
            editRecord={this.editRecord}
            deleteRecord={this.deleteRecord}
            moveAndCopyRecord={this.moveAndCopyRecord}
            saveToTeamSpaceRecord={this.saveToTeamSpaceRecord}
            showFileVersion={this.showFileVersion}
            onShare={this.handleShare}
            onUpload={this.handleUpload}
            onSearch={this.handleSearch}
            onCancel={this.handleSearchCancel}
            onNewFolder={this.handleNewFolder}
            onSort={this.handleSort}
          />
          <div style={{ lineHeight: '40px' }}>
            <Breadcrumb
              dataSource={this.state.navList}
              defaultName="个人空间"
              onBack={this.onBack}
              onClick={this.onBreadcrumbItemClick}
            />
            {selectedRecords.length > 0 &&
            <span style={{ marginLeft: 16 }}>已选中：{selectedRecords.length} 个文件/文件夹</span>
            }
          </div>
          <MoveAndCopyModal ref="moveAndCopyModal"
            onCreateFolderClick={this.onCreateFolderClick}
            onCopyClick={this.onCopyClick}
            onMoveClick={this.onMoveClick}
          />
          <SaveToTeamSpaceModal ref="saveToTeamSpaceModal"
            onSaveClick={this.onSaveToTeamSpaceClick}
          />
          <CreateShareLinkModal ref="createShareLinkModal"
            onSuccess={() => this.getData(this.state.parent)}
            onOk={record => this.refs.shareLinkModal.show(record)}
          />
          <ShareLinkModal ref="shareLinkModal"
            onSuccess={() => this.getData(this.state.parent)}
            onOk={linkRecord => this.refs.createShareLinkModal.show(linkRecord)}
            onSendLink={(record, linkRecord) => this.refs.sendShareLinkModal.show(record, linkRecord)}
            onUpdate={(record, linkRecord) => this.refs.createShareLinkModal.show(record, linkRecord, 'UPDATE')}
          />
          <FileVersionModal ref="fileVersionModal" />
          <SendShareLinkModal ref="sendShareLinkModal" />
          <ShareModal ref="shareModal" />
          <ContextMenu ref="contextMenu"
            shareRecord={this.handleShare}
            editRecord={this.editRecord}
            deleteRecord={this.deleteRecord}
            moveAndCopyRecord={this.moveAndCopyRecord}
            saveToTeamSpaceRecord={this.saveToTeamSpaceRecord}
            showFileVersion={this.showFileVersion}
          />
        </div>
        <AllfileDataGrid
          ref="datagrid"
          editable={this.state.editable}
          togStyle={this.state.togStyle}
          dataSource={this.state.dataSource}
          selectedFiles={this.selectedFiles}
          onContextMenu={this.onContextMenu}
          onRowClick={this.onRowClick}
          onShareClick={this.handleShare}
          onLinkClick={this.onLinkClick}
          shareRecord={this.handleShare}
          editRecord={this.editRecord}
          deleteRecord={this.deleteRecord}
          updateRecord={this.updateRecord}
          moveAndCopyRecord={this.moveAndCopyRecord}
          saveToTeamSpaceRecord={this.saveToTeamSpaceRecord}
          showFileVersion={this.showFileVersion}
        />
      </AdminFindLayout>
    )
  }
}
