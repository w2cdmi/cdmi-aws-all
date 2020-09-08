import Component from '../Component'
import { Modal, Button, Select, Checkbox, Tree, Row, Col, Spin, Icon, message } from 'antd'
import { AppConfig } from 'config'

const Option = Select.Option
const TreeNode = Tree.TreeNode
/**
 * 文件/文件夹共享
 */
export default class ShareModal extends Component {
  state = {
    visible: false,
    fetching: false,
    fileType: 0,
    selectedList: [],
    userList: [],
    userCount: 0,
    selectedUserId: -1,
    deleteingUserId: -1,
    treeData: [],
    isDownloader: true,
    isUploader: false,
  }

  componentDidMount () {
    this.ds = this.http()
    this.loadDeptList()
  }

  show = (record) => {
    this.setState({
      visible: true,
      fetching: false,
      fileType: record.type,
      selectedList: [],
      userList: [],
      userCount: 0,
      selectedUserId: -1,
      deleteingUserId: -1,
      isDownloader: true,
      isUploader: false,
    })
    this.record = record

    this.loadSelectedList()
  }

  loadDeptList () {
    this.ds.mode = 'ecm'
    this.ds.url = '/users/listDept'
    this.ds.get((data) => {
      data.map(item => item.key = item.id)
      this.setState({
        treeData: data,
      })
    })
  }

  loadDeptAndUsers (deptId, callback) {
    this.ds.mode = 'ecm'
    this.ds.url = '/users/listDepAndUsers'
    this.ds.data = JSON.stringify({ deptId })
    this.ds.post(data => callback(data))
  }


  loadSelectedList () {
    this.ds.mode = 'ufm'
    this.ds.url = `/shareships/${this.record.ownedBy}/${this.record.id}`
    this.ds.data = {}
    this.ds.get((data) => {
      this.setState({
        userCount: data.totalCount,
        selectedList: data.contents,
      })
    })
  }

  setShareships (sharedUserId, sharedUserName, record, success) {
    const roleName = this.getRole()
    this.ds.mode = 'ufm'
    this.ds.url = `/shareships/${this.record.ownedBy}/${this.record.id}`
    this.ds.data = JSON.stringify({ sharedUser: { id: sharedUserId, type: 'user' }, roleName })
    this.ds.put(() => {
      if (success) {
        success()
      }
      if (record) {
        record.roleName = roleName
      } else {
        this.state.selectedList.push({ sharedUserId, sharedUserName, roleName })
      }
      this.setState({ selectedUserId: -1 })
    })
  }

  removeShareships (sharedUserId, index) {
    this.setState({
      deleteingUserId: sharedUserId,
    })
    this.ds.mode = 'ufm'
    this.ds.url = `/shareships/${this.record.ownedBy}/${this.record.id}?userId=${sharedUserId}&type=user`
    this.ds.data = null
    this.ds.delete(() => {
      this.state.selectedList.splice(index, 1)
      if (this.state.selectedUserId === sharedUserId) {
        this.setState({ selectedUserId: -1 })
      }
      this.setState({ deleteingUserId: -1 })
    }, () => this.setState({ deleteingUserId: -1 }))
  }

  handleSave = () => {
    this.setShareships(this.selectedUser.sharedUserId,
      this.selectedUser.sharedUserName,
      this.selectedUser,
      () => message.info('保存成功'))
  }

  handleCancel = () => {
    this.setState({
      visible: false,
    })
  }

  getRole () {
    const { isDownloader, isUploader } = this.state
    if (isUploader && isDownloader) {
      return 'uploadAndView'
    }
    if (isUploader && !isDownloader) {
      return 'uploader'
    }
    if (!isUploader && isDownloader) {
      return 'downLoader'
    }
    return 'previewer'
  }

  handleItemClick = (record) => {
    const roleName = record.roleName
    if (roleName === 'uploadAndView') {
      this.state.isDownloader = true
      this.state.isUploader = true
    } else if (roleName === 'uploader') {
      this.state.isDownloader = false
      this.state.isUploader = true
    } else if (roleName === 'downLoader') {
      this.state.isDownloader = true
      this.state.isUploader = false
    } else if (roleName === 'previewer') {
      this.state.isDownloader = false
      this.state.isUploader = false
    }
    this.selectedUser = record
    this.setState({ selectedUserId: record.sharedUserId })
  }

  handleItemRemove = (record, index, e) => {
    e.stopPropagation()
    this.removeShareships(record.sharedUserId, index)
  }

  addToSelectedList (sharedUserId, sharedUserName) {
    if (this.state.selectedList.findIndex(value => value.sharedUserId == sharedUserId) !== -1) {
      return
    }
    this.setShareships(sharedUserId, sharedUserName)
  }

  handleTreeSelect = (selectedKeys, e) => {
    const record = e.node.props.dataRef
    if (record.type === 'user') {
      this.addToSelectedList(record.id, e.node.props.title)
    } else if (record.type === 'department') {
      this.loadDeptAndUsers(record.id, (data) => {
        data.map((item) => {
          if (item.type === 'user') {
            this.addToSelectedList(item.id, (item.alias || item.name))
          }
        })
      })
    }
  }

  handleUserSelect = (value) => {
    this.addToSelectedList(value.key, value.label)
  }

  handleUserSearch = (value) => {
    if (!value.trim()) {
      return
    }
    this.setState({
      fetching: true,
    })
    this.ds.mode = 'ecm'
    this.ds.url = '/users/search'
    this.ds.data = JSON.stringify({ appId: AppConfig.AppId, keyword: value })
    this.ds.post((data) => {
      this.setState({
        userList: data.users,
        fetching: false,
      })
    })
  }

  handleRoleChange = (e) => {
    if (e.target.value === 'downloader') {
      this.setState({ isDownloader: e.target.checked })
    } else if (e.target.value === 'uploader') {
      this.setState({ isUploader: e.target.checked })
    }
  }

  onLoadData = (treeNode) => {
    return new Promise((resolve) => {
      if (treeNode.props.children) {
        resolve()
        return
      }
      const deptId = treeNode.props.dataRef.id

      this.ds.mode = 'ecm'
      this.ds.url = '/users/listDepAndUsers'
      this.ds.data = JSON.stringify({ deptId })
      this.ds.post((data) => {
        data.map(item => item.key = item.id)
        treeNode.props.dataRef.children = data
        this.setState({
          treeData: [...this.state.treeData],
        })
        resolve()
      })
    })
  }

  renderTreeNodes = (data) => {
    return data.map((item) => {
      if (item.children) {
        return (
          <TreeNode title={item.alias || item.name} key={item.key} dataRef={item}>
            {this.renderTreeNodes(item.children)}
          </TreeNode>
        )
      }
      return <TreeNode title={item.alias || item.name} key={item.key} dataRef={item} />
    })
  }

  render () {
    const { visible, fetching, fileType, selectedList, userList, selectedUserId, deleteingUserId, treeData, isDownloader, isUploader } = this.state
    const options = userList.map(item =>
      <Option key={item.cloudUserId}>{item.name}</Option>
    )
    return (
      <Modal
        title="共享"
        visible={visible}
        width={600}
        onCancel={this.handleCancel}
        footer={[
          <Button key="ok" size="large" onClick={this.handleSave} type="primary" disabled={selectedUserId === -1}>保存</Button>,
          <Button key="cancel" size="large" onClick={this.handleCancel}>取消</Button>,
        ]}
      >
        <Row>
          <Col span={24}>
            <Select
              mode="multiple"
              placeholder="查询用户"
              notFoundContent={fetching ? <Spin size="small" /> : null}
              labelInValue
              filterOption={false}
              onSelect={this.handleUserSelect}
              onSearch={this.handleUserSearch}
              style={{ width: '100%' }}
            >
              {options}
            </Select>
          </Col>
        </Row>

        <Row gutter={10} style={{ marginTop: 10 }}>
          <Col span={12}>
            <div>部门/成员选择</div>
            <div style={{ height: '300px', border: '1px solid #ccc', overflow: 'auto' }}>
              <Tree loadData={this.onLoadData} onSelect={this.handleTreeSelect}>
                {this.renderTreeNodes(treeData)}
              </Tree>
            </div>
          </Col>
          <Col span={12}>
            <div>已共享成员（{selectedList.length}）<Icon type="exclamation-circle" />双击移除</div>
            {selectedList.length > 0 ?
              <ul style={{ height: '300px', border: '1px solid #ccc', overflow: 'auto' }}>
                {selectedList.map((item, index) =>
                  (<li className={item.sharedUserId === selectedUserId ? 'list-item list-item-checked' : 'list-item'}
                    key={index}
                    onClick={() => this.handleItemClick(item)}
                    onDoubleClick={e => this.handleItemRemove(item, index, e)}
                  >
                    <span style={{ fontWeight: 'bold' }}>{item.sharedUserName}</span>
                    <Button onClick={e => this.handleItemRemove(item, index, e)} size="small" style={{ float: 'right', marginTop: 4, marginLeft: 5 }} disabled={deleteingUserId !== -1} loading={item.sharedUserId === deleteingUserId}>删除</Button>
                    {item.roleName === 'previewer' &&
                      <span style={{ float: 'right' }}>权限：预览</span>
                    }
                    {item.roleName === 'downLoader' &&
                      <span style={{ float: 'right' }}>权限：预览 下载</span>
                    }
                    {item.roleName === 'uploader' &&
                      <span style={{ float: 'right' }}>权限：预览 上传</span>
                    }
                    {item.roleName === 'uploadAndView' &&
                      <span style={{ float: 'right' }}>权限：预览 下载 上传</span>
                    }
                  </li>)
                )
                }
              </ul> : <div style={{ height: '300px', border: '1px solid #ccc', overflow: 'auto', padding: 5, color: '#ccc' }}>请选择部门或成员</div>
            }
          </Col>
        </Row>

        <Row style={{ marginTop: 10 }}>
          <Col span={24}>
            <div>设置权限</div>
            <div>
              <Checkbox value="previewer" checked readOnly>预览</Checkbox>
              <Checkbox value="downloader" checked={isDownloader} onChange={this.handleRoleChange}>下载</Checkbox>
              {fileType === 0 &&
                <Checkbox value="uploader" checked={isUploader} onChange={this.handleRoleChange}>上传</Checkbox>
              }
            </div>
          </Col>
        </Row>
      </Modal>
    )
  }
}
