import React from 'react'
import { Tag } from 'antd'
import moment from 'moment'

export default class ShareLinkInfo extends React.Component {
  render () {
    const linkRecord = this.props.dataSource
    return (<div style={{ marginTop: '5px' }}>
      <span>访问权限：</span>
      {linkRecord.role === 'viewer' &&
      <span>
        <Tag color="blue">下载</Tag>
        <Tag color="blue">预览</Tag>
      </span>
      }
      {linkRecord.role === 'previewer' &&
      <span>
        <Tag color="blue">预览</Tag>
      </span>
      }
      {linkRecord.role === 'uploader' &&
      <span>
        <Tag color="blue">上传</Tag>
      </span>
      }
      {linkRecord.role === 'uploadAndView' &&
      <span>
        <Tag color="blue">下载</Tag>
        <Tag color="blue">预览</Tag>
        <Tag color="blue">上传</Tag>
      </span>
      }
      {(linkRecord.accessCodeMode === 'static' && linkRecord.plainAccessCode) &&
      <span>提取码：<Tag color="#2db7f5">{linkRecord.plainAccessCode}</Tag></span>
      }
      {linkRecord.accessCodeMode === 'mail' &&
      <span>提取码：<Tag color="#2db7f5">动态码</Tag></span>
      }
      {linkRecord.effectiveAt &&
      <span>有效期：{moment(linkRecord.effectiveAt).format('YYYY-MM-DD HH:mm')}~{moment(linkRecord.expireAt).format('YYYY-MM-DD HH:mm')}</span>
      }
    </div>)
  }
}
