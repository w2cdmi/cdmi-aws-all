import React from 'react'
import { Component, WolfForm,FileIcon } from 'wolf'
import {Modal, Button, Form, Radio, message} from 'antd'
import { getUserInfo } from 'utils'
import styles from './Teamspace.less'

const confirm = Modal.confirm

export default class QuitSpace extends Component{
	constructor(props){
		super(props);
	}
	componentDidMount(){
		
	}


	render(){
		return(
			<div className={styles.depa_info}>					
				<Button>退出团队空间</Button>
			</div>
			
		)
	}
}