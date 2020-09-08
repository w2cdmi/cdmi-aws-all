import { Tabs, Icon } from 'antd'
import { Component } from 'wolf'
import { getUserInfo, formatFileSize, formatDate } from 'utils'
import DepartList from './DepartList'
import CreatDepartspace from './CreatDepartspace'
import DepartInfo from './DepartInfo'
import styles from './Departspace.less'
const TabPane = Tabs.TabPane;

export default class Deptspace extends Component {
	constructor(props){
		super(props);
			this.state={
				depart:1,
				data:[]
			}
	}
	componentDidMount () {
	    this.cloudUserId = getUserInfo().cloudUserId
	    this.depateam = this.http()
	    this.departData()
  	}
	departData(){
		this.depateam.url = `/teamspaces/items`;
		this.depateam.data = JSON.stringify({
			"type": 1,
    		"userId": this.cloudUserId
		})
		this.depateam.post((data) => {
      	this.setState({
        		data:[...data.memberships],
      		})
      	console.log(this.state.data)
    	})
	}
	handclick(){
			if(this.state.depart==1){
				this.setState({
				depart:0
			})
		}else{
			this.setState({
				depart:1
			})
		}
		}
	aa(){
		console.log(this.state.data)
		console.log(this.state.depart)
	}
  render() {
    return (
      <div>
		<button onClick={this.aa.bind(this)}>测试</button>
      	<DepartList
	      	clicked={this.state.depart} 
	      	handclick={this.handclick.bind(this)}
      		data={this.state.data}
      	>
      	</DepartList>
      </div>
    )
  }
}

