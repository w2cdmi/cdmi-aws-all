import React from 'react'
import { Component, WolfForm,FileIcon } from 'wolf'
import { Tabs, Icon, Modal, Button, Form, Input, Radio} from 'antd'
import styles from './Teamspace.less'
import { getUserInfo } from 'utils'
import TeamForm from './TeamForm'

//创建团队
export default class CreatTeamspace extends Component{
	constructor(props){
		super(props)
		this.state = { 
			visible: false,
			data:[],
			name:"",
			description:""
		}
	}
	componentDidMount(){
		this.cloudUserId = getUserInfo().cloudUserId;
		this.creatteam = this.http()
		
	}
  showModal = () => {
    this.setState({
      visible: true,
      name:"",
		description:""
    });
    this.props.onClick()
  }
  handleOk = () => {
    this.setState({
      	visible: false,
      	name:this.formRef.props.form.getFieldValue('name'),
				description:this.formRef.props.form.getFieldValue('description')
    },()=>{
    	if(this.formRef.props.form.getFieldValue('name')==undefined || this.formRef.props.form.getFieldValue('name')==''){
    		alert('请填写团队名称')
    	}else{
	    	this.creatteam.url = `/teamspaces`;
	    	
			this.creatteam.data = JSON.stringify({
	    		"name":this.state.name,
					"description":this.state.description,
					"spaceQuota":209715200,
					"maxMembers":100
			})
			this.creatteam.post((data) => {
	      		console.log(data)
//	      		    console.log(this.refs.teamform.getFieldValue('name'))
	      		this.formRef.props.form.resetFields()
	      		this.props.userTeamData()
	    	})
			
//			this.refs.teamform.getFieldValue("name")=="123";
//	      	this.refs.teamform.getFieldValue("description")=="";
	      	console.log(this.formRef.props.form.getFieldValue("name"))
    	}
    });
    	
    
  }
  handleCancel = () => {
    this.setState({
      visible: false,
    });
    this.formRef.props.form.resetFields()
  }
  render(){
  	return(
  		<div  className={styles.build_team}>
  			<Button type="primary" onClick={this.showModal}>创建团队</Button>
        <Modal
          title="创建项目"
          visible={this.state.visible}
          onOk={this.handleOk}
          onCancel={this.handleCancel}
        >
        <TeamForm wrappedComponentRef={(inst) => this.formRef = inst}></TeamForm>
        </Modal>
  		</div>
  	)
  }
}
