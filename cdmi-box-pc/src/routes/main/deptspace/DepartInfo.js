import React from 'react'
import {Modal, Button, Form,} from 'antd'
import styles from './Departspace.less'


export default class DepartInfo extends React.Component{
	constructor(props){
		super(props)
		
	}
	render(){
		return(
			<div className={styles.depart_info_btn}>
				<Button>查看详情</Button>
			</div>
		)
	}
}
