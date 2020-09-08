/**
 * Created by quxiangqian on 2017/11/27.
 */
import { MutilDataGrid, EditCell, Component, FileTypeIcon } from 'wolf'
import { Menu, Dropdown, Icon } from 'antd'
import { formatFileSize, formatDate } from 'utils'

export default class AllfileDataGrid extends Component {
  constructor (props) {
    super(props)
    this.state = {
      selectedRowKeys: [],
      selectedRows: [],
      overkey: 0,
    }

    this.columns = [{
      title: '名称',
      dataIndex: 'name',
      key: 'name',
      width: '600px',
      render: (text, record) => {
        const menu = (
          <Menu>
            <Menu.Item>
              <span onClick={() => { this.props.onShareClick(record) }} style={{ width: '100%', display: 'inline-block' }}>共享</span>
            </Menu.Item>
            <Menu.Item>
              <span style={{ width: '100%', display: 'inline-block' }}>下载</span>
            </Menu.Item>
              <Menu.Item>
                <span onClick={() => { this.props.moveAndCopyRecords(record) }} style={{ width: '100%', display: 'inline-block' }}>复制/移动</span>
              </Menu.Item>
              <Menu.Item>
                <span onClick={() => { this.props.moveAndCopyRecord(record) }} style={{ width: '100%', display: 'inline-block' }}>转发到个人空间</span>
              </Menu.Item>
              <Menu.Item>
                <span onClick={() => { this.props.deleteRecord(record) }} style={{ width: '100%', display: 'inline-block' }}>删除</span>
              </Menu.Item>
            <Menu.Item>
              <span onClick={(e) => { e.stopPropagation(); this.props.editRecord(record) }} style={{ width: '100%', display: 'inline-block' }}>重命名</span>
            </Menu.Item>
            {record.type === 1 &&
              <Menu.Item>
                <span onClick={() => { this.props.showFileVersion(record) }} style={{ width: '100%', display: 'inline-block' }}>查看版本</span>
              </Menu.Item>
            }
          </Menu>
        )
        let tools
        if (this.state.overkey === record.id && !this.props.editable) {
          tools = (<span>
            <Icon type="share-alt" style={{ fontSize: 16, color: '#08c' }} onClick={this.onShareClick.bind(this, record)} />
            <Icon type="link" style={{ marginLeft: '10px', fontSize: 16, color: '#08c' }} onClick={this.onLinkClick.bind(this, record)} />
            <Dropdown overlayStyle={{ marginLeft: '30px' }} overlay={menu}>
              <Icon type="ellipsis"
                style={{
                  marginLeft: '16px',
                  fontSize: 16,
                  color: '#08c',
                }}
              />
            </Dropdown>
          </span>)
        }

        return {
          children: <div style={{ display: 'flex' }}>
            <div style={{ display: 'flex', flexFlow: 'row', width: '500px' }}>
              <FileTypeIcon dataSource={record} size="small" />
              <EditCell editable={record.editable} value={record} updateRecord={this.props.updateRecord} />
            </div>
            {tools}
          </div>,
          props: { style: { padding: 0, margin: 0, lineHeight: '30px' } },
        }
      },
    }, {
      title: '大小',
      dataIndex: 'size',
      key: 'size',
      width: '200px',
      render: (text, record) => {
        if (record.type === 1) {
          return formatFileSize(parseInt(text))
        }
        return '-'
      },
    }, {
      title: '修改日期',
      dataIndex: 'modifiedAt',
      key: 'modifiedAt',
      render: (text, record) => {
        return formatDate(parseInt(text))
      },
    }]
  }


  rowClassName = (record, index) => {
    if (this.state.selectedRowKeys.findIndex((value, index, arr) => {
      return value === record.id
    }) !== -1) {
      return 'selected'
    }
    return ''
  }

  handleRowClick = (record) => {
    if (this.props.editable) {
      return
    }

    if (this.props.selectedFiles) {
      if (record.type === 0) {
        this.props.selectedFiles([])
        this.setState({
          selectedRowKeys: [],
          selectedRows: [],
        })
      } else {
        this.props.selectedFiles([...record])
        this.setState({
          selectedRowKeys: [record.id],
          selectedRows: [...record],
        })
      }
    }

    if (this.props.onRowClick) {
      this.props.onRowClick(record)
    }
  }

  onShareClick (record, e) {
    e.stopPropagation()
    if (this.props.onShareClick) {
      this.props.onShareClick(record)
    }
  }

  onLinkClick (record, e) {
    e.stopPropagation()
    if (this.props.onLinkClick) {
      this.props.onLinkClick(record)
    }
  }

  clearSelected = () => {
    this.setState({
      selectedRowKeys: [],
      selectedRows: [],
    })
  }

  clearOverkey = () => {
    this.setState({
      overkey: 0,
    })
  }

  handleRowMouseEnter = (record) => {
    if (this.state.selectedRows.length > 1) {
      this.setState({ overkey: 0 })
    } else {
      this.setState({ overkey: record.id })
    }
  }

  render () {
    const { selectedRowKeys } = this.state
    const rowSelection = {
      selectedRowKeys,
      hideDefaultSelections: true,
      onChange: (selectedRowKeys, selectedRows) => {
        this.state.selectedRows = selectedRows
        if (this.props.selectedFiles) {
          this.props.selectedFiles(selectedRows)
        }

        if (selectedRowKeys.length > 1) {
          this.setState({ selectedRowKeys, overkey: 0 })
        } else {
          this.setState({ selectedRowKeys })
        }
        console.log(selectedRows)
      },
      onSelection: (!this.props.editable ? this.onSelection : () => {
      }),
      getCheckboxProps: record => ({
        disabled: (record.type === 0 && record.extraType) || this.props.editable,
      }),
    }

    return (
      <MutilDataGrid
        selectedRows={this.state.selectedRows}
        selectedFiles={this.props.selectedFiles}
        onContextMenu={this.props.onContextMenu}
        onRowMouseEnter={this.handleRowMouseEnter}
        onRowClick={this.handleRowClick}
        rowKey={record => record.id}
        rowClassName={this.rowClassName}
        rowSelection={rowSelection}
        isPagination={false}
        dataSource={this.props.dataSource}
        togStyle={this.props.togStyle}
        updateRecord={this.props.updateRecord}
        columns={this.columns}
      />
    )
  }
}
