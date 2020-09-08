import React from 'react'
import {Component} from 'wolf'
import {Menu, Dropdown, Button, Icon, Popover} from 'antd'

export default class ToolBar extends Component {

    render() {
        const {selectedRecords} = this.props
        const fileNum = selectedRecords.length
        return (
            <div>
                {fileNum > 0 &&
                <Button
                    type="danger"
                    icon="plus"
                    size="large"
                    onClick={() => this.props.deleteRecord(selectedRecords)}>删除</Button>
                }
                <Button
                    style={{
                    marginLeft: 10
                }}
                    icon="plus"
                    size="large"
                    onClick={this.props.onNewFolder}>下载</Button>
                <Button
                    style={{
                    marginLeft: 10
                }}
                    icon="plus"
                    size="large"
                    onClick={this.props.onNewFolder}>转存到个人空间</Button>
                {fileNum > 0 &&
                <Button
                    style={{
                    marginLeft: 10
                }}
                    icon="plus"
                    size="large"
                    onClick={this.props.onNewFolder}>查看</Button>
            }
            
                    </div>
        );
    }
}