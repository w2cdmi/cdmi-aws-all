import React from 'react'
import {Form, Input, Radio, Button} from 'antd'
import styles from './Teamspace.less'
const FormItem = Form.Item;
const { TextArea } = Input;

class UpdateTeamForm extends React.Component{
	constructor(props){
		super(props)
	}
	componentDidMount(){
		this.props.form.setFieldsValue({
      			name:this.props.data.teamspace.name,
      			description:this.props.data.teamspace.description
    		})
	}
	aa =()=>{
		console.log(this.props.data)
		
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
			<p onClick={this.aa}>222</p>
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
UpdateTeamForm = Form.create()(UpdateTeamForm);
export default UpdateTeamForm