import React from 'react'
import { Link } from 'react-router-dom'
import styles from './Departspace.less'
export default class DepartList extends React.Component{
	constructor(props){
		super(props)
		this.state ={
			clicked:1,
		}
	}
	changeStyle(){
		let backgroundStyle;
		if(this.props.clicked==0){
			backgroundStyle={
				backgroundColor:"#999999"
			}
			return backgroundStyle
		}else{
			backgroundStyle={
				backgroundColor:""
			}
			return backgroundStyle
		}
		}
	handclick = () => {
		this.props.handclick()
	}
	render(){
		return(
			<div>
			{
				this.props.data.map((datas)=>
					<div key={datas.id} style={this.changeStyle()} onClick={this.handclick} className={styles.depart_list_con}>
						<div  className={styles.depart_img}><img src='../images/departSpace_03.png'/></div>
						<ul className={styles.file_info_con}>
								<Link to={`/main/deptspacefile/${datas.teamId}/${datas.role}`}>
									<li>
										<a>{datas.teamspace.name}</a>
									</li>
								</Link>
								<li>
									<span>当前成员数:{datas.teamspace.curNumbers}</span>
								</li>
						</ul>
					</div>
				
			)}
				
			</div>
			
		)
	}
}
