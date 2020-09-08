import { Component, FileTypeIcon, SavePersonerSpace } from 'wolf'
import { getUserInfo, formatFileSize, formatDate } from 'utils'
import { Modal, message } from 'antd'
import ReceiveList from './ReceiveList'
import HandelList from './HandelList'
const confirm = Modal.confirm
import styles from './receiveshare.less'
export default class Receiveshares extends Component {
	constructor(props){
		super(props)
		this.state={
    		data:[],
    		dataIndex:[],
    		selectedRecords: [],
    		datas:'',
    		selectedRowKeys: []
		}
	}
	componentDidMount(){
		this.cloudUserId = getUserInfo().cloudUserId
		this.receshare = this.http();
		this.receiveData()
	}
//	收到文件的数据
	receiveData(){
		this.receshare.url=`/shares/received`
		this.receshare.data =JSON.stringify({
    		"offset":0,
    		"limit":100
		})
		this.receshare.post((data)=>{
			this.setState({
				data:[...data.contents]
			})
		})
	}
//	退出分享
		deleteShares(){
		
		const self = this
		const deleteShareData = self.state.datas;
		if(deleteShareData==""){
			message.warning("请选择文件")
		}else{
			confirm({
			title: '系统提示',
			content: '确认退出分享吗',
			okText: '确定',
			okType: 'danger',
			cancelText: '取消',
		    onOk () {
	    		deleteShareData.map((deleteShareDatas)=>{
				self.receshare.url=`/shareships/${deleteShareDatas.ownerId}/${deleteShareDatas.nodeId}?userId=${deleteShareDatas.sharedUserId}&type=${deleteShareDatas.sharedUserType}`
				self.receshare.delete((data) => {
					self.receiveData()
				
	    		})	
			})
	    	message.success("退出分享成功")
	    	self.setState({
	    		datas:''
	    	})
	    	},
      		onCancel () {

      		},
    	})
			
	}
		
}
	aa(){
		console.log(this.state.datas)
		console.log(this.state.selectedRowKeys)
	}
	  /**
	   * 显示复制移动窗口
	   * @param record
	   */
	  moveAndCopyRecord = (record) => {
	    this.state.selectedRecords = []
	    this.state.selectedRecords.push(record)
	    this.refs.moveAndCopyModal.show()
	  }
	  
	  
	    /**
   * 文件复制
   * @param files
   * @param destParent
   * @param destOwnerId
   * @param msg
   */
  filesCopy = (files, destParent, destOwnerId, msg) => {
  	console.log(files)
    if (files[0].length > 0) {
      files[0].map((item) => {
        if (item.type === 1) {
          this.receshare.url = `/files/${item.ownerId}/${item.nodeId}/copy`
        } else if (item.type === 0) {
          this.receshare.url = `/folders/${item.ownerId}/${item.nodeId}/copy`
        }
        this.receshare.data = JSON.stringify({ destParent, destOwnerId, autoRename: true })
        this.receshare.put(() => {
//        this.getData(this.state.parent)
        })
      })
      message.success(msg)
    }
  }
//	文件保存到个人空间
	onCopyClick = (folderId) => {
    this.filesCopy(this.state.selectedRecords, folderId, this.cloudUserId, '保存成功')
    this.refs.moveAndCopyModal.hide()
  }
	achieveData(datas){
		this.setState(datas)
	}

  render() {
    return (
      <div>
      	<div>
      		<p className={styles.share_num_title} onClick={this.aa.bind(this)}>共收到{this.state.data.length}个分享</p>
      		<HandelList
      		
      		achieveData={this.achieveData.bind(this)}
      		receiveData={this.receiveData.bind(this)}
			datas={this.state.datas}
      		Id={this.state.Id}
      		dataIndex={this.state.dataIndex}
      		data={this.state.data}
      		deleteShares={this.deleteShares.bind(this)}
      		moveAndCopyRecord={this.moveAndCopyRecord}
      		></HandelList>
      	</div>
      	<SavePersonerSpace
      		ref="moveAndCopyModal"
            onCreateFolderClick={this.onCreateFolderClick}
            onCopyClick={this.onCopyClick}
            onMoveClick={this.onMoveClick}
      	></SavePersonerSpace>
      	<ReceiveList
      		selectedRowKeys={this.state.selectedRowKeys}
			data={this.state.data}
      		achieveData={this.achieveData.bind(this)}
			
      	>
      	</ReceiveList>
      </div>
    )
  }
}

