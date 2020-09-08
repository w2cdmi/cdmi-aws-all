import React from 'react'
import {Form, Input, Radio} from 'antd'
import styles from './Departspace.less'
const FormItem = Form.Item;
const { TextArea } = Input;

export default class DepartForm extends React.Component{
	render(){
//		const { getFieldDecorator } = this.props.form;
		const formItemLayout = {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 4 },
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 20 },
      },
    };
		return(
			<div>
				<Form>
					<FormItem 
						className={styles.depart_form}
						{...formItemLayout}
						label="部门名称"
					>
						<Input/>
					</FormItem>
					<FormItem 
						{...formItemLayout}
						label="部门描述"
					>
						<TextArea rows={4} className={styles.desc_textarea}/>
					</FormItem>
				</Form>
			</div>
		)
	}
}
