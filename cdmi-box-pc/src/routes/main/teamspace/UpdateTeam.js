import { Component, WolfForm,FileIcon } from 'wolf'
import { Tabs, Icon, Modal, Button, Form, Input, Radio, message} from 'antd'
import UpdateTeamForm from './UpdateTeamForm'
import styles from './Teamspace.less'
import { getUserInfo } from 'utils'
const confirm = Modal.confirm
export default class UpdateTeam extends Component{
	constructor(props){
		super(props)
		this.state={
			visible:false,
			name:'',
			description:'',
		}
	}
	componentDidMount(){
		this.cloudUserId = getUserInfo().cloudUserId;
		this.updates = this.http()
	}
	showModal = () =>{
		const self =this
		if(self.props.checked==false){
			message.warning("请选择团队空间")
		}else{
			self.setState({
				visible:true
			})
		}
		
	
	}
	handleOk = () =>{
		const self =this
		self.setState({
			visible:false,
			name:self.formRef.props.form.getFieldValue('name'),
			description:self.formRef.props.form.getFieldValue('description')
		},()=>{
			self.updates.url = `/teamspaces/${this.props.id}`
			self.updates.data = JSON.stringify({
	    		"name":self.state.name,
	    		"description":self.state.description
			})
			self.updates.put(() => {
				this.props.changeState({
					checked:false
				})
//	      		    console.log(this.refs.teamform.getFieldValue('name'))
	      		self.formRef.props.form.resetFields()
	      		self.props.userTeamData()
	    	})
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
	render(){
		return(
			<div className={styles.depa_info}>
				<Button onClick={this.showModal} style={{marginRight:'16px'}}>修改团队空间</Button>
				<Modal
					ref="modal"
					title="修改团队空间"
			        visible={this.state.visible}
			        onOk={this.handleOk}
			        onCancel={this.handleCancel}
				>
				<UpdateTeamForm data={this.props.data} wrappedComponentRef={(inst) => this.formRef = inst}></UpdateTeamForm>	
				</Modal>
			</div>
		)
	}
}
