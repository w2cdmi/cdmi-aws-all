import React from 'react'
import FileIcon from '../fileicon/FileIcon'

export default class FileTypeIcon extends React.Component {
  wordExts = ['doc', 'docx']
  excelExts = ['xls', 'xlsx']
  pptExts = ['ppt', 'pptx']
  imgExts = ['png', 'jpg', 'jpeg', 'bmp', 'gif']
  musicExts = ['mp3', 'wma', 'wav', 'mod', 'ra', 'cd', 'md', 'asf', 'aac', 'vqf', 'ape', 'mid', 'ogg', 'm4a', 'vqf']
  videoExts = ['mp4', 'avi', 'mov', 'wmv', 'asf', 'navi', '3gp', 'mkv', 'f4v', 'rmvb', 'webm']
  zipExts = ['zip', 'rar', 'gzip', '7z', 'tar', 'gz', 'taz', 'tgz']
  htmlExts = ['html', 'htm']
  isWord (ext) {
    return this.wordExts.includes(ext)
  }
  isExcel (ext) {
    return this.excelExts.includes(ext)
  }
  isPPT (ext) {
    return this.pptExts.includes(ext)
  }
  isImg (ext) {
    return this.imgExts.includes(ext)
  }
  isMusic (ext) {
    return this.musicExts.includes(ext)
  }
  isVedio (ext) {
    return this.videoExts.includes(ext)
  }
  isZip (ext) {
    return this.zipExts.includes(ext)
  }
  isHtml (ext) {
    return this.htmlExts.includes(ext)
  }

  render () {
    const { dataSource, size } = this.props
    const { type, extraType, name } = dataSource
    const ext = name.substring(name.lastIndexOf('.') + 1, name.length)
    let icon
    if (type === 1) {
      if (this.isWord(ext)) {
        icon = (<FileIcon type="word" size={size} />)
      } else if (this.isExcel(ext)) {
        icon = (<FileIcon type="excel" size={size} />)
      } else if (this.isPPT(ext)) {
        icon = (<FileIcon type="ppt" size={size} />)
      } else if (this.isImg(ext)) {
        icon = (<FileIcon type="png" size={size} />)
      } else if (this.isMusic(ext)) {
        icon = (<FileIcon type="music" size={size} />)
      } else if (this.isVedio(ext)) {
        icon = (<FileIcon type="video" size={size} />)
      } else if (this.isZip(ext)) {
        icon = (<FileIcon type="rar" size={size} />)
      } else if (this.isHtml(ext)) {
        icon = (<FileIcon type="htm" size={size} />)
      } else if (ext === 'exe') {
        icon = (<FileIcon type="exe" size={size} />)
      } else if (ext === 'com') {
        icon = (<FileIcon type="com" size={size} />)
      } else if (ext === 'csv') {
        icon = (<FileIcon type="csv" size={size} />)
      } else if (ext === 'db') {
        icon = (<FileIcon type="db" size={size} />)
      } else if (ext === 'dll') {
        icon = (<FileIcon type="dll" size={size} />)
      } else if (ext === 'inf') {
        icon = (<FileIcon type="inf" size={size} />)
      } else if (ext === 'ini') {
        icon = (<FileIcon type="ini" size={size} />)
      } else if (ext === 'iso') {
        icon = (<FileIcon type="iso" size={size} />)
      } else if (ext === 'js') {
        icon = (<FileIcon type="js" size={size} />)
      } else if (ext === 'pdf') {
        icon = (<FileIcon type="pdf" size={size} />)
      } else if (ext === 'txt') {
        icon = (<FileIcon type="txt" size={size} />)
      } else if (ext === 'xml') {
        icon = (<FileIcon type="xmls" size={size} />)
      } else {
        icon = (<FileIcon type="defualt" size={size} />)
      }
    } else if (type === 0) {
      switch (extraType) {
        case 'computer':
          icon = (<FileIcon type="folderPc" size={size} />)
          break
        case 'disk':
          icon = (<FileIcon type="folderDisk" size={size} />)
          break
        default:
          icon = (<FileIcon type="folder" size={size} />)
          break
      }
    }else if(type === -6){
    	icon = (<FileIcon type="defualt" size={size} />)
    }
    return icon
  }
}
