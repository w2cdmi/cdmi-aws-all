/**
 * Created by quxiangqian on 2017/12/5.
 */
import Component from '../Component'
import { Breadcrumb } from 'antd'

export default class FileBreadcrumb extends Component {
  onBack = (e) => {
    e.preventDefault()
    this.props.onBack()
  }
  onClick(index, e) {
    e.preventDefault()
    this.props.onClick(index, e)
  }

  render () {
    const len = this.props.dataSource.length
    const others = this.props.dataSource.map((row,index) =>
      <Breadcrumb.Item key={index + 1}>
      { (len - 1) === index ?
        row.name :
        <a href="javascript:void(0);" onClick={this.onClick.bind(this, index+1)}>{row.name}</a>
      }
      </Breadcrumb.Item>)
    return (
      <Breadcrumb separator="&gt;" style={{ display: 'inline-block'}}>
        { len > 0 &&
          <Breadcrumb.Item key="back">
            <a href="javascript:void(0);" onClick={this.onBack}>返回上一页</a>
          </Breadcrumb.Item>}
        { this.props.defaultName &&
          <Breadcrumb.Item key="0">
            { len > 0 ?
              <a href="javascript:void(0);" onClick={this.onClick.bind(this, 0)}>{this.props.defaultName}</a>
              : this.props.defaultName }
          </Breadcrumb.Item>}
        {others}
      </Breadcrumb>
    )
  }
}

