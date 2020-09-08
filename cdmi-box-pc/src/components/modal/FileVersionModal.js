import Component from '../Component'
import { Modal, Tag, Button } from 'antd'
import { formatFileSize, formatDate } from 'utils'

export default class FileVersionModal extends Component {
  state = {
    visible: false,
    title: '',
    dataSource: [],
  }

  componentDidMount () {
    this.ds = this.http()
  }

  show = (record) => {
    this.setState({
      visible: true,
      title: `查看${record.name}的版本`,
      dataSource: [],
    })

    this.ds.url = `/files/${record.ownedBy}/${record.id}/versions`
    this.ds.get((data) => {
      this.setState({
        dataSource: data.versions,
      })
    })
  }

  handleCancel = () => {
    this.setState({
      visible: false,
    })
  }

  render () {
    const { visible, title, dataSource } = this.state
    return (
      <Modal
        title={title}
        visible={visible}
        onCancel={this.handleCancel}
        footer={[
          <Button key="cancel" size="large" onClick={this.handleCancel}>取消</Button>,
        ]}
      >
        <ul>
          {dataSource.map((row, index) =>
            (<li key={row.id} style={{ marginBottom: '10px' }}>
              <Tag color="blue">V{index + 1}</Tag>
              <Tag>{formatDate(row.createdAt)}</Tag>
              <Tag>{formatFileSize(row.size)}</Tag>
            </li>)
          )}
        </ul>
      </Modal>
    )
  }
}
