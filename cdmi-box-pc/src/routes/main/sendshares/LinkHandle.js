import { Component, FileTypeIcon } from 'wolf'
import { Tabs, Table, Button, Row, Col, message } from 'antd'
import { getUserInfo} from 'utils'
import styles from './sendshares.less'

export default class LinkHandle extends Component{
	constructor(props){
		super(props)
	}
	onLinkClick (e) {
	    e.stopPropagation()
    	if (this.props.onLinkClick) {
      		this.props.onLinkClick(this.props.linkDatas)
    	}
	   
	    
  	}
	render(){
		
		return(
			<div>
				{
					this.props.linkData.length >0?(
						<div className={styles.handel_all_btn}>
							<Button onClick={this.onLinkClick.bind(this)} type="primary">查看分享</Button>
							<Button onClick={this.props.cancelLink} type="primary">取消分享</Button>
						</div>
					):(<span></span>)
				}
			</div>
		)
	}
}