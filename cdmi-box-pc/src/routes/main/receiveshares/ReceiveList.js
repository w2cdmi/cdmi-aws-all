import React from 'react'
import { Tabs, Table, Button, Row, Col } from 'antd'
import { Component, FileTypeIcon } from 'wolf'
import { getUserInfo, formatFileSize, formatDate } from 'utils'
import styles from './receiveshare.less'

export default class ReceiveList extends React.Component{
	constructor(props){
		super(props)
		this.state={
    		data:[],
    		dataindex:[],
    		nodeId:0,
    		ownerId:0,
    		selectedRowKeys:this.props.selectedRowKeys
		}
	}
	rowClick(record,index){
		console.log(index)
	}
	render(){
		const columns=[{
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
    }, {
    	key:'ownerName',
      title: '分享人',
      dataIndex: 'ownerName',
    }, {
    	key:'size',
      title: '大小',
      dataIndex: 'size',
      render:(text, record) => {
        if (record.type === 1) {
          return formatFileSize(parseInt(text))
        }
        return '-'
      }
    }, {
    	key:'modifiedAt',
      title: '分享时间',
      dataIndex: 'modifiedAt',
      render: (text, record) => {
        return formatDate(parseInt(text))
      },
    }]
		const rowSelection ={
			onChange:(selectedRowKeys)=>{
				
			},
      onSelect:(record, selected, selectedRows)=> {
      	this.props.achieveData({
      		datas:selectedRows
      	})
        console.log(record, selected, selectedRows);
      },
      onSelectAll:(selected, selectedRows, changeRows)=> {
      	this.props.achieveData({
      		datas:selectedRows
      	})
        console.log(selected, selectedRows, changeRows);
      },
		}
		return(
			<div>
      		{
      			this.props.data.length > 0?(
      				<div>
      					<Table pagination={false} rowKey={record => record.nodeId} onRowClick={this.rowClick.bind(this)} rowSelection={rowSelection} columns={columns} dataSource={this.props.data}></Table>
      				</div>
      				
      		):(<div>暂时没有收到任何分享</div>)
      		}
			
      </div>
		)
	}
}
