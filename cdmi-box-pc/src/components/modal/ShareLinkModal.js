import Component from '../Component'
import { Modal, Input, Button, message } from 'antd'
import { AppConfig } from 'config'
import copy from 'copy-to-clipboard'
import ShareLinkInfo from "./ShareLinkInfo"

const confirm = Modal.confirm

export default class ShareLinkModal extends Component {
  state = {
    visible: false,
    linkList: [],
  }

  componentDidMount () {
    this.ds = this.http()
  }

  show = (record) => {
    this.setState({
      visible: true,
    })
    this.record = record
    this.ownerId = record.ownedBy
    this.nodeId = record.id
    this.getLinkList()
  }

  getLinkList = () => {
    this.ds.url = `/nodes/${this.ownerId}/${this.nodeId}/links`
    this.ds.get((data) => {
      this.setState({
        linkList: [...data.links],
      })
    })
  }

  hide = () => {
    this.setState({
      visible: false,
    })
  }

  handleOk = () => {
    if (this.state.linkList.length === 3) {
      message.warning('最多创建3个分享链接')
      return
    }

    this.setState({ visible: false })

    if (this.props.onOk) {
      this.props.onOk(this.record)
    }
  }

  handleCancel = () => {
    this.hide()
  }

  /**
   * 更新链接
   */
  updateLink (linkRecord) {
    this.hide()
    if (this.props.onUpdate) {
      this.props.onUpdate(this.record, linkRecord)
    }
  }

  /**
   * 发送链接
   */
  sendLink (linkRecord) {
    if (this.props.onSendLink) {
      this.props.onSendLink(this.record, linkRecord)
    }
  }

  /**
   * 复制链接
   */
  copyLink (linkRecord) {
    if (linkRecord.plainAccessCode) {
      copy(`链接：${AppConfig.ApiShareUrl}${linkRecord.id} 提取码：${linkRecord.plainAccessCode}`)
    } else {
      copy(`链接：${AppConfig.ApiShareUrl}${linkRecord.id}`)
    }
    message.info('复制到剪贴板成功')
  }

  /**
   * 删除链接
   */
  deleteLink (record) {
    const self = this
    confirm({
      title: '系统提示',
      content: '确认要删除吗',
      okText: '确定',
      okType: 'danger',
      cancelText: '取消',
      onOk () {
        self.ds.url = `/links/${self.ownerId}/${self.nodeId}?linkCode=${record.id}`
        self.ds.delete(() => {
          self.getLinkList()
          if (self.props.onSuccess) {
            self.props.onSuccess()
          }
        })
      },
      onCancel () {

      },
    })
  }

  render () {
    const { visible, linkList } = this.state

    return (
      <Modal
        width={600}
        title="分享链接"
        visible={visible}
        onCancel={this.handleCancel}
        footer={[
          <Button key="ok" type="primary" size="large" onClick={this.handleOk}>创建分享链接</Button>,
          <Button key="cancel" size="large" onClick={this.handleCancel}>取消</Button>,
        ]}
      >
        <p>最多创建3个分享链接</p>
        {linkList.map(item =>
          (<div key={item.id} style={{ marginTop: '10px' }}>
            <Input value={`${AppConfig.ApiShareUrl}${item.id}`} style={{ width: '60%' }} disabled />
            <Button.Group size="small" style={{ marginLeft: '10px' }}>
              <Button onClick={() => this.updateLink(item)}>修改</Button>
              <Button onClick={() => this.copyLink(item)}>复制</Button>
              <Button onClick={() => this.sendLink(item)}>发送</Button>
              <Button onClick={() => this.deleteLink(item)}>删除</Button>
            </Button.Group>
            <ShareLinkInfo dataSource={item} />
          </div>)
        )}
      </Modal>
    )
  }
}
