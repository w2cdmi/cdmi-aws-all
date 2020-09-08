import { Component, FileTypeIcon } from 'wolf'
import { Tabs, Table, Button, Row, Col, Modal ,message } from 'antd'
import { getUserInfo} from 'utils'
import styles from './receiveshare.less'

const confirm = Modal.confirm
export default class HandelList extends Component{
	constructor(props){
		super(props)
		this.state={
			url:'',
			datas:""
		}
	}
	savePerClick(){
		if(this.props.datas ==""){
			message.warning("请选择保存的文件")
		}else{
			this.props.moveAndCopyRecord(this.props.datas)
		}
		
	}

	render(){
		return(
			<div>
				{
					this.props.data.length >0?(
						<div className={styles.handel_all_btn}>
							<Button data-index={this.props.dataIndex} type="primary">下载</Button>
							<Button onClick={this.savePerClick.bind(this)} type="primary">保存到个人空间</Button>
							<Button onClick={this.props.deleteShares}
							type="primary"
							>退出分享</Button>
						</div>
					):(<span></span>)
				}
			</div>
			
			
		)
	}
}
