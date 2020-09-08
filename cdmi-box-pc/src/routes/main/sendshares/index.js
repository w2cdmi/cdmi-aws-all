import { Tabs, Table, Button, Row, Col, Modal, message } from 'antd'
import { 
	Component,
	CreateShareLinkModal,
	ShareLinkModal,
	SendShareLinkModal,
	ShareModal } from 'wolf'
import { getUserInfo, formatFileSize, formatDate } from 'utils'
import AssignerShare from './AssignerShare'
import AssignerHandle from './AssignerHandle'
import LinkShare from './LinkShare'
import LinkHandle from './LinkHandle'
import styles from './sendshares.less'

const TabPane = Tabs.TabPane;
const confirm = Modal.confirm
export default class Sendshares extends Component {
	constructor(props){
		super(props);
		this.state={
			assignerData:[],
			linkData:[],
			assignerDatas:[],
			linkDatas:[],
			parent:0
		}
	}
	componentDidMount(){
		this.cloudUserId = getUserInfo().cloudUserId
		this.sendShare = this.http();
		this.assignerShareData();
		this.linkShareData()
	}
//	指定人分享数据
	assignerShareData(){
		this.sendShare.url=`/shares/distributed`
		this.sendShare.data =JSON.stringify({
    		"offset":0,
    		"limit":100
		})
		this.sendShare.post((data)=>{
			this.setState({
				assignerData:[...data.contents]
			})
		})
	}
//	链接分享数据
	linkShareData(id){
		this.sendShare.url=`/links/items`
		this.sendShare.data =JSON.stringify({
    		"offset":0,
    		"limit":100
		})
		this.sendShare.post((data)=>{
			this.setState({
				linkData:[...data.files,...data.folders]
			})
		})
	}
	ass(){
		console.log(this.state.assignerData)
		console.log(this.state.linkData)
		console.log(this.state.assignerDatas)
		console.log(this.state.linkDatas)
	}
//	取消指定人分享
	cancelAssigner(){
		
		const self = this
		const record = self.state.assignerDatas
		if(record==""){
			message.warning("请选择文件")
		}else{
			console.log(record)
			confirm({
		    title: '系统提示',
		    content: '确认取消分享吗',
		    okText: '确定',
		    okType: 'danger',
		    cancelText: '取消',
	    	onOk () {
	    		record.map((records)=>{
	    			if (records.type === 1) {
			          self.sendShare.url = `/shareships/${records.ownerId}/${records.nodeId}`
			        } else {
			          self.sendShare.url = `/shareships/${records.ownerId}/${records.nodeId}`
			        }
			        self.sendShare.data = {}
			        self.sendShare.delete(() => {
			          
	          			self.assignerShareData()
	        		})
	    		})
	        	message.success('删除成功')
	        	self.setState({
	        		assignerDatas:[]
	        	})
	    	},
      		onCancel () {

      		},
    	})
		}
		
	}
//	取消链接分享
	cancelLink(){
		
		const self = this
		const record = self.state.linkDatas
		if(record==""){
			message.warning("请选择文件")
		}else{
			console.log(record)
			confirm({
		    title: '系统提示',
		    content: '确认取消分享吗',
		    okText: '确定',
		    okType: 'danger',
		    cancelText: '取消',
	    	onOk () {
	    		record.map((records)=>{
	    			if (records.type === 1) {
			          self.sendShare.url = `/links/${records.ownedBy}/${records.id}`
			        } else {
			          self.sendShare.url = `/links/${records.ownedBy}/${records.id}`
			        }
			        self.sendShare.data = {}
			        self.sendShare.delete(() => {
			          
	          			self.linkShareData()
	        		})
	    		})
	    		message.success('删除成功')
	        	self.setState({
	        		linkDatas:[]
	        	})
	    	},
      		onCancel () {

      		},
    	})
		}
		
	}
	/**
   * 链接
   * @param record
   */
  onLinkClick = (record) => {
  	
  	const records =this.state.linkDatas
  	console.log(records)
//	console.log(records[0].isSharelink)
  	if(records.length ==0){
  		message.warning("请选择文件")
  	}else if(records.length >=2){
  		message.warning("请选择单个文件查看")
  	}else{
  		if (records[0].isSharelink) {
      this.refs.shareLinkModal.show(records[0])
    } else {
      this.refs.createShareLinkModal.show(records[0])
    }
  	}
    
  }
  /**
   * 分享
   * @param record
   */
  handleShare = (record) => {
  	const records = this.state.assignerDatas
  	console.log(records)
  	if(records.length ==0){
  		message.warning("请选择文件")
  	}else if(records.length >=2){
  		message.warning("请选择单个文件查看")
  	}else{
  		this.refs.shareModal.show(records[0])
  	}
    
  }
//	获取指定人选中数据
	achiveAssign(assignerDatas){
		this.setState(assignerDatas)
	}
//	获取链接选中数据
	achiveLink(linkDatas){
		this.setState(linkDatas)
	}
  render() {
  	
    return (
      <div>
        <Tabs defaultActiveKey="1">
          <TabPane tab="指定人分享" key="1">
            <p className={styles.share_num_title} onClick={this.ass.bind(this)}>共设置{this.state.assignerData.length}个分享</p>
            <AssignerHandle
            	onShareClick={this.handleShare}
            	cancelAssigner={this.cancelAssigner.bind(this)}
            	assignerData={this.state.assignerData}
            ></AssignerHandle>
            <AssignerShare
            	achiveAssign={this.achiveAssign.bind(this)}
            	assignerData={this.state.assignerData}
            ></AssignerShare>
            <ShareModal ref="shareModal" />
          </TabPane>
          <TabPane tab="通过链接分享" key="2">
          	<p className={styles.share_num_title} onClick={this.ass.bind(this)}>共设置{this.state.linkData.length}个分享</p>
          	<LinkHandle
          		onLinkClick={this.onLinkClick}
          		cancelLink={this.cancelLink.bind(this)}
          		linkData = {this.state.linkData}
          	></LinkHandle>
          	<div>
          		<CreateShareLinkModal ref="createShareLinkModal"
	            	onSuccess={() => this.linkShareData()}
	            	onOk={record => this.refs.shareLinkModal.show(record)}
          		/>
          		<ShareLinkModal ref="shareLinkModal"
		            onSuccess={() => this.linkShareData()}
		            onOk={linkRecord => this.refs.createShareLinkModal.show(linkRecord)}
		            onSendLink={(record, linkRecord) => this.refs.sendShareLinkModal.show(record, linkRecord)}
		            onUpdate={(record, linkRecord) => this.refs.createShareLinkModal.show(record, linkRecord, 'UPDATE')}
          		/>
          		<SendShareLinkModal ref="sendShareLinkModal" />

          	</div>
          	<LinkShare
          		achiveLink={this.achiveLink.bind(this)}
          		linkData ={this.state.linkData}
          	></LinkShare>
          </TabPane>
        </Tabs>
      </div>
    )
  }
}

