import React from 'react'
import { Component, WolfForm,FileIcon } from 'wolf'
import {Modal, Button, Form, Radio, message} from 'antd'
import { getUserInfo } from 'utils'
import styles from './Teamspace.less'

const confirm = Modal.confirm
//修改空间
export default class TeamInfo extends Component{
	constructor(props){
		super(props);
		this.state={
			display:"",
			id:"",
			checked:false
		}
	}
	componentDidMount(){
		this.cloudUserId = getUserInfo().cloudUserId;
		this.del = this.http()
		
	}
	click(){
		console.log(this.props.btnteam);
	}
	deleteData(){
		const self = this
//		console.log(this.props.id);
		self.setState({
		      id:self.props.id
		   });
		   
		if(self.props.checked==false){
			message.warning("请选择团队空间")
		}else{
			confirm({
			   	title:'解散团队',
				content:'确认要解散该团队吗？',
				okText:'确定',
				okType:'danger',
				cancelText:'取消',
				onOk(){
					self.del.url = `/teamspaces/${self.props.id}`;
			self.del.delete((data) => {
				self.props.userTeamData()
				self.props.changeState({
					checked:false
				})
				message.success("解散成功")
	    	})
				},
				onCancel(){
					self.props.changeState({
						checked:false
					})
				}
		   })
	}
		
	}

	render(){
		return(
			<div className={styles.depa_info}>			
				<Button style={{marginRight:"16px"}}>移交团队空间</Button>
				<Button style={{marginRight:"16px"}} onClick={this.deleteData.bind(this)} data-index={this.props.btnteam}>解散团队空间</Button>
			</div>
			
		)
	}
}
