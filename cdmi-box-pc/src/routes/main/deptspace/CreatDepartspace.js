import React from 'react'
import { Tabs, Icon, Modal, Button, Form, Input, Radio} from 'antd'
import styles from './Departspace.less'
import DepartForm from './DepartForm'
//创建部门空间
export default class CreatDepartspace extends React.Component{
	state = { visible: false }
  showModal = () => {
    this.setState({
      visible: true,
    });
  }
  handleOk = (e) => {
    console.log(e);
    this.setState({
      visible: false,
    });
  }
  handleCancel = (e) => {
    console.log(e);
    this.setState({
      visible: false,
    });
  }
  render(){
  	return(
  		<div className={styles.creat_btn}>
  			<Button type="primary" onClick={this.showModal}>创建部门空间</Button>
        <Modal
          title="创建部门"
          visible={this.state.visible}
          onOk={this.handleOk}
          onCancel={this.handleCancel}
        >
        <DepartForm></DepartForm>
        </Modal>
  		</div>
  	)
  }
}
