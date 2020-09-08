import React from 'react'
import styles from './ContextMenu.less'

export default class ContextMenu extends React.Component {
  state = {
    record: {},
    style: { visibility: 'hidden' },
  }

  hide = () => {
    this.setState({
      style: { visibility: 'hidden' },
    })
  }

  show = (row, e) => {
    this.setState({
      record: row,
    })
    console.log(`x:${e.clientX}`)
    console.log(`y:${e.clientY}`)

    let top = e.clientY
    let left = e.clientX

    let clientWidth = document.body.clientWidth
    let clientHeight = document.body.clientHeight
    let cxtMenuWidth = parseFloat(window.getComputedStyle(this.ul).width)
    let cxtMenuHeight = parseFloat(window.getComputedStyle(this.ul).height)
    // console.log('clientWidth:'+clientWidth)
    // console.log('clientHeight:'+clientHeight)
    // console.log('cxtMenuWidth:'+cxtMenuWidth)
    // console.log('cxtMenuHeight:'+cxtMenuHeight)

    if ((clientHeight - e.clientY) < cxtMenuHeight) {
      top = clientHeight - parseFloat(cxtMenuHeight)
    }

    if ((clientWidth - e.clientX) < cxtMenuWidth) {
      left = clientWidth - parseFloat(cxtMenuWidth)
    }

    // console.log("top:"+top)
    // console.log("left:"+left)
    this.state.style.top = `${top - 87}px`
    this.state.style.left = `${left - 142}px`
    this.state.style.visibility = 'visible'
    this.setState({})
  }
  render () {
    const { style, record } = this.state
    return (
      <ul className={styles.menu_conent} style={style} ref={(ul) => { this.ul = ul }} onClick={() => this.hide()} onMouseLeave={() => this.hide()}>
        <li onClick={() => { this.props.shareRecord(record) }}>共享</li>
        <li>下载</li>
        {!record.extraType &&
        <li onClick={() => { this.props.saveToTeamSpaceRecord(record) }}>转发到团队空间</li>
        }
        {!record.extraType &&
        <li onClick={() => { this.props.moveAndCopyRecord(record) }}>复制/移动</li>
        }
        {!record.extraType &&
        <li onClick={() => { this.props.deleteRecord(record) }}>删除</li>
        }
        <li onClick={() => { this.props.editRecord(record) }}>重命名</li>
        {record.fileType === 1 &&
          <li onClick={() => { this.props.showFileVersion(record) }}>查看版本</li>
        }
      </ul>
    )
  }
}
