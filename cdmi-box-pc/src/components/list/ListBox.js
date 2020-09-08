/**
 * Created by quxiangqian on 2017/11/30.
 */
import React from 'react'


export default class ListBox extends React.Component {
  constructor (props) {
    super(props)
  }

  state={
    loading: false,
  }

  subrender = (props, _datasrouce) => {
    return React.Children.map(props.children, (child) => {
      return React.cloneElement(child, {
        // 把父组件的props.name赋值给每个子组件
        datasource: _datasrouce,
        ...this.props,
      })
    })
  }

  render () {
    return (<div className="listbox" onContextMenu={this.props.onContextMenu}>
      {this.props.dataSource.map((item) => {
        return this.subrender(this.props, item)
      })}
    </div>
    )
  }
}
