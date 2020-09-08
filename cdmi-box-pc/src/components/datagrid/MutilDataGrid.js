import Component from '../Component'
import { Table } from 'antd'
import styles from './AntDataGrid.less'
import { ListBox, Templet } from 'wolf'

export default class MutilDataGrid extends Component {
  handleContextMenu = (record, e) => {
    console.log(123);
    const { selectedRowKeys } = this.props.rowSelection
    const { selectedRows } = this.props

    if (!record.extraType) {
      selectedRowKeys.splice(0, selectedRowKeys.length)
      selectedRowKeys.push(record.id)
      selectedRows.splice(0, selectedRows.length)
      selectedRows.push(record)
    }

    if (this.props.onContextMenu) {
      this.props.onContextMenu(record, e)
    }

    if (this.props.selectedFiles) {
      this.props.selectedFiles(selectedRows)
    }

    e.stopPropagation()
  }

  handleChange = (record, e) => {
    const { selectedRowKeys } = this.props.rowSelection
    const { selectedRows } = this.props

    if (e.target.checked) {
      selectedRowKeys.push(record.id)
      selectedRows.push(record)
    } else {
      const index = selectedRowKeys.findIndex(value => value === record.id)
      selectedRowKeys.splice(index, 1)
      selectedRows.splice(index, 1)
    }

    if (this.props.selectedFiles) {
      this.props.selectedFiles(selectedRows)
    }
    e.stopPropagation()
  }

  render () {
    return (<div>{
      this.props.togStyle === 0 ?
        <Table className={styles.antDatagrid}
          onRowContextMenu={(record, index, event) => this.handleContextMenu(record, event)}
          pagination={false}
          scroll={{ y: true }}
          {...this.props}
        />
        : <ListBox {...this.props}
          onItemContextMenu={this.handleContextMenu}
          onChange={this.handleChange}
        >
          <Templet.FileBox />
        </ListBox>
    }</div>)
  }
}

