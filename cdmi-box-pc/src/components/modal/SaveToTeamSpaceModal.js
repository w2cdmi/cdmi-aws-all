import Component from '../Component'
import { Modal, Layout, Tree, Button } from 'antd'
import { getUserInfo } from 'utils'

const TreeNode = Tree.TreeNode
const { Content, Footer } = Layout

/**
 * 保存到团队空间
 */
export default class SaveToTeamSpaceModal extends Component {
  constructor (props) {
    super(props)
    this.state = {
      visible: false,
      selectedKeys: ['0'],
      isSelected: false,
      treeData: [
        { name: '团队空间', id: '0', key: '0', level: 0 },
      ],
    }
    this.isLoad = false
  }

  componentDidMount () {
    this.cloudUserId = getUserInfo().cloudUserId
    this.teamSpaceId = 0
    this.ds = this.http()
  }

  onLoadData = (treeNode) => {
    return new Promise((resolve) => {
      if (treeNode.props.children) {
        resolve()
        return
      }

      if (!this.isLoad) {
        this.ds.url = '/teamspaces/items'
        this.ds.data = JSON.stringify({ type: 0, userId: this.cloudUserId })
        this.ds.post((data) => {
          this.isLoad = true
          treeNode.props.dataRef.children = []
          data.memberships.map((item) => {
            treeNode.props.dataRef.children.push({ name: item.teamspace.name, id: item.teamspace.id, teamSpaceId: item.teamspace.id, key: `${item.teamspace.id}`, level: 1 })
          })
          this.setState({
            treeData: [...this.state.treeData],
          })
          resolve()
        })
      } else {
        const dataRef = treeNode.props.dataRef
        let folderId = dataRef.id
        if (dataRef.level === 1) {
          folderId = 0
        }
        this.ds.url = `/folders/${this.teamSpaceId}/${folderId}/items`
        this.ds.data = {}
        this.ds.post((data) => {
          console.log(data);
          treeNode.props.dataRef.children = []
          data.folders.map((item) => {
            treeNode.props.dataRef.children.push({ name: item.name, id: item.id, teamSpaceId: this.teamSpaceId, key: `${this.teamSpaceId}_${item.id}`, level: 3 })
          })
          this.setState({
            treeData: [...this.state.treeData],
          })
          resolve()
        })
      }
    })
  }

  renderTreeNodes = (data) => {
    return data.map((item) => {
      if (item.children) {
        return (
          <TreeNode title={item.name} key={item.key} dataRef={item}>
            {this.renderTreeNodes(item.children)}
          </TreeNode>
        )
      }
      return <TreeNode title={item.name} key={item.key} dataRef={item} />
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

  onSelect = (selectedKeys, e) => {
    this.currentSelectedNode = e.node
    this.setState({
      selectedKeys,
      isSelected: this.currentSelectedNode.props.dataRef.level > 0,
    })
  }

  onExpand = (expandedKeys, e) => {
    const dataRef = e.node.props.dataRef
    if (dataRef.level === 1) {
      this.teamSpaceId = dataRef.id
    }
  }

  handleOk = () => {
    this.setState({
      visible: false,
    })
  }

  handleCancel = () => {
    this.setState({
      visible: false,
    })
  }

  onSaveClick = () => {
    if (this.props.onSaveClick) {
      const dataRef = this.currentSelectedNode.props.dataRef
      let folderId
      if (dataRef.level === 1) {
        folderId = 0
      } else if (dataRef.level === 3) {
        folderId = dataRef.id
      }
      this.props.onSaveClick(dataRef.teamSpaceId, folderId)
    }
  }

  render () {
    const { visible } = this.state

    return (
      <Modal
        title="转发到团队空间"
        visible={visible}
        onCancel={this.handleCancel}
        footer={null}
      >
        <Layout>
          <Content style={{ height: '300px', border: '1px solid #ccc', overflow: 'auto' }}>
            <Tree loadData={this.onLoadData} onSelect={this.onSelect} onExpand={this.onExpand} selectedKeys={this.state.selectedKeys}>
              {this.renderTreeNodes(this.state.treeData)}
            </Tree>
          </Content>
          <Footer style={{ background: '#fff', padding: '10px 0', float: 'left' }}>
            <div style={{ float: 'right' }}>
              {this.state.isSelected &&
                <Button type="primary" style={{ marginLeft: 10 }} onClick={this.onSaveClick}>保存</Button>
              }
              <Button style={{ marginLeft: 10 }} onClick={this.handleCancel}>取消</Button>
            </div>
          </Footer>
        </Layout>
      </Modal>
    )
  }
}
