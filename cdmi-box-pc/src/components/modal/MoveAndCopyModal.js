import Component from '../Component'
import { Modal, Layout, Tree, Button } from 'antd'
import { getUserInfo } from 'utils'

const TreeNode = Tree.TreeNode
const { Content, Footer } = Layout

export default class MoveAndCopyModal extends Component {
  constructor (props) {
    super(props)
    this.state = {
      visible: false,
      selectedKeys: ['0'],
      treeData: [
        { name: '个人空间', id: '0' },
      ],
    }
  }

  componentDidMount () {
    this.cloudUserId = getUserInfo().cloudUserId
    this.ds = this.http()
  }

  onLoadData = (treeNode) => {
    return new Promise((resolve) => {
      if (treeNode.props.children) {
        resolve()
        return
      }
      const folderId = treeNode.props.dataRef.id
      this.ds.url = `/folders/${this.cloudUserId}/${folderId}/items`
      this.ds.post((data) => {
        treeNode.props.dataRef.children = [
          ...data.folders,
        ]
        this.setState({
          treeData: [...this.state.treeData],
        })
        resolve()
      })
    })
  }

  renderTreeNodes = (data) => {
    return data.map((item) => {
      if (item.children) {
        return (
          <TreeNode title={item.name} key={item.id} dataRef={item}>
            {this.renderTreeNodes(item.children)}
          </TreeNode>
        )
      }
      return <TreeNode title={item.name} key={item.id} dataRef={item} />
    })
  }

  show = () => {
    this.setState({
      visible: true,
    })
  }

  hide = () => {
    this.setState({
      visible: false,
    })
  }

  handleCancel = () => {
    this.setState({
      visible: false,
    })
  }

  onCopyClick = () => {
    if (this.props.onCopyClick) {
      const folderId = this.currentSelectedNode.props.dataRef.id
      this.props.onCopyClick(parseInt(folderId))
    }
  }

  onMoveClick = () => {
    if (this.props.onMoveClick) {
      const folderId = this.currentSelectedNode.props.dataRef.id
      this.props.onMoveClick(parseInt(folderId))
    }
  }

  onSelect = (selectedKeys, e) => {
    this.currentSelectedNode = e.node
    this.setState({
      selectedKeys,
    })
  }

  render () {
    const { visible } = this.state
    return (
      <Modal
        title="复制/移动"
        visible={visible}
        onCancel={this.handleCancel}
        footer={null}
      >
        <Layout>
          <Content style={{ height: '300px', border: '1px solid #ccc', overflow: 'auto' }}>
            <Tree loadData={this.onLoadData} onSelect={this.onSelect} selectedKeys={this.state.selectedKeys}>
              {this.renderTreeNodes(this.state.treeData)}
            </Tree>
          </Content>
          <Footer style={{ background: '#fff', padding: '10px 0'}}>
            <div style={{ float: 'right' }}>
              <Button type="primary" style={{ marginLeft: 10 }} onClick={this.onCopyClick}>复制</Button>
              <Button type="primary" style={{ marginLeft: 10 }} onClick={this.onMoveClick}>移动</Button>
              <Button style={{ marginLeft: 10 }} onClick={this.handleCancel}>取消</Button>
            </div>
          </Footer>
        </Layout>
      </Modal>
    )
  }
}

