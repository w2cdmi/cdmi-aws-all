import { Component, FileTypeIcon } from 'wolf'
import { Tabs, Table, Button, Row, Col, message } from 'antd'
import { getUserInfo} from 'utils'
import styles from './sendshares.less'

export default class AssignerHandle extends Component{
	constructor(props){
		super(props)
	}
	onShareClick (e) {
	    e.stopPropagation()
	    if (this.props.onShareClick) {
	      this.props.onShareClick(this.props.assignerDatas)
	    }
  	}
	render(){
		return(
			<div>
				{
					this.props.assignerData.length >0?(
						<div className={styles.handel_all_btn}>
							<Button onClick={this.props.onShareClick.bind(this)} type="primary">查看分享</Button>
							<Button onClick={this.props.cancelAssigner} type="primary">取消分享</Button>
						</div>
					):(<span></span>)
				}
			</div>
		)
	}
}
