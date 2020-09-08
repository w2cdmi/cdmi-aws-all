import React from 'react'
import { Tabs, Table, Button, Row, Col } from 'antd'
import { Component, FileTypeIcon } from 'wolf'
import { getUserInfo, formatFileSize, formatDate } from 'utils'
import styles from './trash.less'

export default class TrashList extends React.Component {
  constructor (props) {
    super(props)
  }
  render () {
    const columns = [{
      key: 'name',
      title: '名称',
      dataIndex: 'name',
      render: (text, record) => {
        return (
          <div className={styles.file_icon_sty} style={{ lineHeight: '32px' }}>
            <FileTypeIcon style={{ verticalAlign: 'bottom' }} dataSource={record} size="small" />
            <span>{text}</span>
          </div>
        )
      },
    },
    {
      key: 'url',
      title: '路径',
      dataIndex: 'url',
      render: (text, record) => {
        return '/'
      },
    },
    {
      key: 'size',
      title: '大小',
      dataIndex: 'size',
      render: (text, record) => {
        if (record.type === 1) {
          return formatFileSize(parseInt(text))
        }
        return '-'
      },
    },
    {
      key: 'createdAt',
      title: '创建时间',
      dataIndex: 'createdAt',
      render: (text, record) => {
        return formatDate(parseInt(text))
      },
    },
    ]
    const rowSelection = {
      onChange: (selectedRowKeys) => {
        console.log(selectedRowKeys)
        if (selectedRowKeys.length > 0) {
          this.props.valiables({
            valiable: true,
          })
        } else if (selectedRowKeys.length == 0) {
          this.props.valiables({
            valiable: false,
          })
        }
      },
      onSelect: (record, selected, selectedRows) => {
        this.props.achieveSelectData({
          restoreSelectData: selectedRows,
        })
        console.log(record, selected, selectedRows)
      },
      onSelectAll: (selected, selectedRows, changeRows) => {
        this.props.achieveSelectData({
          restoreSelectData: selectedRows,
        })
        console.log(selected, selectedRows, changeRows)
      },
    }
    return (
      <div>
        {
          this.props.trashData.length > 0 ? (
            <Table rowKey={record => record.id} rowSelection={rowSelection} pagination={false} columns={columns} dataSource={this.props.trashData} />
          ) : (<p>您的回收站里没有文件</p>)
        }
      </div>
    )
  }
}
