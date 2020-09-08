import Component from '../Component'
import { Input, Button, Icon } from 'antd'
import index from '../../routes/error'

/**
 * 可编辑的表格单元
 */
export default class EditCell extends Component {
  constructor (props) {
    super(props)
    this.state = {
      v: '',
    }
  }

  handleOk = (e) => {
    if (this.props.updateRecord) {
      this.props.updateRecord(this.props.value, this.state.v)
    }
    e.stopPropagation()
  }
  handleCancel = (e) => {
    if (this.props.updateRecord) {
      this.props.updateRecord(this.props.value)
    }
    e.stopPropagation()
  }
  onChange = (e) => {
    this.setState({ v: e.target.value })
  }

  render () {
    return (<div style={{ width: '300px'}}>
      {this.props.editable
        ? <div>
          <span style={{ display: 'inline-block' }}>
            <Input value={this.state.v || this.props.value.name} onChange={this.onChange} size="small" style={{ width: '100px' }} onClick={e => e.stopPropagation()} />
          </span>
          <Button.Group style={{ margin: '-5px 5px', display: 'inline-block',width: '100px' }}>
            <Button onClick={this.handleOk} size="small">保存</Button>
            <Button onClick={this.handleCancel} size="small">取消</Button>
          </Button.Group>
        </div>
        : <div className="editcell-text">{this.props.value.name}</div>
      }
    </div>)
  }
}
