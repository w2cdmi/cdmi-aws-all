import { Tabs, Table, Button, Row, Col } from 'antd'
import { Component, FileTypeIcon} from 'wolf'
import { getUserInfo, formatFileSize, formatDate } from 'utils'
import styles from './sendshares.less'

export default class AssignerShare extends Component{
	constructor(props){
		super(props)
	}
	render(){
		const columns =[{
			key:'name',
		    title: '名称',
		    dataIndex: 'name',
		    render:(text,record)=>{
				return(
		      		<div className={styles.file_icon_sty} style={{lineHeight:'32px'}}>
		      			<FileTypeIcon style={{verticalAlign:'bottom'}} dataSource={record} size="small"></FileTypeIcon>
		      			<span>{text}</span>
		      		</div>
      			)
      	
      		}
		},
		{
			key:'url',
		    title: '路径',
			dataIndex: 'url',
			render:(text,record)=>{
				return '/'
			}
		},
		{
			key:'size',
			title:'大小',
			dataIndex:'size',
			render:(text, record) => {
		        if (record.type === 1) {
		          return formatFileSize(parseInt(text))
		        }
		        return '-'
      		}
		}]
		const rowSelection ={
			onSelect:(record,selected,selectedRows)=>{
				this.props.achiveAssign({
					assignerDatas:selectedRows
				})
				console.log(record,selected,selectedRows)
			},
			onSelectAll:(selected, selectedRows, changeRows)=>{
				this.props.achiveAssign({
					assignerDatas:selectedRows
				})
				console.log(selected, selectedRows, changeRows)
			}
		}
		return(
			<div>
			{
				this.props.assignerData.length >0?(
					<Table
					pagination={false}
					rowKey={record => record.nodeId}
					rowSelection={rowSelection} 
					columns={columns} 
					dataSource={this.props.assignerData}
				></Table>
				):(<div>没有任何发出的分享</div>)
			
			}
				
			</div>
		)
	}
}
