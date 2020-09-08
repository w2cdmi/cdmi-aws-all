import { Component, FileTypeIcon } from 'wolf'
import { Tabs, Table, Button, Row, Col, message } from 'antd'
import { getUserInfo} from 'utils'
import styles from './trash.less'

export default class SingleHandle extends Component{
	constructor(props){
		super(props)
	}
	valiable(){
		let btnstyle
		if(this.props.valiable){
			btnstyle={
				display:'block'
			}
			return btnstyle
		}else{
			btnstyle={
				display:'none'
			}
			return btnstyle
		}
		
	}
	render(){
		return(
			<div style={this.valiable()} >
			{
				this.props.trashData.length>0?(
					<div className={styles.single_handle_btn}>
						<Button onClick={this.props.deleteSelect} type="primary">删除</Button>
						<Button onClick={this.props.restoreSelect} type="primary">还原</Button>
					</div>
				):(<span></span>)
			}
		</div>
		)
	}
}