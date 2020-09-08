import React from 'react'
import {Form, Input, Radio, Button} from 'antd'
import styles from './Teamspace.less'
const FormItem = Form.Item;
const { TextArea } = Input;
//团队表单
class DepartForm extends React.Component{
	constructor(props){
		super(props)
	}
	getFormValue(props){
		console.log(this.props.form.getFieldsValue())
	}
	render(){
		const { getFieldDecorator } = this.props.form;
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
						className={styles.define_forms}
						{...formItemLayout}
						label="团队名称"
					>
					{getFieldDecorator('name', {
                            rules: [
                                {required: true,
                                  message: '请填写团队名称'},
                            ],

                        })(
                            <Input type="text" />
                        )}
					</FormItem>
					<FormItem 
						{...formItemLayout}
						label="团队描述"
					>
					{getFieldDecorator('description', {
                            rules: [
                                {required: false,
                                  message: '项目描述'},
                            ],

                        })(
                            <TextArea rows={4} className={styles.desc_textarea}/>
                        )}
						
					</FormItem>
				</Form>
			</div>
		)
	}
}
DepartForm = Form.create()(DepartForm);
export default DepartForm