import Component from '../Component'
import { Modal, Input, Button, Select, Spin, message } from 'antd'
import { AppConfig } from 'config'
import ShareLinkInfo from './ShareLinkInfo'
import { getUserInfo } from 'utils'
import moment from 'moment'

const Option = Select.Option

const { TextArea } = Input

export default class SendShareLinkModal extends Component {
  state = {
    visible: false,
    linkRecord: null,
    userList: [],
    fetching: false,
    msg: '',
    value: [],
  }

  componentDidMount () {
    this.ds = this.http()
    this.value = []
    this.userMap = {}
  }

  show = (fileReocrd, linkRecord) => {
    this.fileRecord = fileReocrd
    this.setState({
      visible: true,
      linkRecord,
      userList: [],
      fetching: false,
      msg: '',
      value: [],
    })
  }

  hide = () => {
    this.setState({
      visible: false,
    })
  }

  getParams () {
    const linkRecord = this.state.linkRecord
    const msg = this.state.msg
    const sender = getUserInfo().loginName
    let params = [
      {
        name: 'message',
        value: msg,
      },
      {
        name: 'nodeName',
        value: this.fileRecord.name,
      },
      {
        name: 'sender',
        value: sender,
      },
      {
        name: 'linkUrl',
        value: `${AppConfig.ApiShareUrl}${linkRecord.id}`,
      },
    ]

    if (linkRecord.plainAccessCode) {
      params.push({
        name: 'plainAccessCode',
        value: linkRecord.plainAccessCode,
      })
    }

    if (linkRecord.effectiveAt && linkRecord.expireAt) {
      params.push({
        name: 'start',
        value: moment(linkRecord.effectiveAt).format('YYYY-MM-DD HH:mm'),
      })
      params.push({
        name: 'end',
        value: moment(linkRecord.expireAt).format('YYYY-MM-DD HH:mm'),
      })
    }

    let mailTo = []
    this.value.map((id) => {
      mailTo.push({ email: this.userMap[id] })
    })
    return JSON.stringify({
      type: 'link',
      mailTo,
      params,
    })
  }

  handleOk= () => {
    if (this.value.length === 0) {
      message.warning('请选择收件人')
      return
    }
    console.log(this.getParams())
    this.ds.mode = 'ecm'
    this.ds.url = '/mails'
    this.ds.data = this.getParams()
    this.ds.post(() => {
      message.info('发送外链成功')
      this.hide()
    })
  }

  handleCancel = () => {
    this.setState({
      visible: false,
    })
  }

  handleChange = (value) => {
    this.value = value
  }

  handleSelect = (value) => {
    this.state.value.push(value)
    this.setState({})
  }

  handleDeSelect = (value) => {
    this.state.value.splice(this.state.value.findIndex(v => v === value), 1)
    this.setState({})
  }

  handleTextArea = (e) => {
    this.setState({
      msg: e.target.value,
    })
  }

  handleSearch = (value) => {
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

      this.userMap = {}
      data.users.map((user) => {
        this.userMap[user.id] = user.email
      })
    })
  }

  render () {
    const { visible, linkRecord, userList, fetching, msg, value } = this.state
    const options = userList.map((item) =>
    item.email ?
      <Option key={item.id}>{`${item.name}(${item.email})`}</Option>
      : <Option key={item.id} disabled>{`${item.name}(${item.email || '未设置邮箱'})`}</Option>
    )
    return (
      <Modal
        width={600}
        title="发送外链"
        visible={visible}
        onCancel={this.handleCancel}
        footer={[
          <Button key="ok" type="primary" size="large" onClick={this.handleOk}>发送</Button>,
          <Button key="cancel" size="large" onClick={this.handleCancel}>取消</Button>,
        ]}
      >
        {linkRecord && <Input value={`${AppConfig.ApiShareUrl}${linkRecord.id}`} disabled />}
        {linkRecord && <ShareLinkInfo dataSource={linkRecord} />}
        <div style={{ lineHeight: '30px' }}>
          通过邮件发送外链
        </div>
        <Select
          mode="multiple"
          placeholder="请输入用户名、邮箱、或者群组名称、按回车"
          notFoundContent={fetching ? <Spin size="small" /> : null}
          onSelect={this.handleSelect}
          onDeselect={this.handleDeSelect}
          value={value}
          filterOption={false}
          onSearch={this.handleSearch}
          onChange={this.handleChange}
          style={{ width: '100%' }}
        >
          {options}
        </Select>
        <TextArea value={msg} placeholder="请输入消息" autosize={{ minRows: 10, maxRows: 10 }} style={{ marginTop: '10px' }} onChange={this.handleTextArea} />
      </Modal>
    )
  }
}
