import React from 'react'
import Component from '../Component'

export default class List extends Component {
  constructor (props) {
    super(props)
    this.data = []
  }
  componentDidMount () {

  }
  subrender = (props,_datasrouce) => {
    return React.Children.map(props.children, (child) => {
      return React.cloneElement(child, {
        // 把父组件的props.name赋值给每个子组件
        datasource: _datasrouce,
      })
    })
  }
  render () {
    return (<div className="list">
      {this.data.map((item) => {
        return this.subrender(this.props, item)
      })}
    </div>
    )
  }
}
