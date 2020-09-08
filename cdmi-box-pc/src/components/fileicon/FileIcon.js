import React from 'react';
import styles from './FileIcon.less';

export default class FileIcon extends React.Component{
	constructor(props){
		super(props)
	}
	static defaultProps = {
    		type:"folder",
    		size:"small"
		}
	render(){
		const styless ={
			folder:{},exe:{},com:{},csv:{},
			db:{},defualt:{},dll:{},excel:{},
			folderShare:{},folderDisk:{},
			folderBackup:{},folderPc:{},help:{},
			htm:{},inf:{},ini:{},iso:{},
			js:{},music:{},pdf:{},word:{},ppt:{},
			psd:{},rar:{},text:{},shareFolderdisk:{},
			shareFolderpc:{},video:{},xmls:{},png:{}
		}
		const icon =`${this.props.type}_${this.props.size}`;
		
		return(
			<i style={styless[this.props.type]} className={styles[icon]}></i>
		)
	}
}