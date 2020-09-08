import Component from '../Component'
import { Modal, Form, Input, Checkbox, Select, Switch, Button, DatePicker, message } from 'antd'
import moment from 'moment'
import { randomCode, validateEmail } from 'utils'
import { AppConfig } from 'config'

const FormItem = Form.Item
const Option = Select.Option
const { RangePicker } = DatePicker

const formItemLayout = {
  labelCol: { span: 4 },
  wrapperCol: { span: 20 },
}

/**
 * 创建分享链接Modal
 */
export default class CreateShareLinkModal extends Component {
  state = {
    visible: false,
    isCode: false,
    isDynamicCode: false,
    validDateType: '0',
    fileType: 0,
    downloaderChecked: true,
    previewerChecked: true,
    uploaderChecked: false,
    rangePickerValue: [moment(moment(), 'YYYY-MM-DD HH:mm'), moment(moment(), 'YYYY-MM-DD HH:mm')],
    shareLink: '',
    linkCode: '',
    plainAccessCode: '',
    accessCodeMode: 'static', // static, mail
    effectiveAt: 0,
    expireAt: 0,
    identity: '', // mail地址
  }

  componentDidMount () {
    this.ds = this.http()
  }

  show = (record, linkRecord, ACTION) => {
    this.setState({
      visible: true,
      isCode: false,
      isDynamicCode: false,
      validDateType: '0',
      fileType: record.type,
      downloaderChecked: true,
      previewerChecked: true,
      uploaderChecked: false,
      rangePickerValue: [moment(moment(), 'YYYY-MM-DD HH:mm'), moment(moment(), 'YYYY-MM-DD HH:mm')],
      shareLink: '',
      linkCode: '',
      plainAccessCode: '',
      accessCodeMode: 'static', // static, mail
      effectiveAt: 0,
      expireAt: 0,
      identity: '', // mail地址
    })

    this.record = record
    this.ownerId = record.ownedBy
    this.nodeId = record.id
    this.action = ACTION

    if (ACTION === 'UPDATE') {
      this.ds.url = `/links/${this.ownerId}/${this.nodeId}?linkCode=${linkRecord.id}`
      this.ds.data = {}
      this.ds.get((data) => {
        let plainAccessCode = data.plainAccessCode || null
        let accessCodeMode = data.accessCodeMode
        let effectiveAt = data.effectiveAt || 0
        let expireAt = data.expireAt || 0
        let identity = null // data.identities[0].identity
        let role = data.role
        if (role === 'viewer') {
          this.setState({
            downloaderChecked: true,
            previewerChecked: true,
            uploaderChecked: false,
          })
        } else if (role === 'previewer') {
          this.setState({
            downloaderChecked: false,
            previewerChecked: true,
            uploaderChecked: false,
          })
        } else if (role === 'uploader') {
          this.setState({
            downloaderChecked: false,
            previewerChecked: false,
            uploaderChecked: true,
          })
        } else if (role === 'uploadAndView') {
          this.setState({
            downloaderChecked: true,
            previewerChecked: true,
            uploaderChecked: true,
          })
        }

        if ((accessCodeMode === 'static' && plainAccessCode) || accessCodeMode === 'mail') {
          this.setState({
            isCode: true,
          })
        }
        if (accessCodeMode === 'mail') {
          this.setState({
            isDynamicCode: true,
          })
        }

        if (data.identities && data.identities.length === 1) {
          identity = data.identities[0].identity
        }

        if (effectiveAt === 0 && expireAt === 0) {
          this.setState({
            validDateType: '0',
          })
        } else {
          let diffDays = moment(expireAt).diff(moment(effectiveAt), 'days')
          const optionsDays = [1, 3, 7, 30]
          if (optionsDays.includes(diffDays)) {
            this.setState({
              validDateType: `${diffDays}`,
            })
          } else {
            this.setState({
              validDateType: '-1',
              rangePickerValue: [moment(moment(effectiveAt), 'YYYY-MM-DD HH:mm'), moment(moment(expireAt), 'YYYY-MM-DD HH:mm')],
            })
          }
        }

        this.setState({
          linkCode: data.id,
          shareLink: AppConfig.ApiShareUrl + data.id,
          plainAccessCode,
          accessCodeMode,
          effectiveAt,
          expireAt,
          identity,
        })
      })
    } else {
      this.ds.url = `/links/${this.ownerId}/${this.nodeId}`
      this.ds.data = JSON.stringify({ anon: true, accessCodeMode: 'static' })
      this.ds.post((data) => {
        this.setState({
          linkCode: data.id,
          shareLink: AppConfig.ApiShareUrl + data.id,
        })

        if (this.props.onSuccess) {
          this.props.onSuccess()
        }
      })
    }
  }

  /**
   * 获取权限
   * @returns {*}
   */
  getRole () {
    // viewer, previewer, uploader, uploadAndView
    if (!this.state.downloaderChecked
      && !this.state.previewerChecked
      && !this.state.uploaderChecked) {
      return null
    }

    if (this.state.downloaderChecked
      && !this.state.uploaderChecked) {
      return 'viewer'
    }

    if (!this.state.downloaderChecked
      && this.state.previewerChecked
      && !this.state.uploaderChecked) {
      return 'previewer'
    }

    if (!this.state.downloaderChecked
      && !this.state.previewerChecked
      && this.state.uploaderChecked) {
      return 'uploader'
    }

    if (this.state.previewerChecked
      && this.state.uploaderChecked) {
      return 'uploadAndView'
    }
  }

  handleOk = () => {
    let role = this.getRole()
    if (!role) {
      message.warning('请选择访问权限')
      return
    }
    if (this.state.isDynamicCode) {
      if (!this.state.identity.trim()) {
        message.warning('请输入邮箱地址')
        return
      }
      if (!validateEmail(this.state.identity.trim())) {
        message.warning('请输入正确的邮箱地址')
        return
      }
    }
    let plainAccessCode = this.state.plainAccessCode
    let accessCodeMode = this.state.accessCodeMode
    let identities = []
    let effectiveAt = this.state.effectiveAt
    let expireAt = this.state.expireAt

    if (this.state.isDynamicCode) {
      identities = [{ identity: this.state.identity }]
    }

    if (effectiveAt === 0) {
      effectiveAt = null
    }

    if (expireAt === 0) {
      expireAt = null
    }

    this.ds.url = `/links/${this.ownerId}/${this.nodeId}?linkCode=${this.state.linkCode}`
    this.ds.data = JSON.stringify({
      plainAccessCode,
      accessCodeMode,
      role,
      identities,
      effectiveAt,
      expireAt,
      anon: true,
    })
    this.ds.put(() => {
      this.setState({ visible: false })
      if (this.props.onOk) {
        this.props.onOk(this.record)
      }
    })

    console.log(`linkCode: ${this.state.linkCode}`)
    console.log(`plainAccessCode: ${this.state.plainAccessCode}`)
    console.log(`accessCodeMode: ${this.state.accessCodeMode}`)
    console.log(`role: ${role}`)
    console.log(`identity: ${this.state.identity}`)
    console.log(`effectiveAt: ${this.state.effectiveAt} ${moment(this.state.effectiveAt).format('YYYY-MM-DD HH:mm')}`)
    console.log(`expireAt: ${this.state.expireAt} ${moment(this.state.expireAt).format('YYYY-MM-DD HH:mm')}`)
  }

  handleCancel = () => {
    this.setState({
      visible: false,
    })
    if (this.action !== 'UPDATE') {
      this.ds.url = `/links/${this.ownerId}/${this.nodeId}?linkCode=${this.state.linkCode}`
      this.ds.delete(() => {
        if (this.props.onSuccess) {
          this.props.onSuccess()
        }
      })
    }
  }

  handleRoleChange = (e) => {
    if (e.target.value === 'downloader') {
      if (e.target.checked) {
        this.setState({
          downloaderChecked: true,
          previewerChecked: true,
        })
      } else {
        this.setState({ downloaderChecked: false })
      }
    } else if (e.target.value === 'previewer') {
      if (e.target.checked) {
        this.setState({ previewerChecked: true })
        if (this.state.uploaderChecked) {
          this.setState({ downloaderChecked: true })
        }
      } else {
        this.setState({
          downloaderChecked: false,
          previewerChecked: false,
        })
      }
    } else if (e.target.value === 'uploader') {
      if (this.state.previewerChecked && e.target.checked) {
        this.setState({
          downloaderChecked: true,
          previewerChecked: true,
          uploaderChecked: true,
        })
      } else {
        this.setState({ uploaderChecked: e.target.checked })
      }
    }
  }

  handleRefreshCode = () => {
    this.setState({
      plainAccessCode: randomCode(5),
    })
  }

  handleCodeChange = (checked) => {
    this.setState({
      isCode: checked,
      plainAccessCode: randomCode(5),
      accessCodeMode: (checked && this.state.isDynamicCode ? 'mail' : 'static'),
    })
  }

  handleDynamicCodeChange = (e) => {
    const checked = e.target.checked
    this.setState({
      isDynamicCode: checked,
      plainAccessCode: checked ? null : randomCode(5),
      accessCodeMode: checked ? 'mail' : 'static',
    })
  }

  handleValidDateTypeChange = (value) => {
    this.setState({
      validDateType: value,
    })

    if (value === '-1') {
      this.state.effectiveAt = moment().valueOf()
      this.state.expireAt = moment().valueOf()
      return
    }

    if (value === '0') {
      this.state.effectiveAt = 0
      this.state.expireAt = 0
      return
    }

    this.state.effectiveAt = moment().valueOf()
    this.state.expireAt = moment().add(parseInt(value), 'days').valueOf()
  }

  handleRangePickerChange = (date) => {
    this.state.effectiveAt = date[0].valueOf()
    this.state.expireAt = date[1].valueOf()
    this.setState({
      rangePickerValue: [moment(moment(this.state.effectiveAt), 'YYYY-MM-DD HH:mm'),
        moment(moment(this.state.expireAt), 'YYYY-MM-DD HH:mm')],
    })
  }

  handleIdentityChange = (e) => {
    this.setState({
      identity: e.target.value,
    })
  }

  render () {
    const { visible, isCode, isDynamicCode, validDateType, fileType, shareLink, plainAccessCode, rangePickerValue,identity } = this.state

    return (
      <Modal
        title="创建分享链接"
        visible={visible}
        onCancel={this.handleCancel}
        footer={[
          <Button key="ok" type="primary" size="large" onClick={this.handleOk}>完成</Button>,
          <Button key="cancel" size="large" onClick={this.handleCancel}>取消</Button>,
        ]}
      >
        <Form>
          <FormItem {...formItemLayout} label="分享链接">
            <Input value={shareLink} disabled />
          </FormItem>
          <FormItem {...formItemLayout} label="访问权限">
            <Checkbox value="downloader" onChange={this.handleRoleChange} checked={this.state.downloaderChecked}>下载</Checkbox>
            <Checkbox value="previewer" onChange={this.handleRoleChange} checked={this.state.previewerChecked}>预览</Checkbox>
            {fileType === 0 &&
              <Checkbox value="uploader" onChange={this.handleRoleChange} checked={this.state.uploaderChecked}>上传</Checkbox>
            }
          </FormItem>
          <FormItem {...formItemLayout} label="提取码">
            <Switch checked={isCode} checkedChildren="开" unCheckedChildren="关" onChange={this.handleCodeChange} />
            {(isCode && !isDynamicCode) &&
              <Input value={plainAccessCode} style={{ width: '60px', marginLeft: '10px' }} size="small" readOnly />
            }
            {(isCode && !isDynamicCode) &&
              <Button type="primary" size="small" style={{ marginLeft: '10px' }} onClick={this.handleRefreshCode}>刷新</Button>
            }
            {isCode &&
              <Checkbox checked={isDynamicCode} style={{ marginLeft: '10px' }} onChange={this.handleDynamicCodeChange}>动态提取码</Checkbox>
            }
            {(isCode && isDynamicCode) &&
              <Input value={identity} style={{ width: '150px', marginLeft: '10px' }} size="small" onChange={this.handleIdentityChange} placeholder="请输入邮箱地址" />
            }
          </FormItem>
          <FormItem {...formItemLayout} label="有效期">
            <Select value={validDateType} onChange={this.handleValidDateTypeChange} style={{ width: '80px' }}>
              <Option value="0">永久</Option>
              <Option value="1">1天</Option>
              <Option value="3">3天</Option>
              <Option value="7">1周</Option>
              <Option value="30">1月</Option>
              <Option value="-1">自定义</Option>
            </Select>
            {(validDateType === '-1') &&
              <RangePicker
                allowClear={false}
                value={rangePickerValue}
                style={{ marginLeft: '10px' }}
                showTime={{ format: 'HH:mm' }}
                format="YYYY-MM-DD HH:mm"
                placeholder={['生效时间', '失效时间']}
                onChange={this.handleRangePickerChange}
              />
            }
          </FormItem>
        </Form>
      </Modal>
    )
  }
}
