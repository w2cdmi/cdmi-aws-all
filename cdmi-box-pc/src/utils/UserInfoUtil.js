const electron = window.require('electron')
const remote = electron.remote

const getUserInfo = () => {
  return remote.getGlobal('sharedObject').userInfo
}

export { getUserInfo }
