import React from 'react'
import styles from './Teamspace.less'
import { Link } from 'react-router-dom'
export default class TeamList extends React.Component{
	constructor(props){
		super(props);
		this.state ={
			clicked:"",
			backgroundColor:"",
			checked:true,
			id:"",
			roleType:""
		}
	}
//	changeStyle(){
//		let backgroundStyle;
//		if(this.props.clicked==0){
//			backgroundStyle={
//				backgroundColor:"#999999"
//			}
//			return backgroundStyle
//		}else{
//			backgroundStyle={
//				backgroundColor:""
//			}
//			return backgroundStyle
//		}
//	}
	
	onClick(data,index){
		this.props.changeState({
					checked:true
				});
		if(data.teamRole == "admin"){
			this.props.getRoleType({
				roleType:"0"
			})
			console.log(data.teamRole)
		}else if(data.teamRole == "manager"){
			this.props.getRoleType({
				roleType:"1"
			})
			console.log(data.teamRole)
		}else{
			this.props.getRoleType({
				roleType:"2"
			})
			console.log(data.teamRole)
		}

		console.log(this.props.dataSource)
		console.log(data)
		console.log(this.props.checked)
		console.log(this.state.checked)
		if(this.props.checked ==false){
			this.setState((prevState,props)=>({
			clicked:index,
			id:data.teamId,
			checked:true
		}))
		}else{
			this.setState((prevState,props)=>({
			clicked:index,
			checked:false
		}))
		}
		
		
		this.props.onClick(index,data)
	}
	
	render(){
		let curStyle={
			backgroundColor: this.state.checked && this.props.clicked ===this.state.clicked ? '#999999' : ''
		}
		return(
			<div>
				{this.props.dataSource.map((data,index)=>
					<div style={this.props.checked && this.props.clicked ===index ?{backgroundColor:'#999999'}:{backgroundColor:''}} key={index} onClick={this.onClick.bind(this,data,index)} className={styles.file_list_content}>
						<div className={styles.file_list_head}></div>
						<ul className={styles.file_info_con}>
						<Link to={`/main/teamspacefile/${data.teamId}`}>
							<li>
								<a>{data.teamspace.name}</a>
							</li>
						</Link>
							
							<li>
								<span>{data.teamspace.description}</span>
							</li>
							<li>
								<span>当前成员数:{data.teamspace.curNumbers}</span>
							</li>
							<li>
								<span>拥有者:{data.teamspace.ownedByUserName}</span>
							</li>
						</ul>
				</div>
			)}
			</div>
			
			
		)
	}
}
