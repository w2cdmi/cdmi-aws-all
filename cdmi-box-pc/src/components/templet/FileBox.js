import React from 'react'
import { Checkbox } from 'antd'
import { FileTypeIcon, EditCell } from 'wolf'

export default class FileBox extends React.Component {
  constructor (props) {
    super(props)
    this.state = { visible: false }
  }


  handleChange = (row, e) => {
    if (this.props.onChange) {
      this.props.onChange(row, e)
    }
    this.setState({})
    e.stopPropagation()
  }

  handleClick = (row) => {
    if (this.props.onRowClick) {
      this.props.onRowClick(row)
    }
  }

  render () {
    const row = this.props.datasource
    const checked = this.props.rowSelection.selectedRowKeys.includes(row.id)
    return (
      <div className={checked ? 'forderbox forderbox-active' : 'forderbox'}
        onContextMenu={this.props.onItemContextMenu.bind(this, row)}
        onMouseLeave={() => this.setState({ visible: false })}
        onMouseEnter={() => this.setState({ visible: true })}
        onClick={this.handleClick.bind(this, row)}
      >
        { (checked || this.state.visible) &&
          <Checkbox style={{ position: 'absolute', left: 10, top: 10 }}
            checked={checked}
            disabled={(row.type === 0 && row.extraType)}
            onClick={this.handleChange.bind(this, row)}
          />
        }
        <FileTypeIcon dataSource={row} size="large" />
        <EditCell editable={row.editable} value={row} updateRecord={this.props.updateRecord} />
      </div>
    )
  }
}
