import React from 'react'
import { Component, WolfForm,FileIcon } from 'wolf'
import {Modal, Button, Form, Radio, message} from 'antd'
import { getUserInfo, formatFileSize } from 'utils'
import styles from './Teamspace.less'

const confirm = Modal.confirm

export default class SeeDetail extends Component{
    constructor(props){
        super(props)
        this.state={
            spaceDetail:{}
        }
    }
    componentDidMount() {
        this.cloudUserId = getUserInfo().cloudUserId;
        this.seeDetail = this.http()
    }
    showModal = () =>{
		const self =this
		if(self.props.checked==false){
			message.warning("请选择团队空间")
		}else{
			self.setState({
				visible:true
            })
            self.seeDetail.url = `/teamspaces/${this.props.id}`
            self.seeDetail.data ={}
            self.seeDetail.get((data)=>{
                self.setState({
                    spaceDetail:data
                })
            })
		}
		
	
    }
    handleOk = () =>{
		this.setState({
			visible:false
		})
    }
    handleCancel = () =>{
        this.setState({
			visible:false
        })
        this.props.changeState({
			checked:false
		})
    }
    getSpaceDetail = () =>{
        this.seeDetail.url = `/teamspaces/${this.props.id}`
        this.seeDetail.data ={}
        this.seeDetail.get((data)=>{
            this.setState({
                spaceDetail:data
            })
        })
    }
    aa = () =>{
        console.log(this.state.spaceDetail)
    }
    render(){
        return(
            <div className={styles.depa_info}>
                <Button onClick={this.aa} style={{marginRight:'16px'}}>测试</Button>
				<Button onClick={this.showModal} style={{marginRight:'16px'}}>查看详情</Button>
                <Modal
					title="查看空间详情"
			        visible={this.state.visible}
			        onOk={this.handleOk}
			        onCancel={this.handleCancel}
				>
					<p>名称：<span>{this.state.spaceDetail.name}</span></p>
                    <p>描述：<span>{this.state.spaceDetail.description}</span></p>
                    <p>成员数配额：
                    {
                        this.state.spaceDetail.maxMembers==-1?(
                            <span>无限制</span>
                        ):(<span>{this.state.spaceDetail.maxMembers}</span>)
                    }
                    </p>
                    <p>当前成员数:<span>{this.state.spaceDetail.curNumbers}</span></p>
                    <p>空间配额:<span>{formatFileSize(parseInt(this.state.spaceDetail.spaceQuota))}</span></p>
                    <p>已用空间:<span>{formatFileSize(parseInt(this.state.spaceDetail.spaceUsed))}</span></p>
				</Modal>
			</div>
        )
    }
}