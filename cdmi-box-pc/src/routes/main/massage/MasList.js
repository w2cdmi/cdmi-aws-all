/**
 * Created by quxiangqian on 2017/11/27.
 */
import { MutilDataGrid, EditCell, Component, FileTypeIcon } from 'wolf'
import { Menu, Dropdown, Icon } from 'antd'
import { formatFileSize, formatDate } from 'utils'
import styles from  './MasList.less'

export default class AllfileDataGrid extends Component {
  constructor (props) {
    super(props)
    this.state = {
      selectedRowKeys: [],
      selectedRows: [],
      overkey: 0,
      valueName: ['共享','取消共享','团队空间上传文件','团队空间添加成员','团队空间删除成员','成员退出团队空间','群组添加成员','群组删除成员','成员退出群组','团队空间成员角色变更','群组成员角色变更','系统消息']
    }

    this.columns = [{
        title: '消息',
        dataIndex: 'type',
        key: 'type',
        // width: '50px',
        render: (text, record) => {
            if (text === 'share') {
                return '[' + this.state.valueName[0] + ']'
            }
            if (text === 'deleteShare') {
                return '[' + this.state.valueName[1] + ']'
            }
            if (text === 'teamspaceUpload') {
                return '[' + this.state.valueName[2] + ']'
            }
            if (text === 'teamspaceAddMember') {
                return '[' + this.state.valueName[3] + ']'
            }
            if (text === 'teamspaceDeleteMember') {
                return '[' + this.state.valueName[4] + ']'
            }
            if (text === 'leaveTeamspace') {
                return '[' + this.state.valueName[5] + ']'
            }
            if (text === 'groupAddMember') {
                return '[' + this.state.valueName[6] + ']'
            }
            if (text === 'groupDeleteMember') {
                return '[' + this.state.valueName[7] + ']'
            }
            if (text === 'leaveGroup') {
                return '[' + this.state.valueName[8] + ']'
            }
            if (text === 'teamspaceRoleUpdate') {
                return '[' + this.state.valueName[9] + ']'
            }
            if (text === 'groupRoleUpdate') {
                return '[' + this.state.valueName[10] + ']'
            }
            if (text === 'system ') {
                return '[' + this.state.valueName[11] + ']'
            }

            return '[' + text + ']'
          },
      },{
      title: '',
      dataIndex: 'params.nodeName',
      key: 'params.nodeName',
      render: (text, record) => {
        let tools
        if (this.state.overkey === record.id && !this.props.editable) {
          tools = (<span>
            <Icon type="download" style={{ fontSize: 20, color: '#000' }} onClick={this.onShareClick.bind(this, record)} />
            <Icon type="arrow-right" style={{ marginLeft: '20px', fontSize: 20, color: '#000' }} onClick={this.onShareClick.bind(this, record)} />
            <Icon type="close" style={{ marginLeft: '20px', fontSize: 20, color: '#000' }} onClick={this.onDeleteClick.bind(this, record)} />
          </span>)
        }

        return {
          children: <div style={{ display: 'flex' }}>
            <div style={{ display: 'flex', flexFlow: 'row', width: '500px' }}>
            {text}
            </div>
            {tools}
          </div>,
          props: { style: { padding: 0, margin: 0, lineHeight: '30px' } },
        }
      },
    }, {
      title: '',
      dataIndex: 'createdAt',
      key: 'createdAt',
      render: (text, record) => {
        return formatDate(parseInt(text))
      },
    }]
  }

  componentDidMount () {
    // this.cloudUserId = getUserInfo().cloudUserId
    this.ds = this.http()
    // this.getData(0)
  }

  rowClassName = (record, index) => {
    if (this.state.selectedRowKeys.findIndex((value, index, arr) => {
      return value === record.id
    }) !== -1) {
      return 'selected'
    }
    return ''
  }

  onRowClick = (record, index, event) => {
      console.log(record);
    if (this.props.editable) {
      return
    }

    if (this.props.onRowClick) {
      const len = this.state.selectedRowKeys.length
      this.state.selectedRowKeys.splice(0, len)
      this.state.selectedRowKeys.push(record.id)
      this.state.selectedRows.splice(0, len)
      this.state.selectedRows.push(record)
      this.props.onRowClick(record)
    }

    if (record.type === 0) {
      if (this.props.selectedFiles) {
        this.props.selectedFiles([])
        this.setState({ selectedRowKeys: [] })
      }
      return
    }
    if (this.props.selectedFiles) {
      this.props.selectedFiles([record.id])
    }
    this.setState({ selectedRowKeys: [record.id] })
  }

  onShareClick (record, e) {
    e.stopPropagation()
    if (this.props.onShareClick) {
      this.props.onShareClick(record)
    }
  }

  onDeleteClick (record, e) {
    console.log(record);
    e.stopPropagation()
    if (this.props.onDeleteClick) {
      this.props.onDeleteClick(record)
    }
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
          console.log(selectedRowKeys);
        this.state.selectedRows = selectedRows
        if (this.props.selectedFiles) {
          this.props.selectedFiles(selectedRows)
        }

        if (selectedRowKeys.length > 1) {
          this.setState({ selectedRowKeys, overkey: 0 })
        } else {
          this.setState({ selectedRowKeys })
        }
      },
      onSelection: (!this.props.editable ? this.onSelection : () => {
      }),
      getCheckboxProps: record => ({
        disabled: (record.type === 0 && record.extraType === 'computer') || this.props.editable,
      }),
    }
    return (
        <div className={styles.Massage_table}>
      <MutilDataGrid
        selectedRows={this.state.selectedRows}
        selectedFiles={this.props.selectedFiles}
        onContextMenu={this.props.onContextMenu}
        onRowMouseEnter={this.handleRowMouseEnter}
        onRowClick={this.onRowClick}
        rowKey={record => record.id}
        rowClassName={this.rowClassName}
        rowSelection={rowSelection}
        isPagination={true}
        dataSource={this.props.dataSource}
        togStyle={this.props.togStyle}
        columns={this.columns}
        {...this.props}
      />
        </div>
    )
  }
}
