import React from 'react'
import { Input } from 'antd'
import styles from './Search.less'

const Search = Input.Search
export default class Searchd extends React.Component {
  constructor (props) {
    super(props)
    this.state = {
      value: '',
    }
  }

  handleChange = (e) => {
    this.setState({ value: e.target.value })
  }

  handleCancel = () => {
    if (this.props.onCancel) {
      this.props.onCancel()
    }
    this.setState({
      value: '',
    })
  }

  handleSearch = () => {
	  if (this.props.onSearch) {
	    this.props.onSearch(this.state.value)
    }
  }

  render () {
    return (
      <div className={styles.searchs}>
        {
          this.state.value.length > 0 &&
            <button onClick={this.handleCancel} className={styles.cancel}>X</button>
        }
        <Search ref="input"
          placeholder="按文件名称搜素"
          value={this.state.value}
          onChange={this.handleChange}
          onSearch={this.handleSearch}
          style={{ width: 200 }}
        />
      </div>

    )
  }
}
