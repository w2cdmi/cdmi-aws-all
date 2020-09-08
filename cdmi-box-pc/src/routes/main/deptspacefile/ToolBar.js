/**
 * Created by quxiangqian on 2017/11/29.
 */
import React from 'react'
import styles from './Toolbar.less'
import { Menu, Dropdown, Button, Icon, Popover } from 'antd'
import { Search } from 'wolf'

export default class ToolBar extends React.Component {
  state={
    sortid: 0,
    visible: false,
    togstyle: 0,
    showButton: true,
  }

  handleVisibleChange = (visible) => {
    this.setState({ visible })
  }

  togDgStyle =() => {
    if (this.state.togstyle === 0) {
      this.state.togstyle = 1
    } else {
      this.state.togstyle = 0
    }
    if (this.props.togDgStyle) {
      this.props.togDgStyle(this.state.togstyle)
    }
    this.setState({ togstyle: this.state.togstyle })
  }

  handleSearch = (value) => {
    if (this.props.onSearch) {
      this.props.onSearch(value)
      this.setState({
        showButton: false,
      })
    }
  }

  handelCancel = () => {
    if (this.props.onCancel) {
      this.props.onCancel()
    }
    this.setState({
      showButton: true,
    })
  }


  handelSort (sortid) {
    this.setState({ sortid, visible: false })
    if (this.props.onSort) {
      this.props.onSort(sortid)
    }
  }

  render () {
    const { visible, showButton, togstyle } = this.state
    const { selectedRecords } = this.props
    const fileNum = selectedRecords.length
    const menu = (
      <Menu>
        <Menu.Item>
          <span onClick={() => this.props.moveAndCopyRecords(selectedRecords) } style={{ width: '100%', display: 'inline-block' }}>复制/移动</span>
        </Menu.Item>
        <Menu.Item>
          <span onClick={() => this.props.moveAndCopyRecord(selectedRecords)} style={{ width: '100%', display: 'inline-block' }}>转发到个人空间</span>
        </Menu.Item>
        <Menu.Item>
          <span onClick={() => this.props.deleteRecord(selectedRecords)} style={{ width: '100%', display: 'inline-block' }}>删除</span>
        </Menu.Item>
        {fileNum === 1 &&
          <Menu.Item>
            <span onClick={() => this.props.editRecord(selectedRecords[0])} style={{ width: '100%', display: 'inline-block' }}>重命名</span>
          </Menu.Item>
        }
        {(fileNum === 1 && selectedRecords[0].type === 1) &&
          <Menu.Item>
            <span onClick={() => this.props.showFileVersion(selectedRecords[0])} style={{ width: '100%', display: 'inline-block' }}>查看版本</span>
          </Menu.Item>
        }
      </Menu>
    )

    return (
      <div className={styles.Toolbar}>
        <div>
          {showButton &&
            <span>
              <Button type="primary" icon="upload" size="large" onClick={this.props.onUpload} >上传</Button>
              {
              	this.props.role =="auther" || this.props.role =="editor"?(
              		<Button style={{ marginLeft: 10 }} icon="plus" size="large" onClick={this.props.onNewFolder} >创建文件夹</Button>
              	):(<span></span>)
              }
              
            </span>
          }
          {fileNum > 0 &&
          <Button.Group size="large" style={{ marginLeft: 10 }}>
            <Button>下载</Button>
            {fileNum === 1 &&
            <Button onClick={() => this.props.onShare(selectedRecords[0])}>共享</Button>
            }
            <Dropdown overlay={menu}>
              <Button>更多<Icon type="down" /></Button>
            </Dropdown>
          </Button.Group>
          }
        </div>
        <div>
          <Search onSearch={this.handleSearch} onCancel={this.handelCancel} />
          <Popover placement="bottom"
            overlayClassName={styles.Popover1}
            visible={visible}
            onVisibleChange={this.handleVisibleChange}
            content={(
              <div className={styles.sortbar}>
                <div onClick={this.handelSort.bind(this, 'name')}>{this.state.sortid === 'name' ? <Icon type="check" /> : <span style={{ paddingLeft: 12 }} />}<span style={{ paddingLeft: 10 }}>名称</span></div>
                <div onClick={this.handelSort.bind(this, 'size')}>{this.state.sortid === 'size' ? <Icon type="check" /> : <span style={{ paddingLeft: 12 }} />}<span style={{ paddingLeft: 10 }}>大小</span></div>
                <div onClick={this.handelSort.bind(this, 'modifiedAt')}>{this.state.sortid === 'modifiedAt' ? <Icon type="check" /> : <span style={{ paddingLeft: 12 }} />}<span style={{ paddingLeft: 10 }}>修改日期</span></div>
              </div>
            )}
            trigger="hover"
          >
            <Button style={{ marginLeft: 10 }}>
              <Icon type="arrow-down" />
            </Button>
          </Popover>
          <Button style={{ marginLeft: 10 }} onClick={this.togDgStyle}>
            {togstyle === 0 ? <Icon type="appstore" /> : <Icon type="menu-fold" />}
          </Button>
        </div>
      </div>)
  }
}
