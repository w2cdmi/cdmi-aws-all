import { Tabs, Icon } from 'antd'
import { Component } from 'wolf'
import { Modal, message } from 'antd'
import { getUserInfo } from 'utils'
import styles from './trash.less'
import TrashList from './TrashList'
import AllHandle from './AllHandle'
import SingleHandle from './SingleHandle'

const TabPane = Tabs.TabPane
const confirm = Modal.confirm

export default class Trash extends Component {
  constructor (props) {
    super(props)
    this.state = {
      trashData: [],
      valiable: false,
      restoreSelectData: [],
    }
  }
  componentDidMount () {
    this.cloudUserId = getUserInfo().cloudUserId
    this.trash = this.http()
    this.trashdata()
  }
  //	回收站数据
  trashdata () {
    this.trash.url = `/trash/${this.cloudUserId}`
    this.trash.data = JSON.stringify({
      offset: 0,
    		limit: 100,
    })
    this.trash.post((data) => {
      this.setState({
        trashData: [...data.files, ...data.folders],
      })
    })
  }
  //	还原所有文件
  restoreAll () {
    const self = this
    const record = self.state.trashData
    confirm({
      title: '还原所有项目',
      content: '确认还原所有项目吗？',
      okText: '确定',
      okType: 'danger',
      cancelText: '取消',
      onOk () {
        record.map((records) => {
          self.trash.url = `/trash/${self.cloudUserId}/${records.id}`
          self.trash.data = {}
          self.trash.put(() => {
            self.trashdata()
          })
        })
        message.success('还原成功')
      },
      onCancel () {

      },
    })
  }
  //	还原所选的文件
  restoreSelect () {
    const self = this
    const record = self.state.restoreSelectData
    confirm({
      title: '还原所选项目',
      content: '确认还原所选择的项目吗？',
      okText: '确定',
      okType: 'danger',
      cancelText: '取消',
      onOk () {
        record.map((records) => {
          self.trash.url = `/trash/${self.cloudUserId}/${records.id}`
          self.trash.data = {}
          self.trash.put(() => {
            self.trashdata()
          })
        })
        message.success('还原成功')
      },
      onCancel () {

      },
    })
  }
  //	删除所有文件
  deleteAll () {
    const self = this
    const record = self.state.trashData
    confirm({
      title: '清空回收站',
      content: '确认要永久删除所有项目吗？',
      okText: '确定',
      okType: 'danger',
      cancelText: '取消',
      onOk () {
        record.map((records) => {
          self.trash.url = `/trash/${self.cloudUserId}/${records.id}`
          self.trash.data = {}
          self.trash.delete(() => {
            self.trashdata()
          })
        })
        message.info('删除成功')
      },
      onCancel () {

      },
    })
  }
  //	删除选择的文件
  deleteSelect () {
    const self = this
    const record = self.state.restoreSelectData
    confirm({
      title: '删除项目',
      content: '确认要永久删除所选中的项目吗？',
      okText: '确定',
      okType: 'danger',
      cancelText: '取消',
      onOk () {
        record.map((records) => {
          self.trash.url = `/trash/${self.cloudUserId}/${records.id}`
          self.trash.data = {}
          self.trash.delete(() => {
            self.trashdata()
          })
        })
        message.info('删除成功')
      },
      onCancel () {

      },
    })
  }
	tra =() => {
	  console.log(this.state.trashData)
	  console.log(this.state.valiable)
	  console.log(this.state.restoreSelectData)
	}
	valiables (valiable) {
	  this.setState(valiable)
	}
	achieveSelectData (restoreSelectData) {
	  this.setState(restoreSelectData)
	}
	render () {
	  return (
	    <div>
	      <div className={styles.handle_btn}>
    <AllHandle
    deleteAll={this.deleteAll.bind(this)}
    restoreAll={this.restoreAll.bind(this)}
    trashData={this.state.trashData}
  />
	        <SingleHandle
    deleteSelect={this.deleteSelect.bind(this)}
    trashData={this.state.trashData}
    restoreSelect={this.restoreSelect.bind(this)}
    valiable={this.state.valiable}
  />
  </div>
	      <TrashList
	        achieveSelectData={this.achieveSelectData.bind(this)}
	        valiables={this.valiables.bind(this)}
	        trashData={this.state.trashData}
  />
  </div>
	  )
	}
}

