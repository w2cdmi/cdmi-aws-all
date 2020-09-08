import { Tabs, Icon, Modal, Button, Form, Input, Radio} from 'antd'
import { Component, WolfForm,FileIcon } from 'wolf'
import { getUserInfo } from 'utils'
import styles from './Teamspace.less'
import CreatTeamspace from './CreatTeamspace'
import TeamList from './TeamList'
import TeamInfo from './TeamInfo'
import UpdateTeam from './UpdateTeam'
import QuitSpace from './QuitSpace'
import SeeDetail from './SeeDetail'

export default class Teamspace extends Component {
	constructor(props){
		super(props);
		this.state={
			index:"",
			dataSource:[],
			checked:false,
			id:"",
			data:{},
			roleType:""
		}
	}
	componentDidMount(){
		this.cloudUserId = getUserInfo().cloudUserId;
		this.ds = this.http()
		this.userTeamData()
	}
	userTeamData =()=>{
		this.ds.url = `/teamspaces/items`;
		this.ds.data = JSON.stringify({
			"type": 0,
    		"userId": this.cloudUserId
		})
		this.ds.post((data) => {
      	this.setState({
        		dataSource: [...data.memberships],
      		})
      	console.log(this.state.dataSource)
    	})
		
	}
	
	handClick(index,data){
		this.setState({
			index:index,
			id:data.teamId,
			data:data
		})
	}
	changeState(checked){
		this.setState(checked)
	}
	getRoleType = (roleType) => {
		this.setState(roleType)
	}
  render() {
	console.log(this.state.roleType)
    return (
      <div>
      	<div style={{overflow:"hidden"}}>
      		<CreatTeamspace 
	      		userTeamData={this.userTeamData}
	      		onClick={this.userTeamData}
	      	>
      		</CreatTeamspace>
      		<SeeDetail
			  id={this.state.id}
			  checked={this.state.checked}
			  changeState={this.changeState.bind(this)}
			></SeeDetail>
			  {
				  this.state.roleType === "0" ? (
					<div>
						<UpdateTeam
						id={this.state.id}
						data={this.state.data}
						dataSource={this.state.dataSource}
						userTeamData={this.userTeamData}
						checked={this.state.checked}
						changeState={this.changeState.bind(this)}
						></UpdateTeam>
						<TeamInfo 
						checked={this.state.checked} 
						id={this.state.id} 
						userTeamData={this.userTeamData}
						btnteam={this.state.index}
						changeState={this.changeState.bind(this)}
						>
						</TeamInfo>	
					</div>
					
				  ):(
					  <span></span>
				  )
			  }
			  {
				  this.state.roleType === "1" || this.state.roleType === "2" ? (
					<QuitSpace></QuitSpace>
				  ):(
					  <span></span>
				  )
			  }
      		
      		
      	</div>
      	{
      		this.state.dataSource.length>0?(
      			<TeamList 
		      	checked={this.state.checked} 
		      	userTeamData={this.userTeamData} 
		      	dataSource={this.state.dataSource} 
		      	onClick={this.handClick.bind(this)} 
		      	clicked={this.state.index}
				changeState={this.changeState.bind(this)}
				getRoleType={this.getRoleType}
	      		>
      			</TeamList>
      		):(<p>您当前还没有团队或加入的团队</p>)
      	}
      	
      </div>
    )
  }
}

