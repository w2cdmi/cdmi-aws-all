import { Component, FileTypeIcon } from 'wolf'
import { Tabs, Table, Button, Row, Col, message } from 'antd'
import { getUserInfo} from 'utils'
import styles from './trash.less'

export default class AllHandle extends Component{
	constructor(props){
		super(props)
	}
	render(){
		return(
			<div>
			{
				this.props.trashData.length>0?(
					<div className={styles.all_handle_btn}>
						<Button onClick={this.props.deleteAll} type="primary">清空回收站</Button>
						<Button onClick={this.props.restoreAll} type="primary">还原所有项目</Button>
					</div>
					
				):(<span></span>)
			}
				
			</div>
		)
	}
}
